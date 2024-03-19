
public class CalculadorOp1 extends Calculador{
	double porcentaje;
	double valor;
	
	
	public double calcular(Elemento e) {
		return valor + e.getValorTotal()*porcentaje;
	}
	
}
