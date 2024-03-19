
public class CursoAMedida extends Curso{

	public CursoAMedida(String nombre, String tema, int creditos, double costo) {
		super(nombre, tema, creditos, costo);
	}
	
	
	public double getCosto() {
		return getCosto()*getCreditos();
	}

}
