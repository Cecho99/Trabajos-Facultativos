import java.util.*;

public class Noticia extends Elemento{
	String contenido;
	String categoria;
	Vector<String> palabrasClaves;
	
	
	public Noticia (String contenido, String titulo, String editor, String categoria) {
		super(editor, titulo);
		this.contenido = contenido;
		this.categoria = categoria;
		palabrasClaves = new Vector<String>();
	}
	
	
	public Elemento getCopiaRestringida(Filtro f) {
		if (f.seCumple(this)) {
			Noticia n = new Noticia(contenido, titulo, editor, categoria);
			for (int i=0; i<palabrasClaves.size(); i++) {
				n.addPalabrasClaves(palabrasClaves.get(i));
			}
			return n;
		}
		return null;
	}
	
	
	public int getCantidad(Filtro f) {
		if (f.seCumple(this)) {
			return 1;
		}
		return 0;
	}
	
	
	public void addPalabrasClaves(String s) {
		if (!palabrasClaves.contains(s)) {
			palabrasClaves.add(s);
		}
	}
	
	
	
	public Vector<String> getPalabrasClaves(){
		Vector<String> salida = new Vector<String>();
		for(int i=0; i<palabrasClaves.size(); i++) {
			salida.add(palabrasClaves.get(i));
		}
		
		return salida;
	}


	public String getContenido() {
		return contenido;
	}


	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
	
	
	
	
	
}
