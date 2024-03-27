package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

public class AS2 implements AccionSemantica{
	
	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		
		char c = (char) reader.read();
		if (AnalizadorLexico.getColumna() == 22) {
			AnalizadorLexico.setLinea(AnalizadorLexico.getlinea() + 1);
		}
		token.append(c);
		return -1;
	}

}