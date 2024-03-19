
public class CalculadorPorcentaje extends Calculador{
	double porcentaje;
	
	
	public double calcular(Elemento e) {
		return e.getValorTotal()*porcentaje;
	}
	
}
