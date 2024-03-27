package AnalizadorLexico;

import java.io.*;
import java.io.IOException;

public interface AccionSemantica {
	
	public int accion(BufferedReader reader, StringBuilder token) throws IOException;
}
	

