package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.Iterator;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Writer;

public class Principal {

	public static void main(String[] args) {
		Writer.write("Utilizando version 0.1 del Selector\n");
		Writer.write("Utilizando version 0.1 del CalculadorBondadSimple\n");
		Writer.write("Utilizando version 0.1 del Generador Aleatorio\n");
		Writer.write("Problema a solucionar:\n");
		Writer.write("  aceptadas=");
		Iterator<String> it = ParametrosAFP.getInstance().getAceptadas().iterator();
		while(it.hasNext()) {
			Writer.write(it.next());
		}
		it = ParametrosAFP.getInstance().getRechazadas().iterator();
		Writer.write("\n  rechazadas=");
		while(it.hasNext()) {
			Writer.write(it.next());
		}
		Writer.write("\n");
		for (int i = 5; i <= 30; i+=5) {
			for (int j = 100; j <= 1100; j+=200) {
				Writer.write("***************************************\n");
				Writer.write("Mantener="+i+";Poblacion Max="+j+"\n");
				Algoritmo.MANTENER = i;
				Algoritmo.POB_MAX = 1000;
				AplicarAlgortimoAFP.aplicar(i, 50);
			}
		}
		
	}
}
