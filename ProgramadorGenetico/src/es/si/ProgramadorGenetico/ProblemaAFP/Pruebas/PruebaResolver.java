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
		float[][][] transiciones = new float[4][2][5];
		
		String cadena = "10110";
		
		transiciones[A-1][1][0] = 0.2f;
		transiciones[A-1][1][A] = 0.2f;
		transiciones[A-1][1][B] = 0.2f;
		transiciones[A-1][1][C] = 0.4f;
		transiciones[A-1][1][D] = 0f;
		
		transiciones[A-1][0][0] = 0.1f;
		transiciones[A-1][0][A] = 0f;
		transiciones[A-1][0][B] = 0.7f;
		transiciones[A-1][0][C] = 0.2f;
		transiciones[A-1][0][D] = 0f;
		
		transiciones[B-1][1][0] = 0.2f;
		transiciones[B-1][1][A] = 0.2f;
		transiciones[B-1][1][B] = 0.0f;
		transiciones[B-1][1][C] = 0.6f;
		transiciones[B-1][1][D] = 0.0f;
		
		transiciones[B-1][0][0] = 0.2f;
		transiciones[B-1][0][A] = 0.1f;
		transiciones[B-1][0][B] = 0.0f;
		transiciones[B-1][0][C] = 0.6f;
		transiciones[B-1][0][D] = 0.1f;

		transiciones[C-1][1][0] = 0.05f;
		transiciones[C-1][1][A] = 0.0f;
		transiciones[C-1][1][B] = 0.0f;
		transiciones[C-1][1][C] = 0.0f;
		transiciones[C-1][1][D] = 0.95f;
	
		transiciones[C-1][0][0] = 0.05f;
		transiciones[C-1][0][A] = 0.0f;
		transiciones[C-1][0][B] = 0.0f;
		transiciones[C-1][0][C] = 0.0f;
		transiciones[C-1][0][D] = 0.95f;

		transiciones[D-1][1][0] = 0.15f;
		transiciones[D-1][1][A] = 0.0f;
		transiciones[D-1][1][B] = 0.0f;
		transiciones[D-1][1][C] = 0.85f;
		transiciones[D-1][1][D] = 0.0f;
		
		transiciones[D-1][0][0] = 0.15f;
		transiciones[D-1][0][A] = 0.0f;
		transiciones[D-1][0][B] = 0.0f;
		transiciones[D-1][0][C] = 0.85f;
		transiciones[D-1][0][D] = 0.0f;

		afp.setTransiciones(transiciones);

		float[] probfinal = new float[4];
		
		probfinal[A-1] = 0.0f;
		probfinal[B-1] = 1f;
		probfinal[C-1] = 0.0f;
		probfinal[D-1] = 1f;
		
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
