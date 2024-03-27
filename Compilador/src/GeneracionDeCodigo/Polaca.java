package GeneracionDeCodigo;
import java.util.ArrayList;
import java.util.*;

public class Polaca {
	
	public static final List<List<String>> polacaCompleta = new ArrayList<>();
	public static final List<String> polaca = new ArrayList<>();
	public static final List<String> polacaMain = new ArrayList<>();
	public static final Stack<Integer> pila = new Stack<>();	
	
	public Polaca() {
		
	}

	
	public static void imprimirPolacaCompleta() {
		if (!polacaCompleta.isEmpty()) {
                System.out.println();
                System.out.println("Polaca:");

               	for (int j = 0; j < polacaCompleta.size(); j++) { 
		            for (int i = 0; i < polacaCompleta.get(j).size(); ++i) {
		                System.out.println(i + " " + polacaCompleta.get(j).get(i));
		            }
		            System.out.println("  ");
		            System.out.println("  ");
	                }
                }
	}
	
	
	public static void agregarPolaca(String token) {
		if (Ambito.getAmbitoActual().equals("@main")) {
			polacaMain.add(token);
		}
		else {
			polaca.add(token);
		}
	}
	
	
	public static void agregarPolacaMain(String label) {
		if (!polacaMain.isEmpty())
			polacaMain.add(0,label);
	}
	
	
	
	
	
	public static void agregarPolacaCompleta(List<String> p) {
		if (!p.isEmpty()) {
			polacaCompleta.add(p);
	        polaca.clear();
		}
	}
	
	
	public static void apilar(){
         pila.push(polaca.size());
	}
	
	

	
	public static List<String> getPolaca() {
		List<String> aux = new ArrayList<>();
		
		for (int i = 0; i < polaca.size(); ++i) {
			aux.add(polaca.get(i));
		}
		return aux;
	}
	
	
	public static List<String> getPolacaMain() {
		List<String> aux = new ArrayList<>();
		
		for (int i = 0; i < polacaMain.size(); ++i) {
			aux.add(polacaMain.get(i));
		}
		return aux;
	}


}