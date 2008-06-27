package es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores;

import java.util.Random;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

/**
 * Clase que implementa el cruzador TIPO_4
 * Al entrecruzar los miembros, escoge algunos de forma ordenada, y el resto
 * de forma aleatoria
 * En el cruce, toma un valor aleatorio entre 0 y 1 para cada estado del automata 
 * y multiplica todas las transiciones del automata que salen de un estado
 * por el valor correspondiente. Después suma las probabilidades del segundo
 * automata multiplicando por 1-el valor anterior, manteniendo la suma 
 * de todas las probabilidades en 1   
 */
public class CruzadorAFP_4 implements Cruzador {

	public static final double VERSION = 2.0f;

	private static Random rand = new Random();
	
	/**
	 * Entrecruza algunos miembros de forma ordenada y el resto aleatoriamente
	 */
	public Poblacion entrecruzar(int cant, Poblacion mejores) {
		int tam = mejores.getCantidad();
		Poblacion nueva = new Poblacion();
		int cont = 0;
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < cant / tam; j++) {
				nueva.agregarMiembro(cruzar((AFP) mejores.getMiembro(i), (AFP) mejores.getMiembro(j % tam)));
				cont++;
			}
		}
		while (cont < cant) {
			nueva.agregarMiembro(cruzar((AFP)mejores.getMiembro(rand.nextInt(tam)), (AFP)mejores.getMiembro(rand.nextInt(tam))));
			cont++;
		}
		
		return nueva;
	}
	/**
	 * Calcula los valores aleatorios y crea las transiciones
	 * para el nuevo AFP
	 * @param a
	 * @param b
	 * @return
	 */
	private AFP cruzar(AFP a, AFP b) {
		int estados = ParametrosAFP.getInstance().getEstados();
		AFP nuevo = new AFP(estados);
		float[][][] transiciones = new float[estados][2][estados +1];
		float[] finales = new float[estados];
		float peso1 = 0.1f * (float) rand.nextInt(11);
		float peso2 = 0.1f * (float) rand.nextInt(11);
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j < estados + 1; j++) {
				transiciones[i][0][j] = a.getProbabilidad(i+1, 0, j)*peso1
										+ b.getProbabilidad(i+1, 0, j)*(1-peso1);
				transiciones[i][1][j] = a.getProbabilidad(i+1, 1, j)*peso2
										+ b.getProbabilidad(i+1, 1, j)*(1-peso2);
			}
			if (peso1 >= peso2)
				finales[i] = a.getProbabilidadFinal(i+1);
			else
				finales[i] = b.getProbabilidadFinal(i+1);
		}
		nuevo.setTransiciones(transiciones);
		nuevo.setProbabilidadFinal(finales);
		return nuevo;
	}
}
