
public class CarreraTematica extends Carrera{

	public CarreraTematica(String nombre, String tema, double descuento) {
		super(nombre, tema, descuento);
	}

	
	
	public void addCarreras(ProgramaAcademico p){
		if (p.getTema().equals(this.getTema())) {
			carreras.add(this);
		}
	}
	
	
	
}
