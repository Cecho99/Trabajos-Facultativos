package GeneracionDeCodigo;
import AnalizadorLexico.*;

import java.util.*;

public class GeneradorAssembler {

    public static StringBuilder codigo = new StringBuilder();
    public static StringBuilder dataInstancias = new StringBuilder();
   
    private static final Stack<String> pila_tokens = new Stack<>();

    private static String ultimaComparacion = "";
    public static int posicionActualPolaca = 0;
    private static int auxiliarDisponible = 0;
    private static String nombreAux2bytes = "_@aux2bytes"; 
    
    private static final String ERROR_DIVISION_POR_CERO = "ERROR: Division por cero";  
    private static final String ERROR_OVERFLOW_SUMA = "ERROR: Overflow en operacion de suma";   
    private static final String ERROR_OVERFLOW_PRODUCTO = "ERROR: Overflow en operacion de producto";   
    private static final String FIN_PROGRAM = "Fin del programa";  
    private static final String INICIO_PROGRAM = "Inicio del programa";
   
    
    public static void generarCodigo() {
        for (List<String> polaca : Polaca.polacaCompleta) {
        	String tokenAnterior = null;
        	for (String token : polaca) {
        		switch (token) {
	                case "*":
	                case "+":
	                case "-":
	                case "/":
	                case ">=":
	                case ">":   
	                case "<=":
	                case "<":
	                case "!!":
	                case "==":
	                case "=":
	                	 generarOperador(token);
	                break;
	                case "#BI":
	                    generarSalto("JMP");
	                    break;
	                case "#BF":
	                    generarSalto(ultimaComparacion);
	                    break;
	                case "CALL":
	                    generarLlamadoFuncion(); 
	                    break;
	                case "Label":
	                	 codigo.append("\n");	
	                	 codigo.append("\n");	
	                     generarEtiqueta(tokenAnterior);
	                    break;
	                case "RETURN": 
	                	codigo.append("ret  ").append("\n"); 
	                	break;
	                case "PRINT":
	                	  String t = tokenAnterior.replace(" ", "_");
	                	  codigo.append("invoke MessageBox, NULL, addr msj_print_" + t).append(", addr msj_print_" + t).append(", MB_OK \n");
	                case "inst":
	                	generarInstancias();
	                default:
	                       pila_tokens.push(token);
	                      String tokenActual = token;
	                      tokenAnterior = tokenActual;
	                break;
	            
	            }
	            
	             
	          ++posicionActualPolaca;
        	}
        	
        }
        
        codigo.append("\n");
        codigo.append("fin: ").append("\n");
        codigo.append("invoke MessageBox, NULL, addr msj_fin, addr msj_fin, MB_OK \n");
        codigo.append("invoke ExitProcess, 0\n")
        .append("end start");

        System.out.println("  ");
        System.out.println("  ");
        generarCabecera();
 }
    
  
    
    
    private static void generarInstancias() {
		String instancia = pila_tokens.pop();
	

        for (String simbolo : TablaDeSimbolos.getTabla().keySet()) {
        	if (TablaDeSimbolos.getUso(simbolo) != null && TablaDeSimbolos.getClase(simbolo)!= null) {
        		if (TablaDeSimbolos.getUso(simbolo).equals("Atributo") && TablaDeSimbolos.getClase(simbolo).equals(TablaDeSimbolos.getTipo(instancia))){
        			if (TablaDeSimbolos.getHerencia(simbolo) !=null) {
        				String herencia = TablaDeSimbolos.getHerencia(simbolo);
        				for (String simbolito : TablaDeSimbolos.getTabla().keySet()) {
        					if (TablaDeSimbolos.getUso(simbolito) != null && TablaDeSimbolos.getClase(simbolito)!= null) {
        						if (TablaDeSimbolos.getUso(simbolito).equals("Atributo") && TablaDeSimbolos.getClase(simbolito).equals(herencia)){
        							 crearInstancia(simbolito, instancia);
        		        		}
        					}
        		        		
        				}
        			}
        			
        	
        			 crearInstancia(simbolo, instancia);
        		}
        		
        	}
        }
	}

    private static void crearInstancia (String simbolo, String instancia) {
    	String uso = TablaDeSimbolos.getUso(simbolo);
        String tipo = TablaDeSimbolos.getTipo(simbolo);
		if (tipo == null)  {
       	 tipo = "null";
        }
        
        switch (tipo) {
            case TablaTipos.INT_TYPE:
           	
           		if (simbolo.substring(0,1).equals("_")){
           			dataInstancias.append(instancia+simbolo).append(" dw ?\n");
           		}else {
           			dataInstancias.append("_"+instancia+simbolo).append(" dw ?\n");
        		}	
              	 break;
           	 
            case TablaTipos.ULONG_TYPE:
           	
	            		if (simbolo.substring(0,1).equals("_")){
	            		 dataInstancias.append(instancia+simbolo).append(" dd ?\n");
	            		}else {
	            			dataInstancias.append("_"+instancia+simbolo).append(" dd ?\n");
	            		}
	            	   
	               	 break;
           
            case TablaTipos.DOUBLE_TYPE:
           	 
	            		if (simbolo.substring(0,1).equals("_")){
	            		 dataInstancias.append(instancia+simbolo).append(" REAL8 ?\n");
	            		}else {
	            			dataInstancias.append("_"+instancia+simbolo).append(" REAL8 ?\n");
	            		}
	            	   
	               	 break;
           
        }
    }



	private static void generarCabecera() {

            StringBuilder cabecera = new StringBuilder();

            cabecera.append(".586\n")
                .append(".model flat, stdcall\n")
                .append("option casemap :none\n")
                .append("include \\masm32\\include\\windows.inc\n")
                .append("include \\masm32\\include\\kernel32.inc\n")
                .append("include \\masm32\\include\\user32.inc\n")
                .append("includelib \\masm32\\lib\\kernel32.lib\n")
                .append("includelib \\masm32\\lib\\user32.lib\n")
                .append("\n")
                .append(".data\n")
                .append(nombreAux2bytes).append(" dw ? \n")
                .append("msj_inicio db \"" + INICIO_PROGRAM + "\", 0\n")
                .append("msj_fin db \"" + FIN_PROGRAM + "\", 0\n")
                .append("error_division_por_cero db \"" + ERROR_DIVISION_POR_CERO + "\", 0\n")
                .append("error_overflow_producto db \"" + ERROR_OVERFLOW_PRODUCTO + "\", 0\n")
                .append("error_overflow_suma db \"" + ERROR_OVERFLOW_SUMA + "\", 0\n");

            generarCodigoDatos(cabecera);
            
            cabecera.append("\n");
            cabecera.append("\n");
            cabecera.append(".code\n");
            cabecera.append(codigo);
            codigo = cabecera;
        }
   
     
    
    
    private static void generarCodigoDatos(StringBuilder cabecera) {
   	
         for (String simbolo : TablaDeSimbolos.getTabla().keySet()) {
             String uso = TablaDeSimbolos.getUso(simbolo);
             String tipo = TablaDeSimbolos.getTipo(simbolo);
             
             if (tipo == null)  {
            	 tipo = "null";
             }
             
             switch (tipo) {
	             case TablaTipos.INT_TYPE:
	            	 if (uso.equals("Variable") || uso.equals("Parametro") || uso.equals("Atributo")) {
	            		 
	            		if (simbolo.substring(0,1).equals("_")){
	            		 cabecera.append(simbolo).append(" dw ?\n");
	            		}
	            	    else {
	            	     cabecera.append("_"+simbolo).append(" dw ?\n");
	            		}
	            	 }else {
	            		 String v = obtenerValorAntesDeCaracter(simbolo, '_');
	            		 cabecera.append("_"+simbolo).append(" dw " + v + "\n");
	            	 }
	               	 break;
	            	 
	             case TablaTipos.ULONG_TYPE:
	            	 if (uso.equals("Variable")|| (uso.equals("Parametro"))|| uso.equals("Atributo")) {
		            		if (simbolo.substring(0,1).equals("_")){
		            		 cabecera.append(simbolo).append(" dd ?\n");
		            		}
		            	    else {
		            	     cabecera.append("_"+simbolo).append(" dd ?\n");
		            		}
		            	 }else {
		            		 String v = obtenerValorAntesDeCaracter(simbolo, '_');
		            		 cabecera.append("_"+simbolo).append(" dd " + v + "\n");
		            	 }
		               	 break;
	            
	             case TablaTipos.DOUBLE_TYPE:
	            	 if (uso.equals("Variable")|| (uso.equals("Parametro")) || uso.equals("Atributo")) {
		            		if (simbolo.substring(0,1).equals("_")){
		            		 cabecera.append(simbolo).append(" REAL8 ?\n");
		            		}
		            	    else {
		            	     cabecera.append("_"+simbolo).append(" REAL8 ?\n");
		            		}
		            	 }else {
		            		 String v = obtenerValorAntesDeCaracter(simbolo, '_');
		            		 v = simbolo.replace('D', 'E');
		            		 simbolo = simbolo.replace('.', '_').replace('-', '_').replace('+', '_').replace('D','E');
		            		 cabecera.append("_"+simbolo).append(" REAL8 " + v + "\n");
		            	 }
		               	 break;
	            	 
	             case "CADENA":
	            	 	 String s = simbolo.replace(" ", "_");
	            	     cabecera.append("msj_print_" + s).append(" db \"").append(simbolo).append("\", 0\n");
	            	break;
	            	
	             case "null":
		        	 if (uso.equals("Nombre de funcion")) {
		        		 cabecera.append("msj_" + simbolo).append(" db \"").append("Mensaje funcion " + simbolo).append("\", 0\n");
		        	 }else {
		        		 if  (uso.equals("Nombre de metodo")) {
		        			 cabecera.append("msj_" + simbolo).append(" db \"").append("Mensaje metodo " + simbolo).append("\", 0\n");
		        		 }
		        	 }
		             break;
             }
           
        }
         if (!dataInstancias.isEmpty()) {
        	 cabecera.append(dataInstancias);
         }
    }
      
    
    
    private static String obtenerValorAntesDeCaracter(String cadena, char caracter) {
        int indice = cadena.indexOf(caracter);
        if (indice != -1) {
            return cadena.substring(0, indice);
        }
        return cadena;
    }
    
    private static void generarEtiqueta(String s) {
   	 String etiqueta = pila_tokens.pop();    
   	 
   	 if (etiqueta == "main") {
   		codigo.append("start: \n");
        codigo.append("invoke MessageBox, NULL, addr msj_inicio, addr msj_inicio, MB_OK \n");
        codigo.append("\n");
   	 }
   	 else {
	   	 codigo.append(etiqueta).append(":\n");
	   	 codigo.append("invoke MessageBox, NULL, addr msj_" + s).append(", addr msj_" + s).append(", MB_OK \n");

	   	 
   	 }
   	}
  
   
   private static void generarLlamadoFuncion() {
       
       String funcion = pila_tokens.pop();
        
       if (TablaDeSimbolos.getParametro(funcion) != null) {
    	   String parametro = pila_tokens.pop();
    	 
    	   for (String simbolo : TablaDeSimbolos.getTabla().keySet()) {
    		   if (TablaDeSimbolos.getUso(simbolo) != null && TablaDeSimbolos.getParametroNombre(simbolo) != null) {
    			   if (TablaDeSimbolos.getUso(simbolo).equals("Parametro") && TablaDeSimbolos.getParametroNombre(simbolo).equals(funcion)) {
    				   String tipo = TablaDeSimbolos.getTipo(simbolo);
    				   switch (tipo) {
    				   		case "INT":
    				   		    codigo.append("MOV CX, ").append("_" + parametro).append("\n");  // PARAMETRO DOUBLE NO ANDA NI ULONG
    		    				codigo.append("MOV _" + simbolo + ", ").append("CX").append("\n");
    				   			break;
    				   		case "ULONG":
    				   		    codigo.append("MOV ECX, ").append("_" + parametro).append("\n");  // PARAMETRO DOUBLE NO ANDA NI ULONG
 		    				    codigo.append("MOV _" + simbolo + ", ").append("ECX").append("\n");
    	    					break;
    				   		case "DOUBLE":
    				   			if ( TablaDeSimbolos.existeSimbolo(parametro)) {
    				   				if (TablaDeSimbolos.getUso(parametro).equals("Constante")) {
    				   					parametro = parametro.replace('.', '_').replace('-', '_').replace('+', '_').replace('D','E');     
    				   					codigo.append("FLD ").append("_" + parametro).append("\n");  
    				   				}else {
    				   					codigo.append("FLD ").append("_" + parametro).append("\n");  
    				   				}
    				   			}
    				   			
    				   			
		    				    codigo.append("FSTP _" + simbolo ).append("\n");
    				   			break;
    				   			
    				   }
    				   //codigo.append("MOV CX, ").append("_" + parametro).append("\n");  // PARAMETRO DOUBLE NO ANDA NI ULONG
    				   //codigo.append("MOV _" + simbolo + ", ").append(" CX").append("\n");
    			   }
    		   }
    	   }
    	   
    	  
       }  
      
       if (TablaDeSimbolos.getUso(funcion).equals("Nombre de metodo")) { 
    	   String instancia = pila_tokens.pop();
    	   for (String simbolo : TablaDeSimbolos.getTabla().keySet()) {
    		   if (TablaDeSimbolos.getUso(simbolo) != null && TablaDeSimbolos.getClase(simbolo)!= null) {
    			   if (TablaDeSimbolos.getUso(simbolo).equals("Atributo") && TablaDeSimbolos.getClase(simbolo).equals(TablaDeSimbolos.getClase(funcion))){
    				   String tipo = TablaDeSimbolos.getTipo(simbolo);
    				   switch (tipo) {
    				   		case "INT":
    				   			String registro = "_" + instancia +  simbolo;
    				   			
    				   			codigo.append("MOV CX ").append(", " + registro +"\n"); 
    				   			codigo.append("MOV " + "_" + simbolo).append(", CX\n" ); 
    				   			break;
    				   		case "ULONG":
    				   			String registro1 = "_" + instancia +  simbolo;
    				   			codigo.append("MOV ECX ").append(", " + registro1 +"\n"); 
    				   			codigo.append("MOV " + "_" + simbolo).append(", ECX\n" ); 
    	    					break;
    				   		case "DOUBLE":
    				   			String registro2 = "_" + instancia +  simbolo;
    				   			codigo.append("FLD ").append(registro2 +"\n"); 
    				   			codigo.append("FSTP " + "_" + simbolo).append("\n" ); 
    				   			break;
    				   			
    				   }
    			   }
    		   }
    	   }
       }
       
       codigo.append("CALL ").append(funcion).append("; \n");    
   }
    
   
   
   
     private static String renombre(String token) {
            char caracter = token.charAt(0);

            if (Character.isLowerCase(caracter) || Character.isUpperCase(caracter) || Character.isDigit(caracter)) {
                return "_" + token;
            } else {
                return token;
            }
        }
   
     
    
    
    private static String ocuparAuxiliar(String tipo) {
        String retorno = "_@aux" + auxiliarDisponible;
        ++auxiliarDisponible;
        Token tkn = new Token(tipo);
		tkn.setUso("Variable");
        TablaDeSimbolos.agregarSimbolo(retorno, tkn);
        return retorno;
    }
    
    
    
    private static void generarErrorOverflowProducto(String aux){
         	codigo.append("JNO ").append(aux.substring(1)).append("\n");    
            codigo.append("invoke MessageBox, NULL, addr error_overflow_producto, addr error_overflow_producto, MB_OK\n");
            codigo.append("invoke ExitProcess, 0\n");
            codigo.append(aux.substring(1)).append(":\n");
   	        
    }
    
    
    private static void generarErrorDivCero(String aux){
	        codigo.append("JNE ").append(aux.substring(1)).append("\n");
	        codigo.append("invoke MessageBox, NULL, addr error_division_por_cero, addr error_division_por_cero, MB_OK\n");
	        codigo.append("invoke ExitProcess, 0\n");
	        codigo.append(aux.substring(1)).append(":\n");       
    }
    
    
    
       
    private static void generarErrorOverflowSuma(String aux){
	    codigo.append("JNO ").append(aux.substring(1)).append("\n");    
	    codigo.append("invoke MessageBox, NULL, addr error_overflow_suma, addr error_overflow_suma, MB_OK\n");
	    codigo.append("invoke ExitProcess, 0\n");
	    codigo.append(aux.substring(1)).append(":\n");
    }
    
    
    
    
    public static void generarOperador(String operador) {
      
    	
    	String op2 = pila_tokens.pop();  
    	String instancia = "";
    	if (TablaDeSimbolos.getUso(op2).equals("Atributo")) {
    		  instancia = pila_tokens.pop();
    	}
    	
        
        String op1 = pila_tokens.pop();
        
        
        String tipo = TablaTipos.getTipoAbarcativo(op1, op2, operador);
      
	    if (TablaDeSimbolos.getUso(op2).equals("Atributo")) {
	        	
	        op2 = instancia + op2;
	      
        }
       
        switch (tipo) {
            case TablaTipos.ULONG_TYPE:
            	generarOperacionUlong(op1, op2, operador);
                break;
            case TablaTipos.INT_TYPE:
               	generarOperacionInt(op1, op2, operador);
                break; 
            case TablaTipos.DOUBLE_TYPE:
                generarOperacionFlotantes(op1, op2, operador);
                break;
        }
    }
     
    
    private static void generarOperacionUlong(String op1, String op2, String operador) {
        op1 = renombre(op1);
        op2 = renombre(op2); 

        String aux;

        switch (operador) {
            case "+":
                codigo.append("MOV ECX, ").append(op1).append("\n"); 
                codigo.append("ADD ECX, ").append(op2).append("\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                generarErrorOverflowSuma(aux);
                codigo.append("MOV ").append(aux).append(", ECX\n");
                pila_tokens.push(aux);
                break;
            case "-":
                codigo.append("MOV ECX, ").append(op1).append("\n"); 
                codigo.append("SUB ECX, ").append(op2).append("\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV ").append(aux).append(", ECX\n");
                pila_tokens.push(aux);
                break;
            case "*":
                codigo.append("MOV EAX, ").append(op1).append("\n"); 
                codigo.append("XOR DX, DX\n");
                codigo.append("MUL ").append(op2).append("\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV ").append(aux).append(", EAX\n");
                pila_tokens.push(aux);
                break;
            case "=":
            	codigo.append("MOV ECX, ").append(op1).append("\n"); 
                codigo.append("MOV ").append(op2).append(", ECX\n");
                break;
            case "/":   
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("CMP ").append(op2).append(", 00h\n");
                generarErrorDivCero(aux);
                codigo.append("MOV AX, ").append(op1).append("\n"); 
                codigo.append("XOR DX, DX\n");
                codigo.append("DIV ").append(op2).append("\n");
                codigo.append("MOV ").append(aux).append(", AX\n");
                pila_tokens.push(aux);
                break;
          
            case "==":
                codigo.append("MOV ECX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JE ").append(aux.substring(1)).append("\n"); 
                codigo.append("MOV ").append(aux).append(", 00h\n"); 
                codigo.append(aux.substring(1)).append(":\n");
                pila_tokens.push(aux);
                ultimaComparacion = "JNE";
                break;
          
            case ">=":
                codigo.append("MOV ECX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JAE ").append(aux.substring(1)).append("\n");
                codigo.append("MOV ").append(aux).append(", 00h\n");
                codigo.append(aux.substring(1)).append(":\n");
                pila_tokens.push(aux);
                ultimaComparacion = "JB";
                break;
            
            case ">":
                codigo.append("MOV ECX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JA ").append(aux.substring(1)).append("\n");
                codigo.append("MOV ").append(aux).append(", 00h\n");
                codigo.append(aux.substring(1)).append(":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JLE";
                break;
            
            case "<=":
                codigo.append("MOV ECX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JBE ").append(aux.substring(1)).append("\n");
                codigo.append("MOV ").append(aux).append(", 00h\n");
                codigo.append(aux.substring(1)).append(":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JA";
                break;
            
            case "<":
            	codigo.append("MOV ECX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV " + aux + ", 0FFh\n"); 
                codigo.append("JB " + aux.substring(1) + "\n"); 
                codigo.append("MOV " + aux + ", 00h\n");
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JAE";
                break;
                
            case "!!":
                codigo.append("MOV ECX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar(TablaTipos.ULONG_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JNE ").append(aux.substring(1)).append("\n");
                codigo.append("MOV ").append(aux).append(", 00h\n"); 
                codigo.append(aux.substring(1)).append(":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JE";
                break;
                     
            default:
                codigo.append("ERROR, se entro a default en operacion de enteros").append("\n");
                break;
        }
    }

    
    
    private static void generarOperacionInt(String op1, String op2, String operador) {
        op1 = renombre(op1);
        op2 = renombre(op2); 

        String aux;

        switch (operador) {
            case "+":
                codigo.append("MOV CX, ").append(op1).append("\n");
                codigo.append("ADD CX, ").append(op2).append("\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                generarErrorOverflowSuma(aux);
                codigo.append("MOV ").append(aux).append(", CX\n");
                pila_tokens.push(aux);
                break;
            case "-":
                codigo.append("MOV CX, ").append(op1).append("\n");
                codigo.append("SUB CX, ").append(op2).append("\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("MOV ").append(aux).append(", CX\n");
                pila_tokens.push(aux);
                break;
            case "*":
                codigo.append("MOV AX, ").append(op1).append("\n"); 
                codigo.append("XOR DX, DX\n");
                codigo.append("MUL ").append(op2).append("\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("MOV ").append(aux).append(", AX\n");
                pila_tokens.push(aux);
                break;
            case "=":
                codigo.append("MOV CX, ").append(op1).append("\n"); 
                codigo.append("MOV ").append(op2).append(", CX\n");
                break;
            case "/":   
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("CMP ").append(op2).append(", 00h\n");
                generarErrorDivCero(aux);
                codigo.append("MOV AX, ").append(op1).append("\n"); 
                codigo.append("XOR DX, DX\n");
                codigo.append("DIV ").append(op2).append("\n");
                codigo.append("MOV ").append(aux).append(", AX\n");
                pila_tokens.push(aux);
                break;
          
            case "==":
                codigo.append("MOV CX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", CX\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JE ").append(aux.substring(1)).append("\n"); 
                codigo.append("MOV ").append(aux).append(", 00h\n"); 
                codigo.append(aux.substring(1)).append(":\n");
                pila_tokens.push(aux);
                ultimaComparacion = "JNE";
                break;
          
            case "!!":
                codigo.append("MOV CX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", CX\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JNE ").append(aux.substring(1)).append("\n");
                codigo.append("MOV ").append(aux).append(", 00h\n"); 
                codigo.append(aux.substring(1)).append(":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JE";
                break;
                
            case ">=":
                codigo.append("MOV CX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", CX\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JAE ").append(aux.substring(1)).append("\n"); 
                codigo.append("MOV ").append(aux).append(", 00h\n"); 
                codigo.append(aux.substring(1)).append(":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JB";
               break;
            
            case ">":                
                codigo.append("MOV CX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", CX\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                codigo.append("JA ").append(aux.substring(1)).append("\n"); 
                codigo.append("MOV ").append(aux).append(", 00h\n"); 
                codigo.append(aux.substring(1)).append(":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JLE";
                break;
            
            case "<=":
            	 codigo.append("MOV CX, ").append(op2).append("\n");
                 codigo.append("CMP ").append(op1).append(", CX\n");
                 aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                 codigo.append("MOV ").append(aux).append(", 0FFh\n"); 
                 codigo.append("JBE ").append(aux.substring(1)).append("\n");
                 codigo.append("MOV ").append(aux).append(", 00h\n"); 
                 codigo.append(aux.substring(1)).append(":\n"); 
                 pila_tokens.push(aux);
                 ultimaComparacion = "JA";
                break;
            
            case "<":
            	codigo.append("MOV CX, ").append(op2).append("\n"); 
                codigo.append("CMP ").append(op1).append(", CX\n");
                aux = ocuparAuxiliar(TablaTipos.INT_TYPE);
                codigo.append("MOV " + aux + ", 0FFh\n"); 
                codigo.append("JB " + aux.substring(1) + "\n");  
                codigo.append("MOV " + aux + ", 00h\n");
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                ultimaComparacion = "JAE";
                break;
                     
            default:
                codigo.append("ERROR, se entro a default en operacion de enteros").append("\n");
                break;
        }
    }
    
    
    
    private static void generarOperacionFlotantes(String op1, String op2, String operador) { 

         String aux;
        
         if (TablaDeSimbolos.existeSimbolo(op2 )) {
	         if (TablaDeSimbolos.getUso(op2).equals("Constante"))
	     		op2 = op2.replace('.', '_').replace('-', '_').replace('+', '_').replace('D','E');     
         }   
         if (TablaDeSimbolos.existeSimbolo(op1 )) {
	         if (TablaDeSimbolos.getUso(op1).equals("Constante"))
	     		op1 = op1.replace('.', '_').replace('-', '_').replace('+', '_').replace('D','E');     
         }
    	 op1 = renombre(op1);
         op2 = renombre(op2);

         

         switch (operador) {
             case "+":
                 codigo.append("FLD ").append(op2).append("\n"); 
                 codigo.append("FLD ").append(op1).append("\n");
                 codigo.append("FADD\n");
                 aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                 codigo.append("FSTP ").append(aux).append("\n");
                 pila_tokens.push(aux);
                 break;

             case "-":
                 codigo.append("FLD ").append(op2).append("\n"); 
                 codigo.append("FLD ").append(op1).append("\n");
                 codigo.append("FSUB\n");
                 aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                 codigo.append("FSTP ").append(aux).append("\n");
                 pila_tokens.push(aux);
                 break;
             
             case "*":                
                 codigo.append("FLD ").append(op2).append("\n");
                 codigo.append("FLD ").append(op1).append("\n");    
                 codigo.append("FMUL\n");
                 aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                 generarErrorOverflowProducto(aux);
                 codigo.append("FSTP ").append(aux).append("\n");
                 pila_tokens.push(aux);
                 break;              
     
             case "=":
                 codigo.append("FLD ").append(op1).append("\n");
                 codigo.append("FSTP ").append(op2).append("\n");
                 break;
             
             case "/":
            	    aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
            	    codigo.append("FLD ").append(op2).append("\n");
            	    codigo.append("FLDZ\n");  
            	    codigo.append("FCOMP\n"); 
            	    codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");
            	    codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); 
            	    codigo.append("SAHF").append("\n"); 

            	    generarErrorDivCero(aux);

            	    codigo.append("FLD ").append(op2).append("\n");
            	    codigo.append("FLD ").append(op1).append("\n");
            	    codigo.append("FDIV\n");
            	    codigo.append("FSTP ").append(aux).append("\n");
            	    pila_tokens.push(aux);
            	    break;
             
            
            case "==":
                
            	
            	 codigo.append("FLD ").append(op1).append("\n"); 
                 codigo.append("FCOM ").append(op2).append("\n");
                 codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");
                 codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n");
                 codigo.append("SAHF").append("\n"); 

                 aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                 codigo.append("FLD1 ").append("\n"); 
                 codigo.append("FSTP ").append(aux).append("\n");
                 codigo.append("JE " + aux.substring(1) + "\n"); 
                 codigo.append("FLDZ ").append("\n"); 
                 codigo.append("FSTP ").append(aux).append("\n"); 
                 codigo.append(aux.substring(1) + ":\n");          
                 pila_tokens.push(aux);
            case ">=":
                
             
                codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); 
                codigo.append("SAHF").append("\n"); 

                aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                codigo.append("FLD1 ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n");
                codigo.append("JAE " + aux.substring(1) + "\n"); 
                codigo.append("FLDZ ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n"); 
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                break;
            
            case ">":
            	codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); 
                codigo.append("SAHF").append("\n"); 

                aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                codigo.append("FLD1 ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n"); 
                codigo.append("JA " + aux.substring(1) + "\n");
                codigo.append("FLDZ ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n"); 
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                break;
            
            case "<=":
                codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); 
                codigo.append("SAHF").append("\n"); 

                aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                codigo.append("FLD1 ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n"); 
                codigo.append("JBE " + aux.substring(1) + "\n"); 
                codigo.append("FLDZ ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n"); 
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                break;
            
            case "<":
                codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); 
                codigo.append("SAHF").append("\n");

                aux = ocuparAuxiliar(TablaTipos.DOUBLE_TYPE);
                codigo.append("FLD1 ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n");
                codigo.append("JB " + aux.substring(1) + "\n"); 
                codigo.append("FLDZ ").append("\n"); 
                codigo.append("FSTP ").append(aux).append("\n"); 
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                break;
            
            default:
                codigo.append("ERROR se entro a default al generar codigo para una operacion de flotantes\n");
                break;
        }
    }
    
    
    

    private static void generarSalto(String salto) {
    	
        String direccion = pila_tokens.pop();    

        if (!salto.equals("JMP") && ultimaComparacion.equals("")) {
            String valor = pila_tokens.pop();
            String uso = TablaDeSimbolos.getUso(valor);
            String tipo = TablaDeSimbolos.getTipo(valor);
            
            if (uso.equals("Variable"))
                valor = renombre(valor);
            TablaDeSimbolos.getTablaSimbolos();
   
            if (tipo != "DOUBLE") {
            	codigo.append("MOV ECX, ").append(valor).append("\n");
	            codigo.append("OR ECX, 0\n");
	            codigo.append("JE Label").append(direccion).append("\n");
            }else {
      
            	//codigo.append("FLD " + valor + "\n");
                codigo.append("FLDZ\n");
                codigo.append("FCOM " +  valor + " \n");
              
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); 
                codigo.append("SAHF\n"); 
                codigo.append("JE Label").append(direccion).append("\n");
            	
            }
	            
        } 
        else {
        	if (direccion.contains("Label")) 
        		codigo.append(direccion).append("\n");
        	else
        		codigo.append(salto).append(" Label").append(direccion).append("\n");
        }
        
        ultimaComparacion = "";
    }
    
    
}
        
    
