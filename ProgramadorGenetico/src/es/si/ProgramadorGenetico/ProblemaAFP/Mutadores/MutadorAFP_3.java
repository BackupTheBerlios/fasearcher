package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import java.util.Iterator;
import java.util.Random;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
/**
 * Clase que implementa el mutador TIPO_3
 *
 */
public class MutadorAFP_3 implements Mutador {
	/**
	 * Atributo que indica el numero de version
	 */
	public static final double VERSION = 1.0f;
	/**
	 * Atributo Random que sirve para obtener valores aleatorios
	 */
	private static Random rand = new Random();

	/**
	 * Recorre todos los miembros y los muta
	 */
	public Poblacion mutar(Poblacion poblacion) {
		Iterator<Individuo> it = poblacion.getIterator();
		while(it.hasNext()) {
			mutarAFP((AFP) it.next());
		}
		return poblacion;
	}
	/**
	 * Muta los miembros multiplicando todas las transiciones por si mismas
	 * Luego se reajustan los valores multiplicando por un valor que mantiene
	 * la suma de todas las probabilidades de las transiciones en 1
	 * Con una probabilidad, da valor 0 a todas las transiciones de un estado
	 * menos a 1, que pone a 1 
	 * @param afp
	 */
	private void mutarAFP(AFP afp) {
		int estados = ParametrosAFP.getInstance().getEstados();
		float[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			if (rand.nextInt((estados+1)/2) == 0) {
				int valor = rand.nextInt(2);
				float suma = 0;
				for (int j = 0; j < estados + 1; j++) {
					transiciones[i][valor][j] = transiciones[i][valor][j]*transiciones[i][valor][j];
					if (transiciones[i][valor][j] < 0.01)
						transiciones[i][valor][j] = 0;
					suma += transiciones[i][valor][j];
				}
				if (suma < 0.01) {
					for (int j = 0; j< estados + 1;j++)
						transiciones[i][valor][j] = 0;
					transiciones[i][valor][rand.nextInt(estados+1)] = 1;
				}
				else {
					float div = 1.0f / suma;
					for (int j = 0; j < estados + 1; j++)
						transiciones[i][valor][j] = transiciones[i][valor][j] * div;
				}
			} 
			if (rand.nextInt((int)(estados*1.5)) == 0) {
				int valor = rand.nextInt(2);
				for (int j = 0; j< estados + 1;j++)
					transiciones[i][valor][j] = 0;
				transiciones[i][valor][rand.nextInt(estados+1)] = 1;
			}
		}
		afp.setTransiciones(transiciones);	
	}
}
