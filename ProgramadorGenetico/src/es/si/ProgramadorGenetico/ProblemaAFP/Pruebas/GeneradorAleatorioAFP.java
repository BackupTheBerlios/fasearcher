package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;

import java.util.Random;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class GeneradorAleatorioAFP {

	public static AFP nuevo(int estados) {
		double[][][] transiciones = new double[estados][2][estados +1];
		double[] probfinal = new double[estados];
		Random rand = new Random();
		int[] valores0 = new int[estados + 1];
		int[] valores1 = new int[estados + 1];
		for (int i = 0; i < estados; i++) {
			int total0 = 0;
			int total1 = 0;
			for (int j = 0; j < estados + 1; j++) {
				valores0[j] = rand.nextInt(200);
				total0 += valores0[j];
				valores1[j] = rand.nextInt(200);
				total1 += valores1[j];
			}
			for (int j = 0; j < estados + 1; j++) {
				transiciones[i][0][j] = (double) valores0[j] / total0;
				transiciones[i][1][j] = (double) valores1[j] / total1;
			}
			probfinal[i] = rand.nextDouble();
		}
		AFP automata = new AFP(estados);
		automata.setProbabilidadFinal(probfinal);
		automata.setTransiciones(transiciones);
		return automata;
	}
	
}
