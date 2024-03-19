import java.util.*;


public class Agrupacion extends ElementoHeroe{
	Vector<ElementoHeroe> agrupaciones;
	
	
	public Agrupacion(String nombre) {
		super(nombre);
		agrupaciones = new Vector<ElementoHeroe>();
	}
	
	
	
	public Vector<ElementoHeroe> buscar(Seguro ss){
		Vector<ElementoHeroe> salida = new Vector<ElementoHeroe>();
		
		for (int i=0; i<agrupaciones.size(); i++) {
			salida.addAll(agrupaciones.get(i).buscar(ss));
		}
		return salida;
	}
	
	
	public int getEdad() {
		int suma=0;
		
		for(int i=0; i<agrupaciones.size(); i++) {
			suma += agrupaciones.get(i).getEdad();
		}
		
		return suma;
	}
	
	
}
