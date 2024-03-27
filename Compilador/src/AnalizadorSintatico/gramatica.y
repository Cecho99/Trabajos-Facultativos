%{
package AnalizadorSintatico;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import AnalizadorLexico.*;
import GeneracionDeCodigo.*;
%}

%token ID CTE INT ULONG DOUBLE CADENA IF ELSE END_IF PRINT CLASS VOID DO UNTIL IMPL FOR RETURN MAYORIGUAL MENORIGUAL DOBLEIGUAL DISTINTO MENOSIGUAL

%start program

%%

program: inicio_program bloque '}'	{Ambito.removeAmbito(); Polaca.agregarPolacaMain("Label"); Polaca.agregarPolacaMain("main"); Polaca.agregarPolacaCompleta(Polaca.getPolacaMain());}  
;

inicio_program: '{' {Ambito.agregarAmbito("main");}  
;

bloque:  bloque sentencia  
		| sentencia        
;

sentencia_declarativa: 	declaracion_variables',' 
		| declaracion_variables		{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
		| declaracion_funcion		{Polaca.agregarPolacaCompleta(Polaca.getPolaca());}
		| declaracion_clase 		{Ambito.removeAmbito(); claseId = null;}	
		| implementacion_metodo
		| declaracion_instancias_clase','		{claseId = null;}
; 

implementacion_metodo: encabeza_impl cuerpo_impl	
;

encabeza_impl: IMPL FOR ID {claseId = $3.sval; ambitoAux = $3.sval; auxID = comprobarAmbito($3.sval); $3.sval = auxID;}
cuerpo_impl:  ':''{'  declaracion_funcion '}' {claseId = null;}
;


declaracion_clase: encabezado_class cuerpo_class    	
				| encabezado_class','	{TablaDeSimbolos.setCuerpo(auxID, false);}
;

encabezado_class: CLASS ID			{claseId = $2.sval; TablaDeSimbolos.setUso($2.sval, "Nombre de clase"); auxID = TablaDeSimbolos.setAmbito($2.sval);Ambito.agregarAmbito($2.sval); $2.sval = auxID; }
;	
cuerpo_class: '{' bloque '}' 				{if (auxID != null)TablaDeSimbolos.setCuerpo(auxID, true);}
				|   '{' bloque ID',' '}'    { TablaDeSimbolos.setHerencia(auxID, $3.sval); TablaDeSimbolos.setUso($3.sval, "Herencia");auxId2 = comprobarAmbito($3.sval); $3.sval = auxId2; if (auxID != null)TablaDeSimbolos.setCuerpo(auxID, true);comprobarAtributos(auxID);}
;
		
declaracion_variables: 	tipo lista_variables  	
;

declaracion_instancias_clase: tipo_clase lista_instancias			
;

tipo_clase: ID 					{tipoAux = $1.sval; auxID = comprobarAmbito($1.sval); $1.sval = auxID;  }
;

lista_instancias: ID 							{TablaDeSimbolos.setUso($1.sval, "Instancia");$1.sval = TablaDeSimbolos.setAmbito($1.sval); TablaDeSimbolos.setTipo($1.sval, tipoAux); Polaca.agregarPolaca($1.sval);Polaca.agregarPolaca("inst");}
				| lista_instancias ';' ID		{ TablaDeSimbolos.setUso($3.sval, "Instancia");$3.sval = TablaDeSimbolos.setAmbito($3.sval); TablaDeSimbolos.setTipo($3.sval, tipoAux); Polaca.agregarPolaca($3.sval); Polaca.agregarPolaca("inst");}
; 

lista_variables:	 ID    {usoClaseVariable($1.sval);$1.sval = TablaDeSimbolos.setAmbito($1.sval); TablaDeSimbolos.setTipo($1.sval, tipoAux);TablaDeSimbolos.setClase($1.sval, claseId);}
			 | lista_variables ';' ID {usoClaseVariable($3.sval);$3.sval = TablaDeSimbolos.setAmbito($3.sval);TablaDeSimbolos.setTipo($3.sval, tipoAux);TablaDeSimbolos.setClase($3.sval, claseId);}
;   


tipo: INT		{tipoAux = $1.sval;}
     | ULONG		{tipoAux = $1.sval;}
     | DOUBLE		{tipoAux = $1.sval;}
;

declaracion_funcion: encabezado  cuerpo_funcion    {Ambito.removeAmbito(); }
					| encabezado  ','			   {TablaDeSimbolos.setImplementar(funcAux, false);Ambito.removeAmbito();TablaDeSimbolos.getTablaSimbolos();funcAux = null;}
;

encabezado :VOID ID '('')'		{  Polaca.agregarPolacaCompleta(Polaca.getPolaca());usoClaseFuncion($2.sval); funcAux = TablaDeSimbolos.setAmbito($2.sval);Ambito.agregarAmbito($2.sval);$2.sval = funcAux; TablaDeSimbolos.setAnidamiento($2.sval); TablaDeSimbolos.setClase($2.sval, claseId);verificarAnidamiento($2.sval);Polaca.agregarPolaca($2.sval); Polaca.agregarPolaca("Label");}
				| VOID ID '(' tipo ID ')' {Polaca.agregarPolacaCompleta(Polaca.getPolaca());  TablaDeSimbolos.setUso($2.sval, "Nombre de funcion"); funcAux = TablaDeSimbolos.setAmbito($2.sval);Ambito.agregarAmbito($2.sval);$2.sval = funcAux; TablaDeSimbolos.setParametro($2.sval, tipoAux); TablaDeSimbolos.setUso($5.sval, "Parametro");TablaDeSimbolos.setTipo($5.sval, tipoAux);auxId2 = TablaDeSimbolos.setAmbito($5.sval); $5.sval = auxId2;TablaDeSimbolos.setClase($2.sval, claseId);verificarAnidamiento($2.sval); Polaca.agregarPolaca($2.sval); Polaca.agregarPolaca("Label");TablaDeSimbolos.setParametroNombre($5.sval,$2.sval);}
;	




cuerpo_funcion: '{' bloque '}'  {TablaDeSimbolos.setImplementar(funcAux, true);}
				
;


sentencia: sentencia_declarativa
   			| sentencia_ejecutable           
    		| error ','                      {erroresSintacticos.add("Error en la linea " + Integer.toString(AnalizadorLexico.getlinea() ) );}
;

lista_sentencias_ejecutables: lista_sentencias_ejecutables sentencia_ejecutable
				| sentencia_ejecutable
;

sentencia_ejecutable: declaracion_asignacion','	
			  | declaracion_asignacion  		{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
			  | invocacion_funcion','			
			  | declaracion_if','		
			  | declaracion_if					{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
			  | sentencia_print','		
  			  | sentencia_print         		{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
	  		  | sentencia_return','			
  			  | sentencia_return                {erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
  			  | sentencia_do_until',' 
 			  | sentencia_do_until              {erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
 			  | referencia_a_metodos','
 			  | referencia_a_metodos			{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
 			  | referencia_a_atributos','
  			  | referencia_a_atributos	    	{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));} 			  
;  

 referencia_a_metodos:  ID  met_referenciados {auxID = comprobarAmbito($1.sval);$1.sval = auxID;herenciaAux = TablaDeSimbolos.getTipo($1.sval) + "@" + herenciaAux; funcAux = comprobarAmbitoClase(funcAux); Polaca.agregarPolaca($1.sval); Polaca.agregarPolaca(funcAux); Polaca.agregarPolaca("CALL");herenciaAux = null;}
;

 met_referenciados: '.' ID'('')' 		{funcAux = $2.sval;}   
 				|'.' ID'('expresion_aritmetica')' {funcAux = $2.sval;}
				|'.' ID met_referenciados		{herenciaAux = herenciaAux + "@" + $2.sval ;}
;


referencia_a_atributos: ID atr_referenciados '='  expresion_aritmetica {auxID = comprobarAmbito($1.sval);$1.sval = auxID; herenciaAux = TablaDeSimbolos.getTipo($1.sval) + "@" + herenciaAux; funcAux = comprobarAmbitoClase(funcAux); Polaca.agregarPolaca($1.sval);Polaca.agregarPolaca( funcAux); Polaca.agregarPolaca($3.sval);herenciaAux = null;}
;


atr_referenciados: 	'.'ID 				{funcAux = $2.sval;}
			|'.'ID atr_referenciados	{herenciaAux = herenciaAux + "@" + $2.sval;}
;


declaracion_asignacion: ID '=' expresion_aritmetica  	{TablaDeSimbolos.setUso($1.sval, "Variable");auxID = comprobarAmbito($1.sval); $1.sval = auxID; Polaca.agregarPolaca($1.sval); Polaca.agregarPolaca($2.sval);}
     | ID MENOSIGUAL expresion_aritmetica  			    {TablaDeSimbolos.setUso($1.sval, "Variable");auxID = comprobarAmbito($1.sval); $1.sval = auxID; Polaca.agregarPolaca($1.sval); Polaca.agregarPolaca("-"); Polaca.agregarPolaca($1.sval); Polaca.agregarPolaca("=");}
;


expresion_aritmetica: expresion_aritmetica '+' termino  	 {Polaca.agregarPolaca("+");}
   			| expresion_aritmetica '-' termino     	 {Polaca.agregarPolaca("+");}
			| termino		
;
	
termino:   termino '*' factor       		{Polaca.agregarPolaca("*");} 
		| termino '/' factor 			  {Polaca.agregarPolaca("/");}
       	| factor 	
;


factor: ID 		{TablaDeSimbolos.setUso($1.sval, "Variable");auxID = comprobarAmbito($1.sval); $1.sval = auxID; tipoAux = TablaDeSimbolos.getTipo($1.sval); Polaca.agregarPolaca($1.sval);  TablaDeSimbolos.setUsadoDerecha($1.sval, true);}
	| CTE		{TablaDeSimbolos.setUso($1.sval, "Constante"); tipoAux = TablaDeSimbolos.getTipo($1.sval);Polaca.agregarPolaca($1.sval);}
	| '-'CTE		{ChequearRangoNegativo($2.sval);$$.sval = "-" + $2.sval;TablaDeSimbolos.setUso($1.sval, "Constante"); tipoAux = TablaDeSimbolos.getTipo($2.sval); Polaca.agregarPolaca($1.sval);}	
;

invocacion_funcion: ID '(' expresion_aritmetica ')' {auxID = comprobarAmbito($1.sval);$1.sval = auxID;TablaDeSimbolos.noTieneParametros($1.sval); verificarParametros($1.sval); Polaca.agregarPolaca($1.sval); Polaca.agregarPolaca("CALL"); } 
			| ID '(' ')'				{auxID = comprobarAmbito($1.sval); $1.sval = auxID;TablaDeSimbolos.tieneParametros($1.sval); Polaca.agregarPolaca($1.sval); Polaca.agregarPolaca("CALL");}
;


declaracion_if:   	IF  condicion_if cuerpo_if  END_IF 			 {int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size()));}	
                  | IF  condicion_if cuerpo_if cuerpo_else END_IF 	 {int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size()));}
;

condicion_if:	 '(' expresion_aritmetica operador expresion_aritmetica ')' {Polaca.agregarPolaca($3.sval); Polaca.apilar(); Polaca.agregarPolaca(""); Polaca.agregarPolaca("#BF");}
				| '(' expresion_aritmetica operador expresion_aritmetica 	{erroresSintacticos.add("Falta un ) en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
				|  expresion_aritmetica operador expresion_aritmetica ')'	{erroresSintacticos.add("Falta un ( en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
;


operador:		'<' 
			| MENORIGUAL
			| '>' 
			| MAYORIGUAL
			| DOBLEIGUAL
			| DISTINTO
;
  
cuerpo_if:  sentencia_ejecutable         {int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size() + 4)); Polaca.apilar();  Polaca.agregarPolaca(""); Polaca.agregarPolaca("#BI"); Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2 ) + ": "); Polaca.agregarPolaca("#BI");}      		  
             | '{' lista_sentencias_ejecutables '}'     {int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size() + 4)); Polaca.apilar();  Polaca.agregarPolaca(""); Polaca.agregarPolaca("#BI"); Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2 )+ ": "); Polaca.agregarPolaca("#BI");}
;


cuerpo_else:	ELSE  sentencia_ejecutable 			 {Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2) + ": "); Polaca.agregarPolaca("#BI");}
		|  ELSE  '{' lista_sentencias_ejecutables '}'		 {Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2)+ ": "); Polaca.agregarPolaca("#BI");}
;

sentencia_return: RETURN		{Polaca.agregarPolaca($1.sval);};
;

sentencia_print: PRINT CADENA	{Polaca.agregarPolaca($2.sval); Polaca.agregarPolaca($1.sval);}
                        
                                 | PRINT        {erroresSintacticos.add("falta la cadena a imprimir en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
			
;

sentencia_do_until: sentencia_do '{'lista_sentencias_ejecutables'}' UNTIL '(' expresion_aritmetica operador expresion_aritmetica ')' 	{ Polaca.agregarPolaca($8.sval); Polaca.apilar(); int posicion1 = Polaca.pila.pop(); int posicion = Polaca.pila.pop(); Polaca.agregarPolaca(String.valueOf(posicion)); Polaca.agregarPolaca("#BF"); Polaca.agregarPolaca(String.valueOf(posicion1+4)); Polaca.agregarPolaca("#BI"); Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size()) + ": "); Polaca.agregarPolaca("#BI");}     
;

sentencia_do: DO  	{Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size()+2) + ": "); Polaca.agregarPolaca("#BI"); Polaca.apilar();}
;




%%


public static ArrayList<String> erroresLexicos = new ArrayList<String>();

public static ArrayList<String> erroresSintacticos = new ArrayList<String>();

public static ArrayList<String> erroresSemanticos = new ArrayList<String>();

public static String tipoAux = "";

String dirArchivo = "";

String auxID = "";

String atrAux = "";

String auxId2 = "";

String funcAux ="";

String herenciaAux = "";

String ambitoAux ="";

BufferedReader reader;

int token = -1;
	
String claseId = null;
		
public int yylex() throws IOException {
	reader = AnalizadorLexico.getReader();
	while (!AnalizadorLexico.endOfFile(reader)) {
		token = AnalizadorLexico.getToken(reader);
		if (token != -1){
			yylval = new ParserVal(AnalizadorLexico.getToken_actual().toString());
		
			AnalizadorLexico.resetToken();

			return token;
		}
	}
	return token;
}

public void verificarAnidamiento(String simbolo){
	if (TablaDeSimbolos.getClase(simbolo) != null){
		if (TablaDeSimbolos.getAnidamiento(simbolo) > 2 ){
			erroresSemanticos.add("Error en la linea " + AnalizadorLexico.getlinea() + " : se pasa de la cantidad maxima de anidamiento");	
		}
	}
}

public String comprobarAmbito(String simbolo){
	String aux = Ambito.verificarAmbito(simbolo);
	if (!TablaDeSimbolos.getCuerpo(aux)){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
		return null;
	}
	
	if (!TablaDeSimbolos.getImplementar(aux)){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
		return null;
	}
	
	if (aux == null){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
	}	
	return aux;
}



public String comprobarAmbitoClase(String simbolo){

	String aux = Ambito.verificarAmbitoClase(simbolo, herenciaAux);
	
	if (aux == null){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
	}	
	if (herenciaAux != null){
		String [] a = herenciaAux.split("@");
		for (String spliteado : a ){
			if (TablaDeSimbolos.existeSimbolo(spliteado)){
				TablaDeSimbolos.eliminarToken(spliteado);
			}
		}
	}
	return aux;
}




public void verificarParametros(String simbolo){
	if (TablaDeSimbolos.getParametro(simbolo) != null){
		if (!TablaDeSimbolos.getParametro(simbolo).equals(tipoAux)){
			erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : los tipos no coinciden ");
		}
	}else{
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : los tipos no coinciden ");
	}
	
}




private void usoClaseVariable(String lexema){
        String[] parts = Ambito.getAmbitoActual().split("@");
        if (claseId != null && parts.length <=3){
                TablaDeSimbolos.setUso(lexema, "Atributo");
                 if (TablaDeSimbolos.getHerencia(claseId) != null){                
                         String aux = lexema + "@main:" + TablaDeSimbolos.getHerencia(claseId);
                         if (TablaDeSimbolos.devolverToken(aux) != null){
                                 erroresSemanticos.add("Error : el atributo" + lexema + " ya fue declarado");
                         }
                 }
        }else{
                TablaDeSimbolos.setUso(lexema, "Variable");
        }
}

private void comprobarAtributos(String lexema){
       
        if(TablaDeSimbolos.getHerencia(lexema)!=null){
                
                for (String key : TablaDeSimbolos.getTabla().keySet()) {
                    if (TablaDeSimbolos.getClase(key)!= null && TablaDeSimbolos.getUso(key)!= null){
                  		if (TablaDeSimbolos.getClase(key).equals(claseId) && TablaDeSimbolos.getUso(key).equals("Atributo")){
                                    
                                    for (String simbolo : TablaDeSimbolos.getTabla().keySet()) {
                                      String padre = TablaDeSimbolos.getHerencia(lexema);
                                      
                                      if (TablaDeSimbolos.getClase(simbolo)!= null && TablaDeSimbolos.getUso(simbolo)!= null){
                                              
                                              String[] partsKey = key.split("@");
                                              String[] partsSimb = simbolo.split("@");
                                              
                                                    if (TablaDeSimbolos.getClase(simbolo).equals(padre) && TablaDeSimbolos.getUso(simbolo).equals("Atributo") && partsKey[0].equals(partsSimb[0]) ){
                                                            erroresSemanticos.add("Error : el simbolo " + key + " ya fue declarado" );
                                                    }
                                            }
                              }
                            }
                    }                                    
            }
        }
}


private void usoClaseFuncion(String lexema){
        String[] parts = Ambito.getAmbitoActual().split("@");
        if (claseId != null && parts.length <=3){
                TablaDeSimbolos.setUso(lexema, "Nombre de metodo");
        }else{
                TablaDeSimbolos.setUso(lexema, "Nombre de funcion");
        }
}




public static void getErrores() {
	System.out.println(" ");
	if (erroresLexicos.size() != 0) {
		System.out.println("ERRORES LEXICOS");
		for (String s : erroresLexicos) {
			System.out.println(s);
		}
	}
	else {
		System.out.println("No hay errores lexicos");
	}
	
	System.out.println(" ");
	System.out.println(" ");
	if (erroresSintacticos.size() != 0) {
		System.out.println("ERRORES SINTACTICOS");
		for (String s : erroresSintacticos) {
			System.out.println(s);
		}
	}
	else {
		System.out.println("No hay errores sintacticos");
	}
	System.out.println(" ");
	System.out.println(" ");
	if (erroresSemanticos.size() != 0) {
		System.out.println("ERRORES SEMANTICOS");
		for (String s : erroresSemanticos) {
			System.out.println(s);
		}
	}
	else {
		System.out.println("No hay errores semanticos");
	}
	
}


private void yyerror(String mensaje) {
	System.out.println("Error yacc: " + mensaje);
}




private void ChequearRangoNegativo(String numNegativo) { 
    String valor = "";
    String aux = numNegativo;
    if (numNegativo.contains("_i")) { 
    	for (int i=0; i < numNegativo.length() ; i++) {
			if (numNegativo.charAt(i) != '_') { 
				valor += numNegativo.charAt(i); 
			}else {
				break;
			}
		}
		int v = Integer.parseInt("-" + valor); 
		if( v < -32768){
			erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango minimo ");
		}
		Token tkn = new Token(AnalizadorLexico.getPR("INT"));
	    TablaDeSimbolos.agregarSimbolo("-" + numNegativo, tkn);		
    }
    else {
    	if (numNegativo.contains("_ul")) {
    		for (int i=0; i < numNegativo.length() ; i++) {
				if (numNegativo.charAt(i) != '_') { 
					valor += numNegativo.charAt(i); 
				}else {
					break;
				}
			}
    		
			int v = Integer.parseInt("-" + valor); 
			if( v < 0) {  		
				Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango minimo");
			}
			Token tkn = new Token(AnalizadorLexico.getPR("ULONG"));
		    TablaDeSimbolos.agregarSimbolo(numNegativo, tkn);
    	} 
    	else { 
	    	String valorentero = "";
			String valorexponente = "";
			int tamañoentero = 0;
			
			for (int i = 0; i < numNegativo.length() ; i++) {
				if (numNegativo.charAt(i) == 'D' || numNegativo.charAt(i) == 'd') {
					break;
				}else {
					valorentero += numNegativo.charAt(i);
					tamañoentero++;
				}			
			}
			double ve = Double.parseDouble(valorentero);
		
		
			if (numNegativo.toString().contains("D") || numNegativo.toString().contains("d")) {
				for (int i = tamañoentero + 2; i < numNegativo.length() ; i++) {
					valorexponente += numNegativo.charAt(i);
				}
		
				int vex = Integer.parseInt("-"+ valorexponente);
				double power = Math.pow(ve, vex);
				if(power > Math.pow(AnalizadorLexico.getDoubleMaxLong(), AnalizadorLexico.getDoubleMaxLongExp())) { 
					Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango minimo");
				}
			}
			
			Token tkn = new Token(AnalizadorLexico.getPR("DOUBLE"));
		    TablaDeSimbolos.agregarSimbolo("-" + numNegativo, tkn);
		    TablaDeSimbolos.eliminarToken(valorexponente);
		}
	}
 
}

 
      