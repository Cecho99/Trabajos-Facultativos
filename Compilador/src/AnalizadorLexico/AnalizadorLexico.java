package AnalizadorLexico;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;

public class AnalizadorLexico {
	
	private static int linea = 1;
	static Map <String, Integer> tablaPR = new HashMap<>();
	private static int automata[][];
	private static AccionSemantica[][] accionesSemanticas;
	private static Map <Integer, Integer> mapeo;
	private Map <Integer, AccionSemantica> crearAccion;
	private static int estado_actual = 0;
	private int colum = 24;
	private int fila = 19;
	private static BufferedReader reader = null;
	private static StringBuilder token_actual = new StringBuilder();
	private static int longIdentificador = 20; 
	private static double doubleMaxLongEntero =  1.7976931348623158;
	private static int doubleMaxLongExponente = 308; 
	private static int intMaxLong = 32768; 
	private static int ulongMaxLong = 429496729;
	private static Integer columna;
	

	public AnalizadorLexico(String dirArchivo) throws IOException {
		AnalizadorLexico.mapeo = new HashMap<>();
		crearAccion = new HashMap<>();
		automata = new int[fila][colum]; 
		accionesSemanticas = new AccionSemantica[fila][colum];
		crearAS();
		cargarAccionesSemanticas();
		cargarAutomata();
		cargarMapeo();  
		cargarPR();
		reader = new BufferedReader(new FileReader(dirArchivo));
	}
	
	
	public static BufferedReader getReader(){
		return reader;
	}
	
	private void crearAS() {
		crearAccion.put(-1, new ASE()); 
		crearAccion.put(1, new AS1());	
		crearAccion.put(2, new AS2());
		crearAccion.put(3, new AS3());
		crearAccion.put(4, new AS4());
		crearAccion.put(5, new AS5());
		crearAccion.put(6, new AS6()); 
		crearAccion.put(7, new AS7()); 
		crearAccion.put(8, new AS8()); 
		crearAccion.put(9, new AS9()); 
		crearAccion.put(10, new AS10());
		crearAccion.put(11, new AS11());
	}
	
	private void cargarPR() {	       	
	        tablaPR.put("ID", 257);
	        tablaPR.put("CTE", 258);
	        tablaPR.put("INT", 259);
	        tablaPR.put("ULONG", 260);
	        tablaPR.put("DOUBLE", 261);
	        tablaPR.put("CADENA", 262);
	        tablaPR.put("IF", 263);
	        tablaPR.put("ELSE", 264);
	        tablaPR.put("END_IF", 265);
	        tablaPR.put("PRINT", 266);
	        tablaPR.put("CLASS", 267);
	        tablaPR.put("VOID", 268);
	        tablaPR.put("DO", 269);
	        tablaPR.put("UNTIL", 270);
	        tablaPR.put("IMPL", 271);
	        tablaPR.put("FOR", 272);
	        tablaPR.put("RETURN", 273);
	        tablaPR.put(">=", 274);
	        tablaPR.put("<=", 275);
	        tablaPR.put("==", 276);
	        tablaPR.put("!!", 277);
	        tablaPR.put("-=", 278);	
	 }
	

	private void cargarMapeo() {
		mapeo.put((int) 'I', 0);
		mapeo.put((int) 'd', 1);
		mapeo.put((int) 'm', 2);
		mapeo.put((int) '*', 3);
		mapeo.put((int) '%', 4);
		mapeo.put((int) '-', 5);
		mapeo.put((int) '=', 6);
		mapeo.put((int) '/', 7);
		mapeo.put((int) '(', 8);
		mapeo.put((int) ')', 9);
		mapeo.put((int) '{', 10);
		mapeo.put((int) '}', 11);
		mapeo.put((int) ';', 12);
		mapeo.put((int) ',', 13);
		mapeo.put((int) '.', 14);
		mapeo.put((int) '+', 15);
		mapeo.put((int) '_', 16);
		mapeo.put((int) '>', 17);
		mapeo.put((int) '<', 18);
		mapeo.put((int) '!', 19);
		mapeo.put((int) '\s', 20);
		mapeo.put((int) '\t', 21);
		mapeo.put((int) '\r', 22);
		mapeo.put((int) ':', 23);
	}
	
	public static boolean endOfFile(BufferedReader r) throws IOException {
		r.mark(1);
		if (r.read() == -1) {
			r.reset();
			return true;
		}
		r.reset();
		return false;
	}
	
	private void cargarAutomata() {
		String dirArchivo = "src/AnalizadorLexico/Automata.txt";
		Scanner log = null;
		try {
			log = new Scanner(new File (dirArchivo));
		} 
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		for (int f = 0; f < fila; f++) {
			for (int c = 0; c < colum; c++) {
				if(log. hasNextInt()) {
					automata[f][c]= log.nextInt();
				}
				
			}
		} 
	}
	
	private void cargarAccionesSemanticas() {
		String dirArchivo = "src/AnalizadorLexico/AccionesSemanticas.txt";
		Scanner log = null;
		try {
			log = new Scanner(new File (dirArchivo));
		} 
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		for (int f = 0; f < fila; f++) {
			for (int c = 0; c < colum; c++) {
				if(log. hasNextInt()) {
					AccionSemantica accion = crearAccion.get(log.nextInt());
					accionesSemanticas[f][c]= accion;
				}
				
			}
		} 
	}
	
	public static int mapearSimbolo(char c) {
		if(Character.isLowerCase(c)) {
			return 'm';
		}else {
			if (Character.isUpperCase(c)) {
				return 'I';
			} else {
				if (Character.isDigit(c)) {
					return 'd';
				} else {
					return c; 
				}
			}
		}
	}
	
	
	
	public static int estadoAutomata(char c, int caracter) {
		return estado_actual = automata[estado_actual][caracter];
	}						
	
	
	
	public static int getToken(BufferedReader read) throws IOException {
		reader = read;
		reader.mark(1);
		char c = (char) reader.read();
		reader.reset();
		columna = mapeo.get(mapearSimbolo(c));
		AccionSemantica as = accionesSemanticas[estado_actual][columna];
		int token = as.accion(reader, token_actual);
		estadoAutomata(c, columna);
		if (estado_actual == -1) {  //aca pusimos esto porque nos confudimos y pusimos -1 en las F cuando es 0
			estado_actual = 0;
		}
		return token;
	}
	
	public static boolean estaPR(String pal) { //accion semantica
		if (tablaPR.containsKey(pal)) {
			return true;
		}
		return false;
	}
	
	public void mostrarAutomata () {
		System.out.println("se crea");
		for (int f = 0; f < fila; f++) {
			for (int c = 0; c < colum; c++) {
				System.out.println(automata[f][c] + " ");
			}
			System.out.println();
		}
	}
	
	public void mostrarAS () {
		System.out.println("se crea");
		for (int f = 0; f < fila; f++) {
			for (int c = 0; c < colum; c++) {
				System.out.println(accionesSemanticas[f][c] + " ");
			}
			System.out.println();
		}
	}


	public static int getPR(String pr) {
		return tablaPR.get(pr);
	}
	
	

	public static void resetToken() {
		
		token_actual.setLength(0);
	}

	public static int getIntMaxLong() {
		// TODO Auto-generated method stub
		return intMaxLong;
	}

	public static StringBuilder getToken_actual() {
		return token_actual;
	}

	public static int getLongIdentificador() {
		return longIdentificador;
	}

	public static double getDoubleMaxLong() {
		return doubleMaxLongEntero;
	}

	public static int getDoubleMaxLongExp() {
		return doubleMaxLongExponente;
	}
	
	public static int getEstado_actual() {
		return estado_actual;
	}

	public int getColum() {
		return colum;
	}

	public int getFila() {
		return fila;
	}

	public static int getUlongMaxLong() {
		return ulongMaxLong;
	}
	
	public static int getlinea() {
		return linea;
	}

	public static void setLinea(int nL) {
		linea = nL;
	}

	public static Integer getColumna() {
		return columna;
	}
}
