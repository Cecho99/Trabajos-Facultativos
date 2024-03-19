import java.util.Vector;

public abstract class Elemento {
	String nombre;
	Cliente c;
	Calculador cal;
	
	public Elemento(String nombre, Cliente c) {
		this.nombre = nombre;
		this.c = c;
	}
	
	
	public double calcularCosto() {
		return cal.calcular(this);
	}
	
	
	
	public abstract double getValorTotal();
	public abstract int getCantidad();
	public abstract Vector<Elemento> buscar(Filtro f);
	public abstract Elemento getCopia();
}
