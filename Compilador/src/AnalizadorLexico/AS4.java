package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

import AnalizadorSintatico.Parser;

public class AS4 implements AccionSemantica{

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		String pr = token.toString();
		if (AnalizadorLexico.estaPR(pr)) {
			return AnalizadorLexico.getPR(pr);
		}
		Parser.erroresLexicos.add("Error linea " +  AnalizadorLexico.getlinea() + ": La palabra reservada " + pr + " no existe");
		return -1; 
	}		
}