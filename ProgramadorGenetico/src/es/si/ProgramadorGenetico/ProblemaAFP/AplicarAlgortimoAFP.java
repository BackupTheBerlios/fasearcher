package es.si.ProgramadorGenetico.ProblemaAFP;

import es.si.ProgramadorGenetico.Algoritmo;

public class AplicarAlgortimoAFP {

	public static void aplicar(int particiones, int iteraciones) {
		Algoritmo alg = new Algoritmo();
		GeneradorAleatorioAFP.PARTICIONES = particiones;
		alg.setPoblacioninicial(new AFPIniciales());
		alg.setReproductor(new ReproductorAFP());
		alg.setSelector(new SelectorAFP());
		alg.run(iteraciones);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		aplicar(5, 30);
	}

}
