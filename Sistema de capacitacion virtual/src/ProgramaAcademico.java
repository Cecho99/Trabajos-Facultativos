import java.util.Vector;

public abstract class ProgramaAcademico {
	    private String nombre;
	    private String tema;

	    public ProgramaAcademico(String nombre, String tema) {
	        this.nombre = nombre;
	        this.tema = tema;
	    }

	    public abstract double getCosto();
	    public abstract int getCreditos();
	    public abstract Vector<ProgramaAcademico> buscar(Filtro f);
	    
	    public String getNombre() {
	        return nombre;
	    }

	    public String getTema() {
	        return tema;
	    }
	}

