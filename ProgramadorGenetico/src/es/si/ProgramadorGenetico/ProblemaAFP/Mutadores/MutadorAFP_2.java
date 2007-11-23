package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import java.util.Iterator;
import java.util.Random;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

public class MutadorAFP_2 implements Mutador {

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
			if (rand.nextInt(100) == 5) {
				int valor = rand.nextInt(2);
				double minval = 1;
				double suma = 0;
				int total = 0;
				for (int j = 0; j < estados + 1; j++) {
					suma += transiciones[i][valor][j];
					if (transiciones[i][valor][j] > 0.001)
						total++;
					if (transiciones[i][valor][j] > 0.001 && transiciones[i][valor][j] < minval)
						minval = transiciones[i][valor][j];
				}	
				double div = 1 / (suma - minval*total);
				for (int j = 0; j < estados + 1; j++) {
					if (transiciones[i][valor][j] > 0.001) {
						transiciones[i][valor][j] = (transiciones[i][valor][j] - minval) * div;
					}
				}
			}
		}
		afp.setTransiciones(transiciones);	
	}
}
