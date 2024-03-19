import java.util.Vector;

public abstract class Carrera extends ProgramaAcademico{	
	Vector<ProgramaAcademico> carreras;
	double descuento;
	
	
	public Carrera(String nombre, String tema, double descuento) {
		super(nombre, tema);
		this.descuento = descuento;
		carreras = new Vector<ProgramaAcademico>();
		
	}


	public Vector<ProgramaAcademico> buscar(Filtro f){
		Vector<ProgramaAcademico> salida = new Vector<ProgramaAcademico>();
		
		for (int i=0; i<carreras.size(); i++) {
			salida.addAll(carreras.get(i).buscar(f));
		
		}
		
		return salida;
	}
	
	
	
	public double getCosto() {
		double costo=0;
		
		for(int i=0; i<carreras.size(); i++) {
			costo += carreras.get(i).getCosto();
		}
		return costo * descuento;
	}
	
	
	
	public int getCreditos() {
		int creditos=0;
		
		for(int i=0; i<carreras.size(); i++) {
			creditos += carreras.get(i).getCreditos();
		}
		return creditos;
	}
	
	
	
	public void addCursos(ProgramaAcademico p) {
		carreras.add(p);
	}
	
	
	public Vector<ProgramaAcademico> getCarreras(){
		Vector<ProgramaAcademico> aux = new Vector<ProgramaAcademico>();
		
		for(int i=0; i <carreras.size(); i++) {
			aux.add(carreras.get(i));
		}
		
		return aux;
	}

	
	
	public double getDescuento() {
		return descuento;
	}
	
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	
}
