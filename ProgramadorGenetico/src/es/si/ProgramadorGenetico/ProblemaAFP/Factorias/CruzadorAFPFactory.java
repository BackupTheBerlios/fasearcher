package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores.*;

public class CruzadorAFPFactory {

	public static final int TIPO_NULO = 0;

	public static final int TIPO_1 = 1;
	
	public static final int TIPO_2 = 2;
	
	private static int tipo = 1;
	
	public static Cruzador getCruzadorAFP() {
		if (tipo == TIPO_NULO)
			return new CruzadorAFPNulo();
		if (tipo == TIPO_1)
			return new CruzadorAFP_1();
		if (tipo == TIPO_2)
			return new CruzadorAFP_2();
		return new CruzadorAFP_1();
	}
	
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	
}
