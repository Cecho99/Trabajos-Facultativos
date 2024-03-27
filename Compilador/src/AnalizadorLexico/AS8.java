package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

public class AS8 implements AccionSemantica {

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		int c = (char) reader.read();
		char c1 = (char) c;
		token.append(c1);
		return c;
	}

}
