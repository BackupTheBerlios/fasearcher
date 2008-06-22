package es.si.ProgramadorGenetico.ProblemaAFP.Mutadores;

import java.util.Iterator;
import java.util.Random;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Mutador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
/**
 * Clase que implementa el mutador TIPO_1
 *  
 *
 */
public class MutadorAFP_1 implements Mutador {
	/**
	 * Atributo que indica el numero de version
	 */
	public static final double VERSION = 1.1f;
	/**
	 * Variable Random para crear numeros aleatorios
	 */
	private static Random rand = new Random();
	/**
	 * Metodo que recorre todos los individuos de la poblacion
	 * y los muta
	 */
	public Poblacion mutar(Poblacion poblacion) {
		Iterator<Individuo> it = poblacion.getIterator();
		while(it.hasNext()) {
			mutarAFP((AFP) it.next());
		}
		return poblacion;
	}
	/**
	 * Para cada estado multiplica todas sus transiciones por si mismas
	 * Si el valor de todas es muy proximo a 0, pone a 0 todas las transiciones
	 * y a 1 una aleatoria
	 * Si no es proximo a 0, multiplica todas las transiciones por un valor que hace
	 * que la suma de todas las probabilidades sea igual a 1
	 * Con una probabilidad, es posible que para un estado, se pongan todas las transiciones a 0
	 * y una de ellas a 1  
	 * @param afp
	 */
	private void mutarAFP(AFP afp) {
		int estados = ParametrosAFP.getInstance().getEstados();
		float[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			if (rand.nextInt(estados/2) == 0) {
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
			} else if (rand.nextInt(estados*2) == 0) {
				int valor = rand.nextInt(2);
				for (int j = 0; j< estados + 1;j++)
					transiciones[i][valor][j] = 0;
				transiciones[i][valor][rand.nextInt(estados+1)] = 1;
			}
		}
		afp.setTransiciones(transiciones);	
	}

}
