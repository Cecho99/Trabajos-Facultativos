import java.util.Vector;
import java.time.LocalDate;

public class Contrato extends Elemento{
	Elemento e;
	LocalDate fecha;
	
	public Contrato (String nombre, Cliente c, Elemento e, LocalDate fecha) {
		super(nombre, c);
		this.e = e;
		this.fecha = fecha;
	}
	


	public Vector<Elemento> buscar(Filtro f){
		Vector<Elemento> salida = new Vector<Elemento>();
		 LocalDate fechaActual = LocalDate.now();
		 
		if(f.seCumple(this) && fecha.isAfter(fechaActual)) {
			salida.add(this);
		}
		return salida;
	}
	
	
	@Override
	public double getValorTotal() {
		return e.getValorTotal() - 0.01;
	}


	@Override
	public int getCantidad() {
		return 1;
	}
	
	
	
	public Elemento getCopia() {
		return new Contrato(nombre, null, e, fecha);
	}
	
}
