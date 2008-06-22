package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import java.text.DecimalFormat;

import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.ProblemaAFP.Mutadores.*;
/**
 * Factoria de mutadores
 * En los atributos aparecen los tipos distintos de mutadores
 * Se construye uno u otro segun el valor de este atributo
 * 
 */
public class MutadorAFPFactory {
	/**
	 * Atributo que indica el valor del mutador de tipo nulo
	 */
	public static final int TIPO_NULO = 0;
	/**
	 * Atributo que indica el valor del mutador de tipo 1
	 */
	public static final int TIPO_1 = 1;
	/**
	 * Atributo que indica el valor del mutador de tipo 2
	 */
	public static final int TIPO_2 = 2;
	/**
	 * Atributo que indica el valor del mutador de tipo 3
	 */
	public static final int TIPO_3 = 3;
	/**
	 * Atributo que indica el tipo de mutador que se esta utilizando
	 */
	private static int tipo = 1;
	/**
	 * Devuelve un mutador especifico segun el valor del atributo tipo
	 * @return
	 */
	public static Mutador getMutadorAFP() {
		if (tipo == TIPO_NULO)
			return new MutadorAFPNulo();
		if (tipo == TIPO_1)
			return new MutadorAFP_1();
		if (tipo == TIPO_2)
			return new MutadorAFP_2();
		if (tipo == TIPO_3)
			return new MutadorAFP_3();
		return new MutadorAFP_1();
	}
	/**
	 * Actualiza el valor del atributo tipo
	 * @param tipo_nuevo
	 */
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	/**
	 * Devuelve el numero de version
	 * @return
	 */
	public static String getVersion() {
		if (tipo == TIPO_NULO)
			return "TIPO_NULO " + (new DecimalFormat("0.00")).format(MutadorAFPNulo.VERSION);
		if (tipo == TIPO_1)
			return "TIPO_1 " + (new DecimalFormat("0.00")).format(MutadorAFP_1.VERSION);
		if (tipo == TIPO_2)
			return "TIPO_2 " + (new DecimalFormat("0.00")).format(MutadorAFP_2.VERSION);
		if (tipo == TIPO_3)
			return "TIPO_3 " + (new DecimalFormat("0.00")).format(MutadorAFP_3.VERSION);
		return "TIPO_1 " + (new DecimalFormat("0.00")).format(MutadorAFP_1.VERSION);

	}
	
}
