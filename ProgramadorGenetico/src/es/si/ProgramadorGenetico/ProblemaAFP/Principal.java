package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.Iterator;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Writer;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.*;

public class Principal {

	public static void main(String[] args) {
		CruzadorAFPFactory.setTipo(CruzadorAFPFactory.TIPO_1);
		MutadorAFPFactory.setTipo(MutadorAFPFactory.TIPO_1);
		ResolverAFPFactory.setTipo(ResolverAFPFactory.VECTORES);
		
		Writer.write("\n\nUtilizando version 0.1 del Selector\n");
		Writer.write("Utilizando version 0.1 del CalculadorBondadBalanceado\n");
		CalculadorBondad.setTipo(CalculadorBondad.BALANACEADO);
		Writer.write("Utilizando version 0.11 del Generador Aleatorio\n");
		Writer.write("Utilizando version 0.13 del Reproductor");
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
