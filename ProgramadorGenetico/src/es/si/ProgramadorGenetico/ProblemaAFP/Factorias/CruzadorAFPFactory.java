package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import java.text.DecimalFormat;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores.*;

public class CruzadorAFPFactory {

	public static final int TIPO_NULO = 0;

	public static final int TIPO_1 = 1;
	
	public static final int TIPO_2 = 2;
	
	public static final int TIPO_3 = 3;
	
	public static final int TIPO_4 = 4;
	
	private static int tipo = 1;
	
	public static Cruzador getCruzadorAFP() {
		if (tipo == TIPO_NULO)
			return new CruzadorAFPNulo();
		if (tipo == TIPO_1)
			return new CruzadorAFP_1();
		if (tipo == TIPO_2)
			return new CruzadorAFP_2();
		if (tipo == TIPO_3)
			return new CruzadorAFP_3();
		if (tipo == TIPO_4)
			return new CruzadorAFP_4();
		return new CruzadorAFP_1();
	}
	
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	
	public static String getVersion() {
		if (tipo == TIPO_NULO)
			return "NULO " + (new DecimalFormat("0.00")).format(CruzadorAFPNulo.VERSION);
		if (tipo == TIPO_1)
			return "TIPO_1 " + (new DecimalFormat("0.00")).format(CruzadorAFP_1.VERSION);
		if (tipo == TIPO_2)
			return "TIPO_2 " + (new DecimalFormat("0.00")).format(CruzadorAFP_2.VERSION);
		if (tipo == TIPO_3)
			return "TIPO_3" +  (new DecimalFormat("0.00")).format(CruzadorAFP_3.VERSION);
		if (tipo == TIPO_4)
			return "TIPO_4" +  (new DecimalFormat("0.00")).format(CruzadorAFP_4.VERSION);
		return "TIPO_1 " + (new DecimalFormat("0.00")).format(CruzadorAFP_1.VERSION);
	}
	
}
