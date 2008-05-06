package es.si.ProgramadorGenetico.ProblemaAFP;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.Writer;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.*;
import es.si.ProgramadorGenetico.WS.SetSolucionWS;
import es.si.ProgramadorGenetico.util.Config;

public class Principal {

	static AFP mejor;
	public static void main(String[] args) {
		CruzadorAFPFactory.setTipo(CruzadorAFPFactory.TIPO_1);
		MutadorAFPFactory.setTipo(MutadorAFPFactory.TIPO_1);
		ResolverAFPFactory.setTipo(ResolverAFPFactory.VECTORES);
		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.PREFERNCIADET);
		
		Writer.write("\n\nUtilizando version 1.0 del Selector\n");
		Writer.write("Utilizando version 1.0 del CalculadorBondadBalanceado\n");
		Writer.write("Utilizando version 1.0 del Generador Aleatorio\n");
		Writer.write("Utilizando version 1.0 del Reproductor");
		Writer.write("Problema a solucionar:\n");
		Writer.write("  aceptadas=");
		
		Config config = Config.getInstance();
		
		if (config.getProperty("usarinternet").equals("true")) {
		ParametrosAFP.setOrigen(ParametrosAFP.FROM_WEB);
		ParametrosAFP.recrear();
		}
		
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
				Algoritmo.POB_MAX = j;
				AplicarAlgoritmoAFP.aplicar(ParametrosAFP.getInstance().getParticiones(), 50);
				System.out.println("hola");
				
				if (config.getProperty("usarinternet").equals("true")) {
					SetSolucionWS setSolucionWS = new SetSolucionWS();
					setSolucionWS.setAFP(AplicarAlgoritmoAFP.getMejor());
					setSolucionWS.setMuestras(i);
					setSolucionWS.setId(ParametrosAFP.getInstance().getId());
					setSolucionWS.setPobmax(j);
					setSolucionWS.setPasos(AplicarAlgoritmoAFP.getPasos());
					CalculadorBondad temp = CalculadorBondadAFPFactory.getCalculadorBondadAFP(AplicarAlgoritmoAFP.getMejor(), ParametrosAFP.getInstance().getAceptadas(), ParametrosAFP.getInstance().getRechazadas());
					temp.run();
					NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
					nf.setMaximumFractionDigits(4);
					//setSolucionWS.setMejorValor(nf.format(temp.getBondad()));
					setSolucionWS.setMutador(MutadorAFPFactory.getVersion());
					setSolucionWS.setCruzador(CruzadorAFPFactory.getVersion());
					setSolucionWS.setFuncbondad(CalculadorBondadAFPFactory.getVersion());
					setSolucionWS.setMetodoRes(ResolverAFPFactory.getVersion());
					setSolucionWS.setParticiones(new Integer(ParametrosAFP.getInstance().getParticiones()));
					setSolucionWS.ejecutar();
				}
			}
		}
		mejor = AplicarAlgoritmoAFP.getMejor();
	}
	
	public static void ejecuta(List<String> aceptadas, List<String> rechazadas, int numEstados) {
		CruzadorAFPFactory.setTipo(CruzadorAFPFactory.TIPO_1);
		MutadorAFPFactory.setTipo(MutadorAFPFactory.TIPO_1);
		ResolverAFPFactory.setTipo(ResolverAFPFactory.VECTORES);
		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.PREFERNCIADET);
		
		Writer.write("\n\nUtilizando version 0.1 del Selector\n");
		Writer.write("Utilizando version 0.1 del CalculadorBondadBalanceado\n");
		Writer.write("Utilizando version 0.11 del Generador Aleatorio\n");
		Writer.write("Utilizando version 0.13 del Reproductor");
		Writer.write("Problema a solucionar:\n");
		Writer.write("  aceptadas=");
		ParametrosAFP.getInstance().setAceptadas(aceptadas);
		ParametrosAFP.getInstance().setRechazadas(rechazadas);
		ParametrosAFP.getInstance().setNumeroMuestras(aceptadas.size()+rechazadas.size());
		ParametrosAFP.getInstance().setEstados(numEstados);
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
				Algoritmo.POB_MAX = j;
				AplicarAlgoritmoAFP.aplicar(i, 50);
			}
		}
		mejor = AplicarAlgoritmoAFP.getMejor();		
	}
	
	public static AFP getMejor() {
		return mejor;
	}
}
