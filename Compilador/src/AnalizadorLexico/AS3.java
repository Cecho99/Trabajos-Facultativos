package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.IOException;

import AnalizadorSintatico.Parser;

public class AS3 implements AccionSemantica{
	

	@Override
	public int accion(BufferedReader reader, StringBuilder token) throws IOException {
		char c = (char) reader.read();
		token.append(c);
		String valor = "";
		if(token.charAt(token.length()-1) == 'i') {
			for (int i=0; i < token.length() ; i++) {
				if (token.charAt(i) != '_') { 
					valor += token.charAt(i); 
				}else {
					break;
				}
			}
			int v = Integer.parseInt(valor); 
			if( v > AnalizadorLexico.getIntMaxLong()) {
				Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango maximo");	
				return -1;
			}
			Token tk = new Token(AnalizadorLexico.getPR("INT"));
			tk.setTipo("INT");
			TablaDeSimbolos.agregarSimbolo(token.toString(), tk); 
			return AnalizadorLexico.getPR("CTE"); 
		}else {
			if(token.charAt(token.length()-1) == 'u') {
				char l = (char) reader.read();
				token.append(l);
				if(token.charAt(token.length()-1) == 'l') {
					for (int i=0; i < token.length() ; i++) {
						if (token.charAt(i) != '_') { 
							valor += token.charAt(i); 
						}else {
							break;
						}
					}
					int v = Integer.parseInt(valor); 
					if( v > AnalizadorLexico.getUlongMaxLong()) {  
						Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango maximo");
						return -1;
					}
					Token tk = new Token(AnalizadorLexico.getPR("ULONG"));
					tk.setTipo("ULONG");
					TablaDeSimbolos.agregarSimbolo(token.toString(), tk); 
					return AnalizadorLexico.getPR("CTE"); 
				}else {
					Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante no fue declara correctamente");
					return -1;
				}
			}
		}
		Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante no fue declara correctamente");
		return -1;
		
	}		
	
}