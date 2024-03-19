
public class FavoritosSelectivo extends Favoritos{
	Filtro f;
	int puntuacion;
	
	public FavoritosSelectivo(int id, String nombre, Filtro f, int puntuacion) {
		super(id, nombre);
		this.f = f;
		this.puntuacion = puntuacion;
	}
	
	
	
	public int puntaje() {
		if (f.seCumple(this)) {
			return puntuacion;
		}
		
		return 0;
	}
	
	

}
