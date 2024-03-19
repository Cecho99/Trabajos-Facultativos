
public class GrupoSelectivo extends Grupo{
	Filtro f;
	
	public GrupoSelectivo(String titulo, String editor, Filtro f) {
		super(titulo, editor);
		this.f = f;
	}

	
	public void addNoticias(Elemento e) {
		if(f.seCumple(e)) {
			noticias.add(e);
		}
	}
}
