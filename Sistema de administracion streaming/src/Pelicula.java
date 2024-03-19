import java.util.Vector;

public class Pelicula extends Elemento{
	String titulo;
	int duracion;
	int calificacion;
	Vector<String> actores;

	public Pelicula(int id, String titulo, int duracion, int calificacion) {
		super(id);
		this.titulo = titulo;
		this.duracion = duracion;
		this.calificacion = calificacion;
		actores = new Vector<String>();
	}




	@Override
	public Elemento getCopiaRestringida(Filtro f) {	
		if (f.seCumple(this)) {
			Pelicula p = new Pelicula(this.id, this.titulo, this.duracion, this.calificacion);
			for(int i=0; i<actores.size(); i++) {
				p.addActores(actores.get(i));
			}
			
			return p;
		}
		return null;
	}
	
	
	public void addActores(String a) {
		actores.add(a);
	}
	
	
	
	public Vector<String> getActores(){
		Vector<String> salida = new Vector<String>();
		
		for(int i=0; i<actores.size(); i++) {
			salida.add(actores.get(i));
		}
		return salida;
	}
	
	
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public int getCalificacion() {
		return calificacion;
	}
	
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}


	
}
