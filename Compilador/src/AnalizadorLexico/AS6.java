package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

import AnalizadorSintatico.Parser;

public class AS6 implements AccionSemantica{
	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		int c = reader.read();
		if (AnalizadorLexico.getColumna() == 22) {
			reader.read();
			AnalizadorLexico.setLinea(AnalizadorLexico.getlinea() + 1);
		}
		if (AnalizadorLexico.getEstado_actual() == 10) {
			if (c == '*' ) {
				Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea()+ " : El comentario esta mal escrito");
			}
		}
		return -1;
	}		
}