
public class CalculadorCant extends Calculador{
	double valor;
	
	public double calcular(Elemento e) {
		return valor * e.getCantidad();
	}
}
