import java.util.Vector;

public abstract class Elemento {
	int id;
	
	public Elemento(int id) {
		this.id = id;
	}
	
	public abstract int getDuracion();
	public abstract int getCalificacion();
	public abstract Vector<String> getActores();
	public abstract Elemento getCopiaRestringida(Filtro f);
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
