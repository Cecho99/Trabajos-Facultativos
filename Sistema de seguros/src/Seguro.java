import java.util.*;

public abstract class Seguro {
	int poliza;
	Heroe heroe;
	
	public Seguro (int poliza, Heroe heroe) {
		this.poliza = poliza;
		this.heroe = heroe;
	}
	
	
	public Heroe getHeroe() {
		return heroe;
	}
	
	public int getPoliza() {
		return poliza;
	}
	
	public abstract String getDescripcion();
	public abstract double getMonto();
	public abstract double getCosto();
	public abstract Seguro getCopia();
	public abstract Vector<Seguro> buscarSeguros(Heroe h);
}
