package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;

import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.ProblemaAFP.GeneradorAleatorioAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.SelectorAFP;

public class PruebaSeleccionador {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Poblacion pob = new Poblacion();
		for (int i = 0; i < 1000; i++) {
			pob.agregarMiembro(GeneradorAleatorioAFP.nuevo(ParametrosAFP.getInstance().getEstados()));
		}
		SelectorAFP selector = new SelectorAFP();
		long tiempo = System.currentTimeMillis();
		selector.mejor(pob);
		System.out.println("tiempo: " + (System.currentTimeMillis() - tiempo));
	}

}
