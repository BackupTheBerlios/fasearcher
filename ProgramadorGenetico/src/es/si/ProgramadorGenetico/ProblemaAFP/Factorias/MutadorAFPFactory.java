package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.ProblemaAFP.Mutadores.*;

public class MutadorAFPFactory {

	public static final int TIPO_NULO = 0;
	
	public static final int TIPO_1 = 1;
	
	public static final int TIPO_2 = 2;
	
	private static int tipo = 1;
	
	public static Mutador getMutadorAFP() {
		if (tipo == TIPO_NULO)
			return new MutadorAFPNulo();
		if (tipo == TIPO_1)
			return new MutadorAFP_1();
		if (tipo == TIPO_2)
			return new MutadorAFP_2();
		return new MutadorAFP_1();
	}
	
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	
	public static String getVersion() {
		if (tipo == TIPO_NULO)
			return "TIPO_NULO " + MutadorAFPNulo.VERSION;
		if (tipo == TIPO_1)
			return "TIPO_1 " + MutadorAFP_1.VERSION;
		if (tipo == TIPO_2)
			return "TIPO_2 " + MutadorAFP_2.VERSION;
		return "TIPO_1 " + MutadorAFP_1.VERSION;

	}
	
}
