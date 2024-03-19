import java.util.Vector;

public class Sistema {
	Vector<Usuario> usuarios;
	Vector<Elemento> peliculas;
	Filtro preferencia;
	
	public void addUsuaruio(Usuario u) {
		for(int i=0; i<peliculas.size(); i++) {
			if (preferencia.seCumple(peliculas.get(i))) {
				usuarios.add(u);
			}
		}		
	}
	
	
	public void addPeliculas(Elemento e) {
		peliculas.add(e);
	}
}
