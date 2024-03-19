import java.util.*;

public class Suscriptor {
	String nombre;
	String apellido;
	Vector<Elemento> noticias;
	Filtro f;
	
	public Suscriptor(String nombre, String apellido, Filtro f) {
		this.nombre = nombre;
		this.apellido = apellido;
		noticias = new Vector<Elemento>();
		this.f = f;
	}
	
	
	public void addNoticias(Elemento e) {
		if (f.seCumple(e)) {
			noticias.add(e);
		}
	}
	
	
}
