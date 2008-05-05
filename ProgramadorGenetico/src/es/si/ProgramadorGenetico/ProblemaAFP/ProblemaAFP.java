package es.si.ProgramadorGenetico.ProblemaAFP;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadPrefernciaDet;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;
import es.si.ProgramadorGenetico.WS.SetSolucionWS;
import es.si.ProgramadorGenetico.util.Config;

public class ProblemaAFP {
	
	private AFP mejor;
	
	private List<AFP> mejores;
	
	private boolean local;
	
	public ProblemaAFP() {
		mejores = new ArrayList<AFP>();
		CruzadorAFPFactory.setTipo(CruzadorAFPFactory.TIPO_1);
		MutadorAFPFactory.setTipo(MutadorAFPFactory.TIPO_1);
		ResolverAFPFactory.setTipo(ResolverAFPFactory.VECTORES);
		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.PREFERNCIADET);
		local = false;
	}
	
	public void ejecutar() {
		Config config = Config.getInstance();
		
		if (!local && config.getProperty("usarinternet").equals("true")) {
			ParametrosAFP.setOrigen(ParametrosAFP.FROM_WEB);
			ParametrosAFP.recrear();
		}
		
		for (Integer value : ParametrosAFP.getInstance().getMuestras()) {
			int i = value.intValue();
			for (Integer pobmax : ParametrosAFP.getInstance().getPobmax()) {
				int j = pobmax.intValue();
				Algoritmo.MANTENER = i;
				Algoritmo.POB_MAX = j;
				AplicarAlgoritmoAFP.aplicar(ParametrosAFP.getInstance().getParticiones(), 50);
				
				if (!local && config.getProperty("usarinternet").equals("true")) {
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
	
	public float getReconocimiento() {
		double prob = 0;
		for (String cadena : ParametrosAFP.getInstance().getAceptadas()) {
			ResolverAFP resolver = ResolverAFPFactory.getResolverAFP();
			resolver.setAFP(mejor);
			resolver.setCadena(cadena);
			resolver.run();
			prob += resolver.getProbabilidadAceptar();
		}
		for (String cadena : ParametrosAFP.getInstance().getRechazadas()) {
			ResolverAFP resolver = ResolverAFPFactory.getResolverAFP();
			resolver.setAFP(mejor);
			resolver.setCadena(cadena);
			resolver.run();
			prob += 1 - resolver.getProbabilidadAceptar();
		}
		return (float) (prob / (ParametrosAFP.getInstance().getAceptadas().size() +ParametrosAFP.getInstance().getRechazadas().size()) );
	}
	
	public float getParecidoAF() {
		if (mejor != null) {
			int estados = mejor.getEstados();
			float[][][] trans = mejor.getTransiciones();
			
			float valor =0;
			float suma = 0;
			for (int i = 0; i < estados; i++) {
				for (int j = 0; j < estados + 1; j++) {
					if (trans[i][0][j] > 0.5)
						suma += trans[i][0][j];
					if (trans[i][1][j] > 0.5)
						suma += trans[i][1][j];
				}
			}
			valor = suma / (estados*2);
			return valor;
		}
		return 0;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}
}
