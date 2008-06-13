package es.si.ProgramadorGenetico.ProblemaAFP;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.si.ProgramadorGenetico.Algoritmo;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;
import es.si.ProgramadorGenetico.WS.SetSolucionWS;
import es.si.ProgramadorGenetico.util.Config;
/**
 * Clase que representa un problema
 * para el algoritmo genetico
 *
 */
public class ProblemaAFP {
	/**
	 * El mejor AFP
	 */
	private AFP mejor;
	/**
	 * Lista de mejores AFPs
	 */
	private List<AFP> mejores;
	/**
	 * Indica si el problema se debe buscar en un
	 * archivo local o en internet
	 */
	private boolean local;
	/**
	 * Constructora que inicializa los atributos
	 */
	public ProblemaAFP() {
		mejores = new ArrayList<AFP>();
		CruzadorAFPFactory.setTipo(CruzadorAFPFactory.TIPO_1);
		MutadorAFPFactory.setTipo(MutadorAFPFactory.TIPO_1);
		ResolverAFPFactory.setTipo(ResolverAFPFactory.VECTORES);
		CalculadorBondadAFPFactory.setTipo(CalculadorBondadAFPFactory.PREFERNCIADET);
		local = false;
	}
	/**
	 * Metodo que inicializa los valores del algoritmo y los parametrosAFP
	 * a partir de los datos de Internet
	 */
	public void ejecutar() {
		Config config = Config.getInstance();
		
		if (!local && config.getProperty("usarinternet").equals("true")) {
			ParametrosAFP.setOrigen(ParametrosAFP.FROM_WEB);
			ParametrosAFP.recrear();
			CalculadorBondadAFPFactory.setTipo(ParametrosAFP.getInstance().getCalculadorBondad());
			CruzadorAFPFactory.setTipo(ParametrosAFP.getInstance().getCruzador());
			ResolverAFPFactory.setTipo(ParametrosAFP.getInstance().getResolver());
			MutadorAFPFactory.setTipo(ParametrosAFP.getInstance().getMutador());
		}
		
		for (Integer value : ParametrosAFP.getInstance().getMuestras()) {
			int i = value.intValue();
			for (Integer pobmax : ParametrosAFP.getInstance().getPobmax()) {
				int j = pobmax.intValue();
				Algoritmo.MANTENER = i;
				Algoritmo.POB_MAX = j;
				AplicarAlgoritmoAFP.aplicar(ParametrosAFP.getInstance().getParticiones(), 50);
				
				mejor = AplicarAlgoritmoAFP.getMejor();				
				
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
					setSolucionWS.setReconocimiento(nf.format(this.getReconocimiento()));
					setSolucionWS.setParecidoAF(nf.format(this.getParecidoAF()));
					setSolucionWS.setMutador(MutadorAFPFactory.getVersion());
					setSolucionWS.setCruzador(CruzadorAFPFactory.getVersion());
					setSolucionWS.setFuncbondad(CalculadorBondadAFPFactory.getVersion());
					setSolucionWS.setMetodoRes(ResolverAFPFactory.getVersion());
					setSolucionWS.setParticiones(new Integer(ParametrosAFP.getInstance().getParticiones()));
					setSolucionWS.setId_config(ParametrosAFP.getInstance().getId_config());
					setSolucionWS.ejecutar();
				}
			}
		}

		mejores = AplicarAlgoritmoAFP.getMejores();
	}
	/**
	 * Devuelve el mejor AFP
	 * @return
	 */
	public AFP getMejor() {
		return mejor;
	}
	/**
	 * Devuelve los mejores
	 * @return
	 */
	public List<AFP> getMejores() {
		return mejores;
	}
	/**
	 * Devuelve el porcentaje de acierto
	 * en el reconocimiento de cadenas
	 * @return
	 */
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
	/**
	 * Devuelve el porcentaje de parecido
	 * con un AF
	 * @return
	 */
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
	/**
	 * Actualiza el valor local
	 * @param local
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}
}
