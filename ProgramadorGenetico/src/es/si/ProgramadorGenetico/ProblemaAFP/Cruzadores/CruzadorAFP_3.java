package es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores;

import java.util.Random;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

/**
 * Clase que implementa el cruzador TIPO_3
 * Entrecruza todos los miembros de la poblacion en orden y 
 * de forma ordenada, de tal forma que los cruza todos entre si
 * al menos una vez.
 * En el cruce, toma un valor aleatorio entre 0 y 1 para cada estado del automata 
 * y multiplica todas las transiciones del automata que salen de un estado
 * por el valor correspondiente. Después suma las probabilidades del segundo
 * automata multiplicando por 1-el valor anterior, manteniendo la suma 
 * de todas las probabilidades en 1   
 *
 */
public class CruzadorAFP_3 implements Cruzador {

	public static final double VERSION = 1.0f;

	private static Random rand = new Random();
	
	
	/**
	 * Entrecruzara todos los miembros entre si por lo menos una vez. Para ello, hace un bucle que tiene tantas
	 * iteraciones como las combinaciones del numero de mejores tomados de 2 en 2, porque no importa el orden
	 * Asi se garantiza que se mezclan todos los individuos entre si por lo menos 1 vez 
	 */	
	
	public Poblacion entrecruzar(int cant, Poblacion mejores) {
		Poblacion nueva = new Poblacion();
		int cruzados = 0;
		while (cruzados<cant) {
			for (int i=0; i<mejores.getCantidad()-2; i++) {
				for (int j=i+1; j<mejores.getCantidad()-1; j++) {
					nueva.agregarMiembro(cruzar((AFP)mejores.getMiembro(i),(AFP)mejores.getMiembro(j)));
					cruzados++;
				}			
			}
		}
		return nueva;
	}
	/**
	 * Metodo que cruza dos AFPs entre si
	 * @param a
	 * @param b
	 * @return
	 */
	private AFP cruzar(AFP a, AFP b) {
		int estados = ParametrosAFP.getInstance().getEstados();
		AFP nuevo = new AFP(estados);
		float[][][] transiciones = new float[estados][2][estados +1];
		float[] finales = new float[estados];
		float peso1, peso2;
		for (int i = 0; i < estados; i++) {
			peso1 = rand.nextFloat();
			peso2 = rand.nextFloat();
			for (int j = 0; j < estados + 1; j++) {
				transiciones[i][0][j] = a.getProbabilidad(i+1, 0, j)*peso1
										+ b.getProbabilidad(i+1, 0, j)*(1-peso1);
				transiciones[i][1][j] = a.getProbabilidad(i+1, 1, j)*peso2
										+ b.getProbabilidad(i+1, 1, j)*(1-peso2);
			}
			if (rand.nextBoolean())
				finales[i] = a.getProbabilidadFinal(i+1);
			else
				finales[i] = b.getProbabilidadFinal(i+1);
		}
		nuevo.setTransiciones(transiciones);
		nuevo.setProbabilidadFinal(finales);
		return nuevo;
	}
}
