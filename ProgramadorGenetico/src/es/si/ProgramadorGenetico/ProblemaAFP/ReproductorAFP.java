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
		double menospeso;
		double peso1, peso2;
		for (int i = 0; i < estados; i++) {
			peso1 = rand.nextDouble();
			peso2 = rand.nextDouble();
			for (int j = 0; j < estados + 1; j++) {
				transiciones[i][0][j] = a.getProbabilidad(i+1, 0, j)*peso1 + b.getProbabilidad(i+1, 0, j)*(1-peso1);
				transiciones[i][1][j] = a.getProbabilidad(i+1, 1, j)*peso2 + b.getProbabilidad(i+1, 1, j)*(1-peso2);
			}
			peso1 = rand.nextDouble();
			menospeso = 1 - peso1;
			finales[i]= a.getProbabilidadFinal(i+1)*peso1 + b.getProbabilidadFinal(i+1)*menospeso;
		}
		nuevo.setTransiciones(transiciones);
		nuevo.setProbabilidadFinal(finales);
		return nuevo;
	}
	
	private void mutarAFP(AFP afp) {
		int estados = ParametrosAFP.getInstance().getEstados();
		double[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			if (rand.nextInt(estados) == 0) {
				int valor = rand.nextInt(2);
				double minval = 1;
				double suma = 0;
				int total = 0;
				for (int j = 0; j < estados + 1; j++) {
					suma += transiciones[i][valor][j];
					if (transiciones[i][valor][j] > 0)
						total++;
					if (transiciones[i][valor][j] > 0 && transiciones[i][valor][j] < minval)
						minval = transiciones[i][valor][j];
				}
				double div = 1 / (suma - minval*total);
				for (int j = 0; j < estados + 1; j++) {
					if (transiciones[i][valor][j] != 0) {
						transiciones[i][valor][j] = (transiciones[i][valor][j] - minval) * div;
					}
				}
			}
		}
		afp.setTransiciones(transiciones);
		
	}
}
