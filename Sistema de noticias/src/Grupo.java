import java.util.*;

public class Grupo extends Elemento{
	Vector<Elemento> noticias;

	
	public Grupo (String titulo, String editor) {
		super(editor, titulo);
		noticias = new Vector<Elemento>();
	}
	
	
	public void addNoticias(Elemento e) {
		noticias.add(e);
	}
	
	
	public Vector<Elemento> getNoticias(){
		Vector<Elemento> salida = new Vector<Elemento>();
		
		for (int i=0; i<noticias.size(); i++) {
			salida.add(noticias.get(i));
		}
		return salida;
	}

	
	
	public Elemento getCopiaRestringida(Filtro f) {
		Grupo copia = new Grupo(this.getTitulo(), this.getEditor());
		boolean encontro = false;
		
		for(int i=0; i<noticias.size(); i++) {
			Elemento aux = noticias.get(i).getCopiaRestringida(f);
			if (aux != null) {
				copia.addNoticias(aux);
				encontro = true;
			}
		}
		
		if(encontro) {
			return copia;
		}else {
			return null;
		}
	}
	
	
	
	
	public int getCantidad(Filtro f) {
		int cant = 0;
		if (f.seCumple(this)) {
			cant += 1;
		}
		else {
			for(int i=0; i<noticias.size(); i++) {
				cant += noticias.get(i).getCantidad(f);
			}
		}
		return cant;
	}
	
	
	
	@Override
	public String getCategoria() {
		if (noticias.get(0)!=null) {
			return noticias.get(0).getCategoria();
		}
		
		return null;
	}
	

	@Override
	public Vector<String> getPalabrasClaves() {
		Vector<String> salida = new Vector<String>();
		
		for(int i=0; i<noticias.size(); i++) {
			if (!salida.contains(noticias.get(i).getCategoria())) {
				salida.add(noticias.get(i).getCategoria());
			}
		}
		
		return salida;
	}

	
	
	@Override
	public String getContenido() {
		String salida = new String();
		for (int i=0; i<noticias.size(); i++) {
			salida += noticias.get(i).getContenido();
		}
		
		return salida;
		
	}
	
}
