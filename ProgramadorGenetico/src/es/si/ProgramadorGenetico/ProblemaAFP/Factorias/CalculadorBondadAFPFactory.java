package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import java.text.DecimalFormat;
import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadBalanceado;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadCuadratico;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadPrefernciaDet;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadSimple;

/**
 * Factoria de los calculadores de bondad
 * En los atributos aparecen los tipos distintos de calculadores
 * Se construye uno u otro segun el valor de este atributo
 */
public class CalculadorBondadAFPFactory {
	/**
	 * Atributo que da valor al calculador simple
	 */
	public final static int SIMPLE = 0;
	/**
	 * Atributo que da valor al calculador cuadratico
	 */
	public final static int CUADRATICO = 1;
	/**
	 * Atributo que da valor al calculador balanceado
	 */
	public final static int BALANACEADO = 2;
	/**
	 * Atributo que da valor al calculador determinista
	 */
	public final static int PREFERNCIADET = 3;
	/**
	 * Atributo que indica el tipo de calculador que se utiliza
	 */
	static int tipo = SIMPLE;
	/**
	 * Metodo que devuelve un calculador especifico segun el valor del atributo tipo, 
	 * pasando por parametros el afp y las cadenas
	 * @param afp
	 * @param cadenasAceptadas
	 * @param cadenasRechazadas
	 * @return
	 */
	public static CalculadorBondad getCalculadorBondadAFP(AFP afp, List<String> cadenasAceptadas, List<String> cadenasRechazadas) {
		if (tipo == SIMPLE) {
			return new CalculadorBondadSimple(afp, cadenasAceptadas, cadenasRechazadas);
		} else if (tipo == CUADRATICO) {
			return new CalculadorBondadCuadratico(afp, cadenasAceptadas, cadenasRechazadas);
		} else if (tipo == BALANACEADO) {
			return new CalculadorBondadBalanceado(afp, cadenasAceptadas, cadenasRechazadas);
		} else if (tipo == PREFERNCIADET) {
			return new CalculadorBondadPrefernciaDet(afp, cadenasAceptadas, cadenasRechazadas);
		}
		return new CalculadorBondadSimple(afp, cadenasAceptadas, cadenasRechazadas);

	}
	/**
	 * Actualiza el valor de tipo
	 * @param tipo_nuevo
	 */
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	/**
	 * Devuelve la version del calculador
	 * @return
	 */
	public static String getVersion() {
		if (tipo == SIMPLE) {
			return "SIMPLE " + (new DecimalFormat("0.00")).format(CalculadorBondadSimple.VERSION);
		} else if (tipo == CUADRATICO) {
			return "CUADRATICO " + (new DecimalFormat("0.00")).format(CalculadorBondadCuadratico.VERSION);
		} else if (tipo == BALANACEADO) {
			return "BALANACEADO " + (new DecimalFormat("0.00")).format(CalculadorBondadBalanceado.VERSION);
		} else if (tipo == PREFERNCIADET) {
			return "PREFERNCIADET " + (new DecimalFormat("0.00")).format(CalculadorBondadPrefernciaDet.VERSION);
		}
		return "SIMPLE " + (new DecimalFormat("0.00")).format(CalculadorBondadSimple.VERSION);
		
	}
	
}
