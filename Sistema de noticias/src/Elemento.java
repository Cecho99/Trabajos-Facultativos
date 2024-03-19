import java.util.Vector;

public abstract class Elemento {
	String editor;
	String titulo;
	
	
	public Elemento(String editor, String titulo) {
		this.editor = editor;
		this.titulo = titulo;
	}
	
	
	public abstract String getCategoria();
	public abstract Vector<String> getPalabrasClaves();
	public abstract String getContenido();
	public abstract int getCantidad(Filtro f);
	public abstract Elemento getCopiaRestringida(Filtro f);
	
	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getEditor() {
		return editor;
	}


	public void setEditor(String editor) {
		this.editor = editor;
	}

}
