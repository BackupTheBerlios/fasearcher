package es.si.ProgramadorGenetico.ProblemaAFP.Cruzadores;

import es.si.ProgramadorGenetico.Cruzador;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;

public class CruzadorAFPNulo implements Cruzador {

	public static final double VERSION = 1.0f;

	@Override
	public Poblacion entrecruzar(int cant, Poblacion mejores) {
		int tam = mejores.getCantidad();
		Poblacion nueva = new Poblacion();
		int canttipo = cant / tam;
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < canttipo; j++) {
				nueva.agregarMiembro(clon((AFP)mejores.getMiembro(i)));
			}
		}
		for (int i = 0; i < cant % tam; i++) {
			nueva.agregarMiembro(clon((AFP)mejores.getMiembro(tam-1)));			
		}
		return nueva;
	}
	
	private AFP clon(AFP a) {
		int estados = ParametrosAFP.getInstance().getEstados();
		AFP nuevo = new AFP(estados);
		double[][][] transiciones = new double[estados][2][estados +1];
		double[] finales = new double[estados];
		for (int i = 0; i < estados; i++) {
			for (int j = 0; j < estados + 1; j++) {
				transiciones[i][0][j] = a.getProbabilidad(i+1, 0, j);
				transiciones[i][1][j] = a.getProbabilidad(i+1, 1, j);
			}
			finales[i] = a.getProbabilidadFinal(i+1);
		}
		nuevo.setTransiciones(transiciones);
		nuevo.setProbabilidadFinal(finales);
		return nuevo;
	}
}
