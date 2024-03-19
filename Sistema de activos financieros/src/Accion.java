import java.util.Vector;

public class Accion extends Elemento{
	int cantidad;
	double valor;
	
	
	public Accion (String nombre, Cliente c, int cantidad, double valor) {
		super(nombre, c);
		this.cantidad = cantidad;
		this.valor = valor;
	}
	
	
	public Vector<Elemento> buscar(Filtro f){
		Vector<Elemento> salida = new Vector<Elemento>();
		
		if(f.seCumple(this)) {
			salida.add(this);
		}
		
		return salida;
	}
	
	
	@Override
	public double getValorTotal() {
		return cantidad*valor;
	}

	@Override
	public int getCantidad() {
		return cantidad;
	}
	
	
	public Elemento getCopia() {
		return new Accion(nombre, null, cantidad, valor);
	}

}
