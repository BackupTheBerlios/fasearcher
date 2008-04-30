package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.List;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;

public class AplicarAlgoritmoAFP {

	private static AFP mejor;
	
	private static List<AFP> mejores = new ArrayList<AFP>();
	
	private static int pasos;
	
	public static void aplicar(int particiones, int iteraciones) {
		Algoritmo alg = new Algoritmo();
		GeneradorAleatorioAFP.PARTICIONES = particiones;
		alg.setPoblacioninicial(new AFPIniciales());
		alg.setCruzador(CruzadorAFPFactory.getCruzadorAFP());
		alg.setMutador(MutadorAFPFactory.getMutadorAFP());
		alg.setSelector(new SelectorAFP());
		alg.run(iteraciones);
		mejor = (AFP)alg.getMejor();
		pasos = alg.getPasos();
		mejores.clear();
		for (Individuo mejor : alg.getMejores())
			mejores.add((AFP) mejor);
	}

	public static List<AFP> getMejores() {
		return mejores;
	}

	public static AFP getMejor() {
		return mejor;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		aplicar(5, 30);
	}
	
	public static int getPasos() {
		return pasos;
	}

}
