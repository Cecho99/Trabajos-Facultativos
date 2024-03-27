package GeneracionDeCodigo;

import java.util.*;

import AnalizadorLexico.*;
import AnalizadorSintatico.Parser;

public class Ambito {
	
	private static List<String> ambitos = new ArrayList<>();
	
	public static void agregarAmbito (String amb) {
			ambitos.add(amb);
			
	}
	
	
	public static String getAmbitoActual() {
		StringBuilder ambito = new StringBuilder();
        for (String a : ambitos){
        	ambito.append("@").append(a);
        }
        return ambito.toString();
	}
	
	
	public static String verificarAmbito(String simbolo) { 
		
		int ind = ambitos.size()-1;
		
        while (ind>=0){
            String aux = simbolo + variableNameMangling(ind);
            if (TablaDeSimbolos.existeSimbolo(aux)){
                TablaDeSimbolos.eliminarToken(simbolo);
                return aux;
            	
            }
            ind--;
        }
        return null;
	}
	
	
	
	public static String variableNameMangling(int indice) {
		StringBuilder nombre = new StringBuilder();
        int i= 0;
        while (i<=indice){
            nombre.append("@").append(ambitos.get(i));
            i++;
        }
        return nombre.toString();
	}
	

	public static String verificarAmbitoClase(String simbolo, String clase) { 

		String[] parts = clase.split("@"); 
        for ( int i = 0; i < parts.length ; i++) {   
        	for (String key : TablaDeSimbolos.getTabla().keySet()) {
        		String aux = simbolo + TablaDeSimbolos.devolverToken(key).getAmbito();       
        		if (TablaDeSimbolos.getClase(key) != null) {        		
        			if (TablaDeSimbolos.getClase(key).equals(parts[i]) && aux.equals(key) && TablaDeSimbolos.getImplementar(key)) {
            			TablaDeSimbolos.eliminarToken(simbolo);
            			return aux;
            		}
        		}      		
        	}
        }
          
        return null;
	}
	

	
	public static boolean esVacio() {
		return ambitos.isEmpty();
	}
	
	public static void removeAmbito() {
	
	
		ambitos.remove(ambitos.size()-1);
	}
	
    public static String quitarAmbito(String simbolo) {
	    if (TablaDeSimbolos.existeSimbolo(simbolo))
    		if (simbolo.contains("@"))
	    		return simbolo.substring(0, simbolo.indexOf('@'));
	    return simbolo;
    }
	
    public static List<String> mostrarAmbitos(){
		return ambitos;
    }
}
