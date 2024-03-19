import java.util.Vector;

public class Heroe extends ElementoHeroe{
	int edad;
	Filtro f;
	
	public Heroe(String nombre, int edad, Filtro f) {
		super(nombre);
		this.edad = edad;
		this.f = f;
	}
	
	
	
	public int getEdad() {
		return edad;
	}



	@Override
	public Vector<ElementoHeroe> buscar(Seguro ss) {
		Vector<ElementoHeroe> salida = new  Vector<ElementoHeroe>();
		
		if(f.seCumple(ss)) {
			salida.add(this);			
		}
		
		return salida;
	}
	
	
	public boolean estaInteresado(Seguro ss) {
		return f.seCumple(ss);
	}
	
	
}
