package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import java.text.DecimalFormat;

import es.si.ProgramadorGenetico.ProblemaAFP.ResolverAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Resolutores.ResolverAFPPila;
import es.si.ProgramadorGenetico.ProblemaAFP.Resolutores.ResolverAFPVectores;
/**
 * Factoria de los resolutores
 * En los atributos aparecen los tipos distintos de resolutores
 * Se construye uno u otro segun el valor de este atributo
 *
 */
public class ResolverAFPFactory {
	/**
	 * Atributo que da valor al resolutor de vectores
	 */
	public static final int VECTORES = 0;
	/**
	 * Atributo que da valor al resolutor de pila
	 */
	public static final int PILA = 1;
	/**
	 * Atributo que indica el tipo del resolutor que se utiliza
	 */
	private static int tipo = VECTORES;
	/**
	 * Devuelve el resolutor especifico que se va a utilizar segun el valor
	 * del atributo tipo
	 * @return
	 */
	public static ResolverAFP getResolverAFP() {
		if (tipo == VECTORES) {
			return new ResolverAFPVectores();
		}
		if (tipo == PILA) {
			return new ResolverAFPPila();
		}
		return new ResolverAFPVectores();
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
		if (tipo == VECTORES) {
			return "VECTORES " + (new DecimalFormat("0.00")).format(ResolverAFPVectores.VERSION);
		}
		if (tipo == PILA) {
			return "PILA " + (new DecimalFormat("0.00")).format(ResolverAFPPila.VERSION);
		}
		return "VECTORES " + (new DecimalFormat("0.00")).format(ResolverAFPVectores.VERSION);
		
	}
}
