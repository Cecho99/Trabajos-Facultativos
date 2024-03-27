import java.io.*;
import java.util.*;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaDeSimbolos;
import AnalizadorSintatico.*;
import GeneracionDeCodigo.Ambito;
import GeneracionDeCodigo.GeneradorAssembler;
import GeneracionDeCodigo.Polaca;
public class Main {

	public static void main(String[] args) throws IOException {
		
		TablaDeSimbolos ts = new TablaDeSimbolos();
		System.out.println("Ingrese la direccion del archivo del codigo completa: ");
		Scanner s = new Scanner(System.in);
		String dirArchivo = s.nextLine();
		AnalizadorLexico al = new AnalizadorLexico(dirArchivo);
		s.close();
		Parser as = new Parser();
		as.run();
	
		System.out.println(" ");
		Parser.getErrores();
		System.out.println(" ");
		Polaca p = new Polaca();
		System.out.println(" ");
		TablaDeSimbolos.getTablaSimbolos();
		System.out.println(" ");
		p.imprimirPolacaCompleta();
		chequearUsoDerecha();
		System.out.println(" ");
		if (Parser.erroresLexicos.isEmpty() && Parser.erroresSintacticos.isEmpty() && Parser.erroresSemanticos.isEmpty()) {
			GeneradorAssembler.generarCodigo();
			System.out.println(GeneradorAssembler.codigo);
			writeProgram("CodigoAssembler.asm", GeneradorAssembler.codigo.toString());
			
		}
		
	
	}
	
	public static void chequearUsoDerecha() {
		for (String key : TablaDeSimbolos.getTabla().keySet()) {
			if (!TablaDeSimbolos.getUsadoDerecha(key) && TablaDeSimbolos.devolverToken(key).getUso() != null) {
				if (TablaDeSimbolos.devolverToken(key).getUso().equals("Variable")) {
					System.out.println("Warning : el simbolo " + key + " nunca fue usado");
				}
			}
		}
	}
	
	public static void writeProgram(String file_name, String content) {
        File file = new File(file_name);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file_name);
            writer.write(content);
            writer.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}



