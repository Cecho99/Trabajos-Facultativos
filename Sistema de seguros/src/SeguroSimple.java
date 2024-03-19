import java.util.Vector;

public class SeguroSimple extends Seguro{
	String heroe;
	double monto;
	String descripcion;
	double costoMensual;
	
	
	public SeguroSimple(Heroe heroe, int poliza, double monto, String descripcion, double costoMensual) {
		super(poliza, heroe);
		this.monto = monto;
		this.descripcion = descripcion;
		this.costoMensual = costoMensual;
	}
	
	

	@Override
	public String getDescripcion() {
		return descripcion;
	}


	@Override
	public double getMonto() {
		return monto;
	}


	@Override
	public double getCosto() {
		return costoMensual;
	}


	@Override
	public Seguro getCopia() {
		return new SeguroSimple(this.getHeroe(), this.getPoliza(), monto, descripcion, costoMensual);
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
