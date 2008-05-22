package es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores;

import java.util.Random;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;


public class CruzadorAFP_4 implements Cruzador {

	public static final double VERSION = 1.0f;

	private static Random rand = new Random();
	
	@Override
	public Poblacion entrecruzar(int cant, Poblacion mejores) {
		int tam = mejores.getCantidad();
		Poblacion nueva = new Poblacion();
		for (int i = 0; i < cant; i++) {
			nueva.agregarMiembro(cruzar((AFP)mejores.getMiembro(rand.nextInt(tam)), (AFP)mejores.getMiembro(rand.nextInt(tam))));
		}
		return nueva;
	}
	
	private AFP cruzar(AFP a, AFP b) {
		int estados = ParametrosAFP.getInstance().getEstados();
		AFP nuevo = new AFP(estados);
		float[][][] transiciones = new float[estados][2][estados +1];
		float[] finales = new float[estados];

		int estado = rand.nextInt(estados);
		
		for (int i = 0; i < estados; i++) {
			if (estado == i) {
				for (int j = 0; j < estados + 1; j++) {
					transiciones[i][0][j] = a.getProbabilidad(i+1, 0, j);
					transiciones[i][1][j] = a.getProbabilidad(i+1, 1, j);
				}
			} else {
				for (int j = 0; j < estados + 1; j++) {
					transiciones[i][0][j] = b.getProbabilidad(i+1, 0, j);
					transiciones[i][1][j] = b.getProbabilidad(i+1, 1, j);
				}
			}
			finales[i] = a.getProbabilidadFinal(i+1);
		}
		nuevo.setTransiciones(transiciones);
		nuevo.setProbabilidadFinal(finales);
		return nuevo;
	}
}