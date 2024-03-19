
public class CalculadorOp extends Calculador{
	double porcentaje;
	double valor;
	
	
	public double calcular(Elemento e) {
		return (e.getValorTotal()*porcentaje)-valor;
	}
}
