package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;

import java.util.ArrayList;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadorBondad;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;

public class PruebaBondad {

	final static int A = 1;
	final static int B = 2;
	final static int C = 3;
	final static int D = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AFP afp = new AFP(4);
		float[][][] transiciones = new float[4][2][5];
/*		
		transiciones[A-1][1][0] = 0.25;
		transiciones[A-1][1][A] = 0.25;
		transiciones[A-1][1][B] = 0.3;
		transiciones[A-1][1][C] = 0.2;
		transiciones[A-1][1][D] = 0;
		
		transiciones[A-1][0][0] = 0;
		transiciones[A-1][0][A] = 0.4;
		transiciones[A-1][0][B] = 0.4;
		transiciones[A-1][0][C] = 0.1;
		transiciones[A-1][0][D] = 0.1;
		
		transiciones[B-1][1][0] = 0.2;
		transiciones[B-1][1][A] = 0.1;
		transiciones[B-1][1][B] = 0.3;
		transiciones[B-1][1][C] = 0.3;
		transiciones[B-1][1][D] = 0.2;
		
		transiciones[B-1][0][0] = 0.9;
		transiciones[B-1][0][A] = 0.1;
		transiciones[B-1][0][B] = 0.0;
		transiciones[B-1][0][C] = 0.0;
		transiciones[B-1][0][D] = 0.0;

		transiciones[C-1][1][0] = 0.1;
		transiciones[C-1][1][A] = 0.1;
		transiciones[C-1][1][B] = 0.4;
		transiciones[C-1][1][C] = 0.2;
		transiciones[C-1][1][D] = 0.2;
	
		transiciones[C-1][0][0] = 0.0;
		transiciones[C-1][0][A] = 0.1;
		transiciones[C-1][0][B] = 0.0;
		transiciones[C-1][0][C] = 0.0;
		transiciones[C-1][0][D] = 0.9;

		transiciones[D-1][1][0] = 0.2;
		transiciones[D-1][1][A] = 0.1;
		transiciones[D-1][1][B] = 0.2;
		transiciones[D-1][1][C] = 0.4;
		transiciones[D-1][1][D] = 0.1;
		
		transiciones[D-1][0][0] = 0.1;
		transiciones[D-1][0][A] = 0.1;
		transiciones[D-1][0][B] = 0.1;
		transiciones[D-1][0][C] = 0.1;
		transiciones[D-1][0][D] = 0.6;
*/
		transiciones[A-1][1][0] = 0.0f;
		transiciones[A-1][1][A] = 0.0f;
		transiciones[A-1][1][B] = 1.0f;
		transiciones[A-1][1][C] = 0.0f;
		transiciones[A-1][1][D] = 0f;
		
		transiciones[A-1][0][0] = 0f;
		transiciones[A-1][0][A] = 1.0f;
		transiciones[A-1][0][B] = 0.0f;
		transiciones[A-1][0][C] = 0.0f;
		transiciones[A-1][0][D] = 0.0f;
		
		transiciones[B-1][1][0] = 0.0f;
		transiciones[B-1][1][A] = 0.0f;
		transiciones[B-1][1][B] = 1.0f;
		transiciones[B-1][1][C] = 0.0f;
		transiciones[B-1][1][D] = 0.0f;
		
		transiciones[B-1][0][0] = 1.0f;
		transiciones[B-1][0][A] = 0.0f;
		transiciones[B-1][0][B] = 0.0f;
		transiciones[B-1][0][C] = 0.0f;
		transiciones[B-1][0][D] = 0.0f;

		transiciones[C-1][1][0] = 0.0f;
		transiciones[C-1][1][A] = 0.0f;
		transiciones[C-1][1][B] = 1.0f;
		transiciones[C-1][1][C] = 0.0f;
		transiciones[C-1][1][D] = 0.0f;
	
		transiciones[C-1][0][0] = 0.0f;
		transiciones[C-1][0][A] = 0.0f;
		transiciones[C-1][0][B] = 0.0f;
		transiciones[C-1][0][C] = 0.0f;
		transiciones[C-1][0][D] = 1.0f;

		transiciones[D-1][1][0] = 0.0f;
		transiciones[D-1][1][A] = 0.0f;
		transiciones[D-1][1][B] = 0.0f;
		transiciones[D-1][1][C] = 1.0f;
		transiciones[D-1][1][D] = 0.0f;
		
		transiciones[D-1][0][0] = 0.0f;
		transiciones[D-1][0][A] = 0.0f;
		transiciones[D-1][0][B] = 0.0f;
		transiciones[D-1][0][C] = 0.0f;
		transiciones[D-1][0][D] = 1.0f;

		
		afp.setTransiciones(transiciones);

		float[] probfinal = new float[4];
		
		probfinal[A-1] = 0.2f;
		probfinal[B-1] = 0.2f;
		probfinal[C-1] = 0.3f;
		probfinal[D-1] = 0.9f;
		
		afp.setProbabilidadFinal(probfinal);

		
		ArrayList<String> aceptadas = new ArrayList<String>();
		aceptadas.add("0010110");
		aceptadas.add("1110011");
		
		ArrayList<String> rechazadas = new ArrayList<String>();
		rechazadas.add("1100010");
		rechazadas.add("0011011");
		
		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.SIMPLE);
		
		CalculadorBondad calculador = CalculadorBondadAFPFactory.getCalculadorBondadAFP(afp, aceptadas, rechazadas);

		calculador.run();
		
		System.out.println("Bondad simple: " + calculador.getBondad());

		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.BALANACEADO);
		
		calculador = CalculadorBondadAFPFactory.getCalculadorBondadAFP(afp, aceptadas, rechazadas);

		calculador.run();
		
		System.out.println("Bondad balanceada: " + calculador.getBondad());

		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.PREFERNCIADET);
		
		calculador = CalculadorBondadAFPFactory.getCalculadorBondadAFP(afp, aceptadas, rechazadas);

		calculador.run();
		
		System.out.println("Bondad pref det: " + calculador.getBondad());

	}

}
