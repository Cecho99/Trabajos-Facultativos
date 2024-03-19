import java.util.Vector;

public class Favoritos extends Elemento{
	String nombre;
	Vector<Elemento> favoritos;
	
	
	public Favoritos(int id, String nombre) {
		super(id);
		this.nombre = nombre;
		favoritos = new Vector<Elemento>();
	}
	
	
	
	
	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	@Override
	public Elemento getCopiaRestringida(Filtro f) {	
		Favoritos fav = new Favoritos(this.getId(), nombre);
		boolean encontro = false;
		
		for(int i = 0;i < favoritos.size();i++) {
			Elemento aux = favoritos.get(i).getCopiaRestringida(f);
			if ((aux != null) || (favoritos.get(i) != null)){
				fav.addFavoritos(aux);
				encontro=true;
			}
		}
		
		if (encontro) 
			return fav;
		else
			return null;
	}
	



	public void addFavoritos(Elemento f) {
		favoritos.add(f);
	}
	
	
	
	@Override
	public int getDuracion() {
		int duracion=0;
		
		for(int i=0; i<favoritos.size(); i++) {
			duracion += favoritos.get(i).getDuracion();
		}
		return duracion;
	}
	
	
	
	
	@Override
	public int getCalificacion() {
		int calificacion=0;
		int cont=0;
		
		for(int i=0; i<favoritos.size(); i++) {
			calificacion += favoritos.get(i).getDuracion();
			cont++;
		}
		return calificacion/cont;
	}
	
	
	
	
	@Override
	public Vector<String> getActores() {
		Vector<String> salida = new Vector<String>();
		
		for(int i=0; i<favoritos.size(); i++) {
			Vector<String> s = favoritos.get(i).getActores();
			for(int j=0; j<s.size(); j++) {
				if(!salida.contains(s.get(j))) {
					salida.add(s.get(j));
				}
			}
		}
		return salida;
	}
	
	
	
}
