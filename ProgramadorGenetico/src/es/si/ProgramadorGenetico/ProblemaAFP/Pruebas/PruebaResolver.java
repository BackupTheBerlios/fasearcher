package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ResolverAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;

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
		
		String cadena = "10110";
		
		transiciones[A-1][1][0] = 0.2;
		transiciones[A-1][1][A] = 0.2;
		transiciones[A-1][1][B] = 0.2;
		transiciones[A-1][1][C] = 0.4;
		transiciones[A-1][1][D] = 0;
		
		transiciones[A-1][0][0] = 0.1;
		transiciones[A-1][0][A] = 0;
		transiciones[A-1][0][B] = 0.7;
		transiciones[A-1][0][C] = 0.2;
		transiciones[A-1][0][D] = 0;
		
		transiciones[B-1][1][0] = 0.2;
		transiciones[B-1][1][A] = 0.2;
		transiciones[B-1][1][B] = 0.0;
		transiciones[B-1][1][C] = 0.6;
		transiciones[B-1][1][D] = 0.0;
		
		transiciones[B-1][0][0] = 0.2;
		transiciones[B-1][0][A] = 0.1;
		transiciones[B-1][0][B] = 0.0;
		transiciones[B-1][0][C] = 0.6;
		transiciones[B-1][0][D] = 0.1;

		transiciones[C-1][1][0] = 0.05;
		transiciones[C-1][1][A] = 0.0;
		transiciones[C-1][1][B] = 0.0;
		transiciones[C-1][1][C] = 0.0;
		transiciones[C-1][1][D] = 0.95;
	
		transiciones[C-1][0][0] = 0.05;
		transiciones[C-1][0][A] = 0.0;
		transiciones[C-1][0][B] = 0.0;
		transiciones[C-1][0][C] = 0.0;
		transiciones[C-1][0][D] = 0.95;

		transiciones[D-1][1][0] = 0.15;
		transiciones[D-1][1][A] = 0.0;
		transiciones[D-1][1][B] = 0.0;
		transiciones[D-1][1][C] = 0.85;
		transiciones[D-1][1][D] = 0.0;
		
		transiciones[D-1][0][0] = 0.15;
		transiciones[D-1][0][A] = 0.0;
		transiciones[D-1][0][B] = 0.0;
		transiciones[D-1][0][C] = 0.85;
		transiciones[D-1][0][D] = 0.0;

		afp.setTransiciones(transiciones);

		double[] probfinal = new double[4];
		
		probfinal[A-1] = 0.0;
		probfinal[B-1] = 1;
		probfinal[C-1] = 0.0;
		probfinal[D-1] = 1;
		
		afp.setProbabilidadFinal(probfinal);
		
		ResolverAFPFactory.setTipo(ResolverAFPFactory.PILA);
		ResolverAFP resolver = ResolverAFPFactory.getResolverAFP();
		resolver.setAFP(afp);
		resolver.setCadena(cadena);
		
		Long tiempo = System.currentTimeMillis();
		
		resolver.run();
		
		System.out.println("Probabilidad normal: " + resolver.getProbabilidadAceptar() + " tiempo: " + (System.currentTimeMillis() - tiempo));
		
		ResolverAFPFactory.setTipo(ResolverAFPFactory.VECTORES);
		resolver = ResolverAFPFactory.getResolverAFP();
		resolver.setAFP(afp);
		resolver.setCadena(cadena);
		
		tiempo = System.currentTimeMillis();
		
		resolver.run();
		
		System.out.println("Probabilidad fast: " + resolver.getProbabilidadAceptar() + " tiempo: " + (System.currentTimeMillis() - tiempo));
		
	}

}
