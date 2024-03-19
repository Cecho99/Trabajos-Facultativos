import java.util.Vector;

public class Usuario {
	String nombre;
	String apellido;
	Vector<Elemento> peliculas;
	Filtro preferencia;
	
	public Usuario(String nombre, String apellido, Filtro preferencia) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.preferencia = preferencia;
		peliculas = new Vector<Elemento>();
	}

	
	
	public Filtro getPreferencia() {
		return preferencia;
	}


	public void setPreferencia(Filtro preferencia) {
		this.preferencia = preferencia;
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

	public Vector<Elemento> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Vector<Elemento> peliculas) {
		this.peliculas = peliculas;
	}
	
	
	
}
