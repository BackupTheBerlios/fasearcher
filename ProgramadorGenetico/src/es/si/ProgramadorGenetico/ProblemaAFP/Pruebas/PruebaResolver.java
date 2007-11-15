package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Resolver;

public class PruebaResolver {

	
	final static int A = 1;
	final static int B = 2;
	final static int C = 3;
	final static int D = 4;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AFP afp = new AFP(4);
		double[][][] transiciones = new double[4][2][5];
		
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

		afp.setTransiciones(transiciones);

		double[] probfinal = new double[4];
		
		probfinal[A-1] = 0.2;
		probfinal[B-1] = 0.2;
		probfinal[C-1] = 0.3;
		probfinal[D-1] = 0.9;
		
		afp.setProbabilidadFinal(probfinal);
		
		Resolver resolver = new Resolver(afp, "101100010");
		
		resolver.run();
		
		System.out.println("Probabilidad: " + resolver.getProbabilidadAceptar());
	}

}
