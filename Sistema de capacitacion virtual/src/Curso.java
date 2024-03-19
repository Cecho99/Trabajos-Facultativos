import java.util.Vector;

public class Curso extends ProgramaAcademico{
	int creditos;
	double costo;
	Nota nota;
	
	
	public Curso(String nombre, String tema, int creditos, double costo) {
		super(nombre, tema);
		this.creditos = creditos;
		this.costo = costo;
	}
	

	public Nota getNota() {
		return nota;
	}
	
	public int getCreditos() {
		return creditos;
	}
	
	public void setCreditos(int numCreditos) {
		this.creditos = numCreditos;
	}
	
	public double getCosto() {
		return costo;
	}
	
	public void setCosto(double costo) {
		this.costo = costo;
	}


	@Override
	public Vector<ProgramaAcademico> buscar(Filtro f) {
		Vector<ProgramaAcademico> salida = new Vector<ProgramaAcademico>();
		if (f.seCumple(this)) {
			salida.add(this);
		}
		
		return salida;
	}

	
}
