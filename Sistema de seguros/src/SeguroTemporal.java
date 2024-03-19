import java.time.LocalDate;
import java.util.Vector;


public class SeguroTemporal extends Seguro{
	LocalDate fechaInicio;
	LocalDate fechaFin;
	Seguro ss;
	double descuento;
	
	
	public SeguroTemporal(int poliza, Heroe heroe, LocalDate fechaInicio, LocalDate fechaFin, Seguro ss) {
		super(poliza, heroe);
		this.fechaFin = fechaFin;
		this.fechaInicio = fechaInicio;
		this.ss = ss;
	}
	
	
	
	
	
	@Override
	public String getDescripcion() {
		return ss.getDescripcion();
	}
	
	
	
	@Override
	public double getMonto() {
		LocalDate fechaActual = LocalDate.now();
		
		if (fechaInicio.isBefore(fechaActual) && fechaFin.isAfter(fechaActual)) {
			return ss.getMonto();
		}
		
		return 0;
	}
	
	
	
	
	@Override
	public double getCosto() {
		return ss.getCosto()*descuento;
	}



	@Override
	public Seguro getCopia() {
		return new SeguroTemporal(this.getPoliza(), this.getHeroe(), fechaInicio, fechaFin, ss);
	}



	@Override
	public Vector<Seguro> buscarSeguros(Heroe h) {
		Vector<Seguro> salida = new Vector<Seguro>();
		
		if(h.estaInteresado(this)) {
			salida.add(this);
		}
		return salida;
	}
	
}
