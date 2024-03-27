package AnalizadorLexico;

import java.util.*;

import AnalizadorSintatico.*;
import GeneracionDeCodigo.*;

public class TablaDeSimbolos {
	private static Map <String, Token> tabla;
	
	public TablaDeSimbolos() {
		tabla = new HashMap<>();
		
	}

	public static void agregarSimbolo(String lexema, Token token) { 
			tabla.put(lexema, token); 
		
	}

	public static Token devolverToken(String clave) {
		if(tabla.containsKey(clave)) {
			return tabla.get(clave);
		}
		return null;
	}

	
	public static void eliminarToken(String string) {  
		tabla.remove(string);              
	}
	
	
	public static void getTablaSimbolos() {
		System.out.println("TABLA DE SIMBOLOS");
		for (String key : tabla.keySet()) {
			Token value = tabla.get(key);
			System.out.println("Lexema: " + key + "   " + "Token: " + value );
		}
	}
	
	public static boolean existeSimbolo(String lexema) {
		return tabla.containsKey(lexema);
	}
	
	public static void setTipo(String lexema, String tipo) {
		if (existeSimbolo(lexema)) {
			tabla.get(lexema).setTipo(tipo);
		}
	}
	
	public static String getTipo(String lexema) {
	
		return tabla.get(lexema).getTipo();
    
	
	}

	public static String getParametroNombre(String lexema) {
		if (existeSimbolo(lexema)) {
			return tabla.get(lexema).getParametroNombre();
		}
		return null;
	}

	public static void setParametroNombre(String lexema, String nombre) {
		if (existeSimbolo(lexema)) {
			tabla.get(lexema).setParametroNombre(nombre);
		}
	}
	
	public static void setAnidamiento(String lexema) {
		if (existeSimbolo(lexema)) {
			int counter = 0;
			String ambito = devolverToken(lexema).getAmbito();
			String[] parts = ambito.split("@");
			
			for (int i = 2 ; i < parts.length ; i++) {
				counter++;
				
			}
			tabla.get(lexema).setAnidamiento(counter);
		}
	}
	
	public static int getAnidamiento(String lexema) {
		if(existeSimbolo(lexema)) {
			return tabla.get(lexema).getAnidamiento();
		}
		return 0;
	}
	
	
	
	public static void setUsadoDerecha(String lexema, boolean usado) {
		if (existeSimbolo(lexema)) {
			tabla.get(lexema).setUsadoDerecha(usado);
		}
	}
	
	public static boolean getUsadoDerecha(String lexema) {

		if(existeSimbolo(lexema)) {
			return tabla.get(lexema).isUsadoDerecha();
		}
		return false;
	}
	
	
	public static void setCuerpo(String lexema, boolean dist) {
	
		tabla.get(lexema).setCuerpo(dist);
		
	}
	
	public static boolean getCuerpo(String lexema) {
		if(existeSimbolo(lexema)) {
			return tabla.get(lexema).getCuerpo();
		}
		return false;
	}
	
	
	
	public static void setUso(String lexema, String uso) {
		if (existeSimbolo(lexema)) {
			tabla.get(lexema).setUso(uso);
		}
	}
	
	public static void setParametro(String lexema, String parametro) {
		if (existeSimbolo(lexema)) {
			tabla.get(lexema).setParametro(parametro);
		}
	}
	
	public static String setAmbito(String lexema) {
		String s = lexema + Ambito.getAmbitoActual(); 
		
		if (devolverToken(lexema).getUso().equals("Nombre de metodo") ) {	
			for (String key : tabla.keySet()) {
				String aux = lexema + (devolverToken(key).getAmbito());
				if (devolverToken(key).getUso()!=null)	{
					if (devolverToken(key).getUso().equals("Nombre de metodo") && !devolverToken(key).isImplementar() && aux.equals(key) ) {
						Token tk = devolverToken(key);
						eliminarToken(lexema);
						tk.setImplementar(true);
						agregarSimbolo(key, tk);
						return s;
					}else {
						if  (devolverToken(key).getUso().equals("Nombre de metodo") && devolverToken(key).isImplementar() && aux.equals(key) ) {
							Parser.erroresSemanticos.add("Error : el método ya fue implementado");
							eliminarToken(lexema);
							return null;
						}
					}
				}
				
			}
		}
		
		if (existeSimbolo(s)) {
			
			if (devolverToken(s).getCuerpo()) {
				Parser.erroresSemanticos.add("Error en la linea " + AnalizadorLexico.getlinea() + " : Redeclaracion de " + devolverToken(s).getUso());
				tabla.remove(lexema);
				return null;
			}else { 
				if (!devolverToken(s).getCuerpo()) {
					Token tk = devolverToken(lexema);
					eliminarToken(lexema);
					tk.setAmbito(Ambito.getAmbitoActual());
					agregarSimbolo(s, tk);
					return s;
				}
			}
			Parser.erroresSemanticos.add("Error en la linea " + AnalizadorLexico.getlinea() + " : Redeclaracion de " + devolverToken(s).getUso());
			tabla.remove(lexema);
			return null;
		}
	
		Token tk = devolverToken(lexema);
		eliminarToken(lexema);
		tk.setAmbito(Ambito.getAmbitoActual());
		agregarSimbolo(s, tk);
		return s;
	}
	
	
	
	public static String getClase(String lexema) {
		if (existeSimbolo(lexema)) {
			return tabla.get(lexema).getClase();
		}
		return null;
	}
	
	public static void setClase(String lexema, String clase) {
	
		if (existeSimbolo(lexema)) {
 			tabla.get(lexema).setClase(clase);
		}
	}
	
	public static String getHerencia(String lexema) {
		if (existeSimbolo(lexema)) {
			return tabla.get(lexema).getHerencia();
		}
		return null;
	}
	
	public static void setHerencia(String lexema, String herencia) {
		if (existeSimbolo(lexema)) {
			tabla.get(lexema).setHerencia(herencia);
		}
	}
	
	public static void tieneParametros(String lexema) {

		if (existeSimbolo(lexema)) {
			if (tabla.get(lexema).getParametro() != null) {
				
				Parser.erroresSemanticos.add("Error en la linea " + AnalizadorLexico.getlinea() + " : La funcion debe tener parametros");
			}
		
		}
	
	}
	
	public static String getParametro(String lexema) {
		if (existeSimbolo(lexema)) {
			return tabla.get(lexema).getParametro();
		}
		return null;
	}
	
	public static void noTieneParametros(String lexema) {
		if (existeSimbolo(lexema)) {
			if (tabla.get(lexema).getParametro() == null) {
				Parser.erroresSemanticos.add("Error en la linea: " + AnalizadorLexico.getlinea() + " La funcion no debe tener parametros");
			}
		}
	
	}
	
	public static void setImplementar(String lexema, boolean bool) {
		if (existeSimbolo(lexema)) {
			tabla.get(lexema).setImplementar(bool);
		}
	}
	
	public static boolean getImplementar(String lexema) {
		if (existeSimbolo(lexema)) {
			return tabla.get(lexema).isImplementar();
		}
		return false;
	}

	public static Map<String , Token> getTabla() {
		// TODO Auto-generated method stub
		return tabla;
	}

	
	public static String getUso(String valor) {
		return tabla.get(valor).getUso();
	}
}