package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.List;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;

/**
 * Esta clase es utilizada para aplicar el algortimo gen�tico con
 * AFP dados una serie de par�metros.<p>
 * 
 */
public class AplicarAlgoritmoAFP {

	/**
	 * Guarda el mejor AFP encontrado por el algoritmo 
	 */
	private static AFP mejor;
	
	/**
	 * Guarda la lista del mejor AFP en cada iteraci�n del algoritmo
	 */
	private static List<AFP> mejores = new ArrayList<AFP>();
	
	/**
	 * Guarda el n�mero de pasos que llevaron al algoritmo para encontrar
	 * el mejor AFP posible
	 */
	private static int pasos;
	
	/**
	 * Aplicar el algoritmo gen�tico, para AFP y dados el par�metro
	 * de particiones e iteraciones maximas de forma expresa
	 * 
	 * @param particiones
	 * Par�metro que indica el denomiador para las probabilidades
	 * de los AFP generados inicialmente (actualmente no es  utilizado)
	 * @param iteraciones
	 * N�mero de iteraciones maximo que se debe ejecutar el algoritmo
	 * gen�tico
	 */
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
	 * M�todo para realizar pruebas de la clase
	 * 
	 * @param args
	 * No necesita ningun valor
	 */
	public static void main(String[] args) {
		aplicar(5, 30);
	}
	
	public static int getPasos() {
		return pasos;
	}

}
