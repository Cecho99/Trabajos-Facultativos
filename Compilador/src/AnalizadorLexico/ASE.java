package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

import AnalizadorSintatico.Parser;

public class ASE implements AccionSemantica {

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		Parser.erroresLexicos.add("Error linea "+ AnalizadorLexico.getlinea() + ": el token '" + token + "' es incorrecto");
		return -1;
	}

}
