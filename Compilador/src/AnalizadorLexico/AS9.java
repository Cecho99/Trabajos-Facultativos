package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

public class AS9 implements AccionSemantica {

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		int c = token.toString().charAt(0);
		return c;
	}

}
