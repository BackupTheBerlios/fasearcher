package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import java.util.List;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadBalanceado;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadCuadratico;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadPrefernciaDet;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadSimple;
import es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores.CruzadorAFPNulo;
import es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores.CruzadorAFP_1;
import es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores.CruzadorAFP_2;


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
	
}
