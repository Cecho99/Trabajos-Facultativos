package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

import AnalizadorSintatico.Parser;

public class AS5 implements AccionSemantica{

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		if (token.length() > AnalizadorLexico.getLongIdentificador()) {
			token.setLength(AnalizadorLexico.getLongIdentificador());
			System.out.println("warning linea " + AnalizadorLexico.getlinea() + " : el identificador se pasa de la maxima longitud de identificador, por lo que fue truncado");
		}
		
		Token tk = new Token(AnalizadorLexico.getPR("ID"));
		TablaDeSimbolos.agregarSimbolo(token.toString(), tk); 
		return AnalizadorLexico.getPR("ID");
	}		
}