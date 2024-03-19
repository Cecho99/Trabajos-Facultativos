
public class FiltroMonto extends Filtro{
	double monto;
	
	public boolean seCumple(Seguro s) {
		return s.getMonto() < monto;
	}
}
