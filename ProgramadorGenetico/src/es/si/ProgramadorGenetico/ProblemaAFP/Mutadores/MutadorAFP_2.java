package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import java.util.Iterator;
import java.util.Random;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
/**
 * Clase que implementa el mutador TIPO_2 
 */
public class MutadorAFP_2 implements Mutador {
	/**
	 * Atributo que indica el numero de version
	 */
	public static final double VERSION = 1.0f;
	/**
	 * Atributo que representa un Random para generar numeros aleatorios
	 */
	private static Random rand = new Random();

	/**
	 * Recorre todos los miembros de la poblacion y los muta
	 */
	public Poblacion mutar(Poblacion poblacion) {
		Iterator<Individuo> it = poblacion.getIterator();
		while(it.hasNext()) {
			mutarAFP((AFP) it.next());
		}
		return poblacion;
	}
	/**
	 * Para mutar un miembro se busca el valor minimo de todas las transiciones de cada estado
	 * Se resta ese valor a todas las transiciones que salen de ese estado, y luego se multiplican
	 * las transiciones por un valor para que la suma de todas siga siendo 1
	 * @param afp
	 */
	private void mutarAFP(AFP afp) {
		int estados = ParametrosAFP.getInstance().getEstados();
		float[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			if (rand.nextInt(100) == 5) {
				int valor = rand.nextInt(2);
				float minval = 1;
				float suma = 0;
				int total = 0;
				for (int j = 0; j < estados + 1; j++) {
					suma += transiciones[i][valor][j];
					if (transiciones[i][valor][j] > 0.001)
						total++;
					if (transiciones[i][valor][j] > 0.001 && transiciones[i][valor][j] < minval)
						minval = transiciones[i][valor][j];
				}	
				float div = 1 / (suma - minval*total);
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
