package GeneracionDeCodigo;
import AnalizadorLexico.*;
import AnalizadorSintatico.*;


public class TablaTipos {
	 	public static final int ULONG = 0;
	 	public static final int DOUBLE = 1;
	 	public static final int INT = 2;
	    

	    public static final String ULONG_TYPE = "ULONG";
	    public static final String INT_TYPE = "INT";
	    public static final String DOUBLE_TYPE = "DOUBLE";
	    public static final String ERROR_TYPE = "error";

	    private static final String[][] tiposSumaResta = { { ULONG_TYPE, ERROR_TYPE, ERROR_TYPE },
				   										   { ERROR_TYPE, DOUBLE_TYPE, ERROR_TYPE },
				   										   { ERROR_TYPE, ERROR_TYPE, INT_TYPE },
				   										   { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE} };

	    
	    private static final String[][] tiposMultDiv = { { ULONG_TYPE, ERROR_TYPE, ERROR_TYPE }, 
				  										 { ERROR_TYPE, DOUBLE_TYPE, ERROR_TYPE },
				  										 { ERROR_TYPE, ERROR_TYPE, INT_TYPE},
				  										 { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE} };
	       
	    
	    private static final String[][] tiposComparadores = { { ULONG_TYPE, ERROR_TYPE, ERROR_TYPE }, 
	    													  { ERROR_TYPE, DOUBLE_TYPE, ERROR_TYPE },
					  										  { ERROR_TYPE, ERROR_TYPE, INT_TYPE},
					  										  { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE} };
	    

	   
	    private static final String[][] tiposAsig = { { ULONG_TYPE, ERROR_TYPE, ERROR_TYPE }, 
	    											  { ERROR_TYPE, DOUBLE_TYPE, ERROR_TYPE },
	    											  { ERROR_TYPE, ERROR_TYPE, INT_TYPE},
				  									  { ERROR_TYPE, ERROR_TYPE, ERROR_TYPE} };
	    
	    
	   

	    
	    
	    private static String tipoResultante(String op1, String op2, String operador) {
	        int fil = getNumeroTipo(op1);
	        int col = getNumeroTipo(op2);

	        switch (operador) {
	            case ("+"):
	            case ("-"):
	                return tiposSumaResta[fil][col];
	            case ("*"):
	            case ("/"):
	                return tiposMultDiv[fil][col];
	            case ("="):
	                return tiposAsig[fil][col];
	            case ("<="):
	            case ("<"):
	            case (">="):
	            case (">"):
	            case ("=="):
	            case ("!!"):
	            	return tiposComparadores[fil][col];
	            default:
	                return ERROR_TYPE;
	        }
	    }

	    
	    
	    public static String getTipo(String op) {
	    	String tipo = TablaDeSimbolos.getTipo(op);
	        return tipo;
	    }
	   
	    
	    
	    public static String getTipoAbarcativo(String op1, String op2, String operador){
	        String tipoOp1 = getTipo(op1);
	        String tipoOp2 = getTipo(op2);
   
	        String tipoFinal = tipoResultante(tipoOp1, tipoOp2, operador);

	        if (tipoFinal.equals(ERROR_TYPE)) {
	            System.out.println("No se puede realizar la operacion " + operador + " entre los tipos " + tipoOp1 + " y " + tipoOp2);
	            System.exit(0);
	        }

	        return tipoFinal;
	    }
	    
	    
	    private static int getNumeroTipo(String tipo) {
	        if (tipo.equals(ULONG_TYPE)) 
	        	return ULONG;
	        else 
	        	if (tipo.equals(DOUBLE_TYPE)) 
	        		return DOUBLE;
	        return INT;
	    }
}

