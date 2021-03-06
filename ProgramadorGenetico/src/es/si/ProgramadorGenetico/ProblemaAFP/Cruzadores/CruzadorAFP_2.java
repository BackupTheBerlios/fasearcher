package es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores;

import java.util.Random;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

/**
 * Clase que implementa el segundo tipo de cruzador
 * Cruza aleatoriamente los miembros de la poblacion
 * En el cruce, escoge para cada estado, las transiciones
 * del primer automata o las transiciones del segundo automata  
 *	
 */
public class CruzadorAFP_2 implements Cruzador {

	public static final double VERSION = 1.0f;

	private static Random rand = new Random();
	
	/**
	 * Metodo que elige aleatoriamente los dos miembros a cruzar
	 */
	public Poblacion entrecruzar(int cant, Poblacion mejores) {
		int tam = mejores.getCantidad();
		Poblacion nueva = new Poblacion();
		for (int i = 0; i < cant; i++) {
			nueva.agregarMiembro(cruzar((AFP)mejores.getMiembro(rand.nextInt(tam)), (AFP)mejores.getMiembro(rand.nextInt(tam))));
		}
		return nueva;
	}
	/**
	 * Metodo que cruza los AFPs a y b
	 * y crea el nuevo AFP
	 * @param a
	 * @param b
	 * @return
	 */
	private AFP cruzar(AFP a, AFP b) {
		int estados = ParametrosAFP.getInstance().getEstados();
		AFP nuevo = new AFP(estados);
		float[][][] transiciones = new float[estados][2][estados +1];
		float[] finales = new float[estados];
		int aut;
		for (int i = 0; i < estados; i++) {
			aut = rand.nextInt(2);
			for (int j = 0; j < estados + 1; j++) {						
				if (aut==0) {
					transiciones[i][0][j] = a.getProbabilidad(i+1, 0, j);
					transiciones[i][1][j] = a.getProbabilidad(i+1, 1, j);
				}
				else {//aut==1
					transiciones[i][0][j] = b.getProbabilidad(i+1,0,j);
					transiciones[i][1][j] = b.getProbabilidad(i+1,1,j);
				}
			}
			if (aut==0) {
				finales[i] = a.getProbabilidadFinal(i+1);
			}
			else
				finales[i] = b.getProbabilidadFinal(i+1);
		}
		nuevo.setTransiciones(transiciones);
		nuevo.setProbabilidadFinal(finales);
		return nuevo;
	}
}
