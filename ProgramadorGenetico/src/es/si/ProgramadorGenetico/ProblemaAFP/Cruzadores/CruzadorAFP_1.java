package es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores;

import java.util.Random;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;


public class CruzadorAFP_1 implements Cruzador {

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
		double[][][] transiciones = new double[estados][2][estados +1];
		double[] finales = new double[estados];
		double peso1, peso2;
		for (int i = 0; i < estados; i++) {
			peso1 = rand.nextDouble();
			peso2 = rand.nextDouble();
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
