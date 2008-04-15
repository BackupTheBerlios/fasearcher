package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.Random;


public class GeneradorAleatorioAFP {

	public static int PARTICIONES = 200;
	
	private static Random rand = new Random();
	
	public static AFP nuevo(int estados) {
		float[][][] transiciones = new float[estados][2][estados +1];
		float[] probfinal = new float[estados];
		//Random rand = new Random();
		//rand.setSeed(System.currentTimeMillis());
		int[] valores0 = new int[estados + 1];
		int[] valores1 = new int[estados + 1];
		for (int i = 0; i < estados; i++) {
			int total0 = 0;
			int total1 = 0;
			for (int j = 0; j < estados + 1; j++) {
				valores0[j] = rand.nextInt(PARTICIONES);
				total0 += valores0[j];
				valores1[j] = rand.nextInt(PARTICIONES);
				total1 += valores1[j];
			}
			for (int j = 0; j < estados + 1; j++) {
				if (total0 == 0)
					transiciones[i][0][j] = 1.0f/estados;
				else
					transiciones[i][0][j] = ((float) valores0[j]) / total0;
				if (total1 == 0)
					transiciones[i][1][j] = 1.0f/estados;
				else
					transiciones[i][1][j] = ((float) valores1[j]) / total1;
			}
			//probfinal[i] = (double)(i + rand.nextInt(PARTICIONES)) / (estados + PARTICIONES);
			if (rand.nextBoolean())
				probfinal[i] = 1;
			else
				probfinal[i] = 0;
		}
		AFP automata = new AFP(estados);
		automata.setProbabilidadFinal(probfinal);
		automata.setTransiciones(transiciones);
		return automata;
	}
	
}
