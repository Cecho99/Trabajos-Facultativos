package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

public class AS10 implements AccionSemantica {

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		char a = (char) reader.read();
		token.append(a);
		Token tk = new Token(AnalizadorLexico.getPR(token.toString()));
		//TablaDeSimbolos.agregarSimbolo(token.toString(), tk); aca no se si hay que guardarlo o no 	
		return AnalizadorLexico.getPR(token.toString());
	}

	
}


