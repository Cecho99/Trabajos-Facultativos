import java.util.*;

public class Inversion extends Elemento{
	Vector<Elemento> inversiones;
	
	
	public Inversion(String nombre, Cliente c) {
		super(nombre, c);
		inversiones = new Vector<Elemento>();
	}	
	
	
	public void addInversiones(Elemento e) {
		inversiones.add(e);
	}
	
	
	
	public Vector<Elemento> buscar(Filtro f){
		Vector<Elemento> salida = new Vector<Elemento>();
		
		if(f.seCumple(this)) {
			salida.add(this);
		}else {
			for(int i=0; i<inversiones.size(); i++) {
				salida.addAll(inversiones.get(i).buscar(f));
			}
		}
		
		return salida;
	}
	
	
	
	public Vector<Elemento> getInversiones(){
		Vector<Elemento> salida = new Vector<Elemento>();
		
		for(int i=0; i<inversiones.size(); i++) {
			salida.add(inversiones.get(i));
		}
		
		return salida;
	}
	
	
	
	@Override
	public double getValorTotal() {
		double suma = 0;
		
		for(int i=0; i<inversiones.size(); i++) {
			suma += inversiones.get(i).getValorTotal();
		}
		return suma;
	}
	
	

	@Override
	public int getCantidad() {
		int cant=0;
		
		for(int i=0; i<inversiones.size(); i++) {
			cant += inversiones.get(i).getCantidad();
		}
		return cant;
	}
	
	
	
	public Elemento getCopia() {
		Inversion salida = new Inversion(nombre, null);
		
		for(int i=0; i<inversiones.size(); i++) {
			salida.addInversiones(inversiones.get(i).getCopia());
		}
		
		return salida;
	}

}
