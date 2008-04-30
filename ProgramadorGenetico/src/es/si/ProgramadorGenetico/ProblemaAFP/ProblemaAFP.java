package es.si.ProgramadorGenetico.ProblemaAFP;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;
import es.si.ProgramadorGenetico.WS.SetSolucionWS;
import es.si.ProgramadorGenetico.util.Config;

public class ProblemaAFP {
	
	private AFP mejor;
	
	private List<AFP> mejores;
	
	public ProblemaAFP() {
		mejores = new ArrayList<AFP>();
	}
	
	public void ejecutar() {
		CruzadorAFPFactory.setTipo(CruzadorAFPFactory.TIPO_1);
		MutadorAFPFactory.setTipo(MutadorAFPFactory.TIPO_1);
		ResolverAFPFactory.setTipo(ResolverAFPFactory.VECTORES);
		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.PREFERNCIADET);
		
		Config config = Config.getInstance();
		
		if (config.getProperty("usarinternet").equals("true")) {
		ParametrosAFP.setOrigen(ParametrosAFP.FROM_WEB);
		ParametrosAFP.recrear();
		}
		
		for (Integer value : ParametrosAFP.getInstance().getMuestras()) {
			int i = value.intValue();
			Iterator<Integer> itpobmax = ParametrosAFP.getInstance().getPobmax().iterator();
			while(itpobmax.hasNext()) {
				int j = itpobmax.next().intValue();
				Algoritmo.MANTENER = i;
				Algoritmo.POB_MAX = j;
				AplicarAlgoritmoAFP.aplicar(ParametrosAFP.getInstance().getParticiones(), 50);
				
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
					setSolucionWS.setMejorValor(nf.format(temp.getBondad()));
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
		mejores = AplicarAlgoritmoAFP.getMejores();
	}

	public AFP getMejor() {
		return mejor;
	}

	public List<AFP> getMejores() {
		return mejores;
	}
}
