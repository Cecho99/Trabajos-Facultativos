import java.util.*;

public abstract class ElementoHeroe {
	String nombre;
	
	
	public ElementoHeroe(String nombre) {
		this.nombre=nombre;
	}
	
	
	public abstract int getEdad();
	public abstract Vector<ElementoHeroe> buscar(Seguro ss);

}
