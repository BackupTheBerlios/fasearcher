package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import java.util.Iterator;
import java.util.Random;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

public class MutadorAFP_1 implements Mutador {

	public static final double VERSION = 1.0f;

	private static Random rand = new Random();

	@Override
	public Poblacion mutar(Poblacion poblacion) {
		Iterator<Individuo> it = poblacion.getIterator();
		while(it.hasNext()) {
			mutarAFP((AFP) it.next());
		}
		return poblacion;
	}
	private void mutarAFP(AFP afp) {
		int estados = ParametrosAFP.getInstance().getEstados();
		double[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			if (rand.nextInt(estados/2) == 0) {
				int valor = rand.nextInt(2);
				double suma = 0;
				for (int j = 0; j < estados + 1; j++) {
					transiciones[i][valor][j] = transiciones[i][valor][j]*transiciones[i][valor][j];
					suma += transiciones[i][valor][j];
				}	
				if (suma < 0.0001) {
					transiciones[i][valor][rand.nextInt(estados+1)] = 1;
				}
				else {
					double div = 1.0f / suma;
					for (int j = 0; j < estados + 1; j++) {
						transiciones[i][valor][j] = transiciones[i][valor][j] * div;
					}
				}
			} else if (rand.nextInt(estados*2) == 0) {
				int valor = rand.nextInt(2);
				for (int j = 0; j< estados + 1;j++) {
					transiciones[i][valor][j] = 0;
				}
				transiciones[i][valor][rand.nextInt(estados+1)] = 1;
			}
		}
		afp.setTransiciones(transiciones);	
	}

}
