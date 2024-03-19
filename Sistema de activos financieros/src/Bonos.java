
public class Bonos extends Accion{
	int interes;


	public Bonos(String nombre, Cliente c, int cantidad, double valor, int interes) {
		super(nombre, c, cantidad, valor);
		this.interes = interes;
	}


}
