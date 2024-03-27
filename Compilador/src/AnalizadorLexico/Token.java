package AnalizadorLexico;

public class Token {
	
	private int valor;
	private String tipo;
	private String uso;
	private String ambito;
	private String parametro;
	private String parametroNombre;
	

	private String clase;
	private String herencia;
	private boolean usadoDerecha = false;
	private boolean tieneCuerpo = true;
	private boolean implementar = true;
	private int anidamiento = 0;
	
	public String getParametroNombre() {
		return parametroNombre;
	}

	public void setParametroNombre(String parametroNombre) {
		this.parametroNombre = parametroNombre;
	}
	
	public int getAnidamiento() {
		return anidamiento;
	}

	public void setAnidamiento(int anidamiento) {
		this.anidamiento = anidamiento;
	}

	public boolean getCuerpo() {
		return tieneCuerpo;
	}

	public void setCuerpo(boolean declaracionDistribuida) {
		this.tieneCuerpo = declaracionDistribuida;
	}

	public boolean isUsadoDerecha() {
		return usadoDerecha;
	}

	public void setUsadoDerecha(boolean usadoDerecha) {
		this.usadoDerecha = usadoDerecha;
	}

	public String getHerencia() {
		return herencia;
	}

	public void setHerencia(String herencia) {
		this.herencia = herencia;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Token(int valor) {
		super();
		this.valor = valor;
	}
	
	public Token(String tipo2) {
		this.tipo = tipo2;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public void setLexema(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}
	
	public String toString() {
		return  Integer.toString(valor)+ "  AMBITO: " + ambito + "  USO: " + uso + "  TIPO: " + tipo + "  PARAMETRO: " + parametro + "  CLASE: " + clase + "  HERENCIA: " + herencia + "  USO DERECHA: " + usadoDerecha + "  CUERPO: " + tieneCuerpo+ "  IMPLEMENTAR: " + implementar + "  ANIDAMIENTO: " + anidamiento + " PARAMETRO NOMBRE: " + parametroNombre; 
	}
	
	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}
	
	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public boolean isImplementar() {
		return implementar;
	}

	public void setImplementar(boolean implementar) {
		this.implementar = implementar;
	}
}
