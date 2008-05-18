package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import java.util.Iterator;
import java.util.Random;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

public class MutadorAFP_3 implements Mutador {

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
		float[][][] transiciones = afp.getTransiciones();
		int cuantos = rand.nextInt(estados*2);
		for (int j = 0; j < cuantos;j++) {
			int estado = rand.nextInt(estados);
			int valor = rand.nextInt(2);
			int max = 0;
			float maxval = 0;
			for (int i = 0; i < estados; i++) {
				if (transiciones[estado][valor][i] > maxval) {
					maxval = transiciones[estado][valor][i];
					max = i;
				}
			}
			int signo = rand.nextInt(2) - 1;
			float suma = 0.5f;
			if (transiciones[estado][valor][max] + signo*suma > 1) {
				suma = 1.0f - transiciones[estado][valor][max];
			}		
			if (transiciones[estado][valor][max] + signo*suma < 0) {
				suma = transiciones[estado][valor][max];
			}
			float quitado = 0.0f;
			for (int i = 0; i < estados; i++) {
				float temp = quitado;
				if (i != max) {
					if (transiciones[estado][valor][i] - signo*suma < 0) {
						quitado += transiciones[estado][valor][i];
						transiciones[estado][valor][i] = 0;
					} else {
						transiciones[estado][valor][i] -= signo*suma;
						quitado += suma;
					}
				}
				if (quitado > suma) {
					transiciones[estado][valor][i] += signo*(quitado - temp);
					quitado = temp;
				}
			}
			transiciones[estado][valor][max] += signo*quitado;
		}
		afp.setTransiciones(transiciones);	
	}
}
