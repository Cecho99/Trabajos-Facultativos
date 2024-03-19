import java.util.Vector;

public class Empresa {
	Vector<Usuario> usuarios;
	Filtro f;
	
	
	
	
	
	public Vector<Usuario> getInteresados(Curso c){
		Vector<Usuario> salida = new Vector<Usuario>();
		
		for(int i=0; i<usuarios.size(); i++) {
			if ((usuarios.get(i).getNota().getNota() < c.getNota().getNota()) || (f.seCumple(c))) {
				salida.add(usuarios.get(i));
			}
		}
		
		return salida;
	}
}
