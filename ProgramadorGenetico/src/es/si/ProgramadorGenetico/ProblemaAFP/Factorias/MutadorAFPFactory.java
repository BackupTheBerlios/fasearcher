package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.ProblemaAFP.Mutadores.*;

public class MutadorAFPFactory {

	public static final int TIPO_1 = 0;
	
	public static final int TIPO_2 = 1;
	
	private static int tipo = 0;
	
	public static Mutador getMutadorAFP() {
		if (tipo == TIPO_1) {
			return new MutadorAFP_1();
		}
		if (tipo == TIPO_2) {
			return new MutadorAFP_2();
		}
		return new MutadorAFP_1();
	}
	
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	
}
