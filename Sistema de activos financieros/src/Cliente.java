import java.util.Vector;

public class Cliente {
	String nombre;
	int dni;
	Vector<Elemento> contratados;
	
	
	public Cliente(String nombre, int dni) {
		this.nombre = nombre;
		this.dni = dni;
		contratados = new Vector<Elemento>();
	}
	
	
	
	public void addContratados(Elemento e) {
		contratados.add(e);
	}
	
	
	public Vector<Elemento> getContratados(){
		Vector<Elemento> salida = new Vector<Elemento>();
		
		for(int i=0; i<contratados.size(); i++) {
			salida.add(contratados.get(i));
		}
		
		return salida;
	}
}
