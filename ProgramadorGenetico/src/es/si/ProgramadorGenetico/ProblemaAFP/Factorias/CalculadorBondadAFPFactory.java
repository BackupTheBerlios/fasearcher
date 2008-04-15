package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import java.text.DecimalFormat;
import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadBalanceado;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadCuadratico;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadPrefernciaDet;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadSimple;


public class CalculadorBondadAFPFactory {

	public final static int SIMPLE = 0;
	
	public final static int CUADRATICO = 1;
	
	public final static int BALANACEADO = 2;
	
	public final static int PREFERNCIADET = 3;
	
	static int tipo = SIMPLE;
	
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
	
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	
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
