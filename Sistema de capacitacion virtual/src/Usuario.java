import java.util.Vector;

public class Usuario {
	String nombre;
	String apellido;
	Vector<Curso> cursos;
	Nota nota;
	
	
	public Vector<Curso> getCursos(){
		Vector<Curso> aux = new Vector<Curso>();
		
		for(int i=0; i<cursos.size(); i++) {
			aux.add(cursos.get(i));
		}
		return aux;
	}
	
	

	public boolean aprobo() {
		for (int i=0; i<cursos.size(); i++) {
			if (cursos.get(i).getNota().getNota() < nota.getNota()) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	public Nota getNota() {
		return nota;
	}


	public void setNota(Nota nota) {
		this.nota = nota;
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	
}
