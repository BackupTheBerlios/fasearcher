package es.si.ProgramadorGenetico.ProblemaAFP;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;

public class AplicarAlgortimoAFP {

	public static void aplicar(int particiones, int iteraciones) {
		Algoritmo alg = new Algoritmo();
		GeneradorAleatorioAFP.PARTICIONES = particiones;
		alg.setPoblacioninicial(new AFPIniciales());
		alg.setCruzador(CruzadorAFPFactory.getCruzadorAFP());
		alg.setMutador(MutadorAFPFactory.getMutadorAFP());
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
