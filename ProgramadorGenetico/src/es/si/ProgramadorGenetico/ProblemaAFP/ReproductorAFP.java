package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.Random;

import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.Reproductor;

public class ReproductorAFP implements Reproductor {

	@Override
	public Poblacion entrecruzar(int cant, Poblacion mejores) {
		int tam = mejores.getCantidad();
		Poblacion nueva = new Poblacion();
		Random rand = new Random();
		
		for (int i = 0; i < cant; i++) {
			nueva.agregarMiembro(cruzar((AFP)mejores.getMiembro(rand.nextInt(tam)), (AFP)mejores.getMiembro(rand.nextInt(tam)), rand.nextDouble()));
		}
		
		return nueva;
	}

	@Override
	public Poblacion mutar(Poblacion poblacion) {
		return poblacion;
	}

	private AFP cruzar(AFP a, AFP b, double peso) {
		int estados = ParametrosAFP.getInstance().getEstados();
		AFP nuevo = new AFP(estados);
		double[][][] transiciones = new double[estados][2][estados +1];
		double[] finales = new double[estados];
		double menospeso = 1 - peso;
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j < estados + 1; j++) {
				transiciones[i][0][j] = a.getProbabilidad(i+1, 0, j)*peso + b.getProbabilidad(i+1, 0, j)*menospeso;
				transiciones[i][1][j] = a.getProbabilidad(i+1, 1, j)*peso + b.getProbabilidad(i+1, 1, j)*menospeso;
			}
			finales[i]= a.getProbabilidadFinal(i+1)*peso + b.getProbabilidadFinal(i+1)*menospeso;
		}
		nuevo.setTransiciones(transiciones);
		nuevo.setProbabilidadFinal(finales);
		return nuevo;
	}
}
