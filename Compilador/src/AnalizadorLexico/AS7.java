package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

import AnalizadorSintatico.Parser;
import java.lang.Math;

public class AS7 implements AccionSemantica{

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		String valorentero = "";
		String valorexponente = "";
		int tamañoentero = 0;
		
		for (int i = 0; i < token.length() ; i++) {
			if (token.charAt(i) == 'D' || token.charAt(i) == 'd') {
				break;
			}else {
				valorentero += token.charAt(i);
				tamañoentero++;
			}			
		}	
		double ve = Double.parseDouble(valorentero);		
		
		if ((token.toString().contains("D") || token.toString().contains("d"))) {
			for (int i = tamañoentero + 1; i < token.length() ; i++) {
				valorexponente += token.charAt(i);
			}
			int vex = Integer.parseInt(valorexponente);
			double power = Math.pow(ve, vex);
			if (power > Math.pow(AnalizadorLexico.getDoubleMaxLong(), AnalizadorLexico.getDoubleMaxLongExp())) {
				Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango maximo" );
				return -1;
			}

		}
		
		Token tk = new Token(AnalizadorLexico.getPR("DOUBLE"));
		tk.setTipo("DOUBLE");
		TablaDeSimbolos.agregarSimbolo(token.toString(), tk); 	
		return AnalizadorLexico.getPR("CTE");
		}		
}