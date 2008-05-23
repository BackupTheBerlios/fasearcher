package es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores;

import java.util.Random;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;


public class CruzadorAFP_4 implements Cruzador {

	public static final double VERSION = 2.0f;

	private static Random rand = new Random();
	
	@Override
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
