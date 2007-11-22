package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.Iterator;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Writer;

public class Principal {

	public static void main(String[] args) {
		Writer.write("\n\nUtilizando version 0.1 del Selector\n");
		Writer.write("Utilizando version 0.1 del CalculadorBondadSimple\n");
		CalculadorBondad.setTipo(CalculadorBondad.SIMPLE);
		Writer.write("Utilizando version 0.11 del Generador Aleatorio\n");
		Writer.write("Utilizando version 0.12 del Reproductor");
		Writer.write("Problema a solucionar:\n");
		Writer.write("  aceptadas=");
		Iterator<String> it = ParametrosAFP.getInstance().getAceptadas().iterator();
		while(it.hasNext()) {
			Writer.write(it.next()+",");
		}
		it = ParametrosAFP.getInstance().getRechazadas().iterator();
		Writer.write("\n  rechazadas=");
		while(it.hasNext()) {
			Writer.write(it.next()+",");
		}
		Writer.write("\n");
		Iterator<Integer> itmuestras = ParametrosAFP.getInstance().getMuestras().iterator();
		while (itmuestras.hasNext()) {
			int i = itmuestras.next().intValue();
			Iterator<Integer> itpobmax = ParametrosAFP.getInstance().getPobmax().iterator();
			while(itpobmax.hasNext()) {
				int j = itpobmax.next().intValue();
				Writer.write("*******************************************\n");
				Writer.write("Mantener="+i+";Poblacion Max="+j+"\n");
				Algoritmo.MANTENER = i;
				Algoritmo.POB_MAX = 1000;
				AplicarAlgortimoAFP.aplicar(i, 50);
			}
		}
		
	}
}
