package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.Iterator;
import java.util.Random;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.Reproductor;

public class ReproductorAFP implements Reproductor {

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

	@Override
	public Poblacion mutar(Poblacion poblacion) {
		Iterator<Individuo> it = poblacion.getIterator();
		while(it.hasNext()) {
			mutarAFP((AFP) it.next());
		}
		return poblacion;
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
	
	private void mutarAFP(AFP afp) {
		int estados = ParametrosAFP.getInstance().getEstados();
		double[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			if (rand.nextInt(estados/2) == 0) {
				int valor = rand.nextInt(2);
				double suma = 0;
				for (int j = 0; j < estados + 1; j++) {
					transiciones[i][valor][j] = transiciones[i][valor][j]*transiciones[i][valor][j];
					suma += transiciones[i][valor][j];
				}	
				if (suma < 0.0001) {
					transiciones[i][valor][rand.nextInt(estados+1)] = 1;
				}
				else {
					double div = 1.0f / suma;
					for (int j = 0; j < estados + 1; j++) {
						transiciones[i][valor][j] = transiciones[i][valor][j] * div;
					}
				}
			} else if (rand.nextInt(estados*2) == 0) {
				int valor = rand.nextInt(2);
				for (int j = 0; j< estados + 1;j++) {
					transiciones[i][valor][j] = 0;
				}
				transiciones[i][valor][rand.nextInt(estados+1)] = 1;
			}
		}
		afp.setTransiciones(transiciones);	
	}
}
