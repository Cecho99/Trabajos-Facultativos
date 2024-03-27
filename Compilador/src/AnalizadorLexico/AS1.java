package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

public class AS1 implements AccionSemantica{

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		token.setLength(0);
		char c = (char) reader.read();
		token.append(c);
		return -1;
	}
		
}