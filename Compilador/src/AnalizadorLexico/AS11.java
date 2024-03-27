package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

import AnalizadorSintatico.Parser;

public class AS11 implements AccionSemantica {

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		char c = (char) reader.read();
		if ( c != ',') {
			token.append(c);
			Token tk = new Token(AnalizadorLexico.getPR("CADENA"));
			token.deleteCharAt(0);
			token.deleteCharAt(token.length()-1);
			TablaDeSimbolos.agregarSimbolo(token.toString(), tk); 
			tk.setTipo("CADENA");
			return AnalizadorLexico.getPR("CADENA");
		}else {
			Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : La cadena no fue finalizada correctamente");
			return -1;
		}
	}
}


