import java.util.Vector;

public class SeguroAgrupador extends Seguro{
	Vector<Seguro> seguros;
	double descuento;
	
	public SeguroAgrupador(int poliza, Heroe heroe, double descuento) {
		super(poliza, heroe);
		this.descuento = descuento;
		seguros = new Vector<Seguro>();
	}
	
	
	public void addSeguro(Seguro s) {
		seguros.add(s);
	}
	
	
	
	@Override
	public String getDescripcion() {
		String salida = new String();
	
		for (int i=0; i<seguros.size(); i++) {
			salida += " " + seguros.get(i).getDescripcion();
		}
		
		return salida;
	}

	
	
	@Override
	public double getMonto() {
		double suma = 0;
		
		for(int i=0; i<seguros.size(); i++) {
			suma += seguros.get(i).getMonto();
		}
		
		return suma;
	}

	
	@Override
	public double getCosto() {
		double suma = 0;
		
		for(int i=0; i<seguros.size(); i++) {
			suma += seguros.get(i).getCosto();
		}
		
		return suma*descuento;
	}


	@Override
	public Seguro getCopia() {
		SeguroAgrupador ss =  new SeguroAgrupador(this.getPoliza(), this.getHeroe(), descuento);
		
		for(int i=0; i<seguros.size(); i++) {
			ss.addSeguro(seguros.get(i).getCopia());
		}

		return ss;
	}


	@Override
	public Vector<Seguro> buscarSeguros(Heroe h) {
		Vector<Seguro> salida = new Vector<Seguro>();
		
		for (int i=0; i<seguros.size(); i++) {
			salida.addAll(seguros.get(i).buscarSeguros(h));
		}
		
		return salida;
	}
	
	
	
	
}
