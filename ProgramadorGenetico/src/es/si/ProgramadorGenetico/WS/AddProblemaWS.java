package es.si.ProgramadorGenetico.WS;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.axis2.AxisFault;

import es.si.ProgramadorGenetico.WS.FASearcherServiceBeanServiceStub.*;
import es.si.ProgramadorGenetico.util.Config;

/**
 * Esta clase es utilizada para contactar al serividor y añadir
 * un nuevo problema en la BBDD.<p>
 * 
 * Para usar este método hay que crear una instacia, luego dar
 * valores a los distintos parámetos y por ultimo, al llamar
 * al método "ejecutar" se envía la información al servidor.
 * Una vez enviada la información al servidor este devuevle
 * el id del problema, que se puede obtener de la instacia.
 */
public class AddProblemaWS {

	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private String tipoAutomata;
	
	private Afp afp;
	
	private Integer estados;
	
	private Integer pobMax;
	
	private String id;
	
	public boolean ejecutar() {
			try {
				String server = Config.getInstance().getProperty("FASearcherServerService");
				FASearcherServiceBeanServiceStub fassbss = new FASearcherServiceBeanServiceStub(server);
				
				AddProblemaRequest apr = new AddProblemaRequest();
				
				apr.setAceptadas((String[]) aceptadas.toArray());
				apr.setRechazadas((String[]) rechazadas.toArray());
				apr.setTipoAutomata(tipoAutomata);
				apr.setAfp(afp);
				apr.setEstados(estados);
				apr.setPobMax(pobMax);
				
				AddProblema ap = new AddProblema();
				ap.setAddProblemaRequest(apr);
				AddProblema2 ap2 = new AddProblema2();
				ap2.setAddProblema(ap);
				AddProblemaResponse1 apresponse1 = fassbss.addProblema(ap2);
				id = apresponse1.getAddProblemaResponse().getAddProblemaResponse().getId();
			
			
			} catch (AxisFault e) {
				e.printStackTrace();
				return false;
			} catch (RemoteException e) {
				e.printStackTrace();
				return false;
			}
			
		return true;
	}

	public String getId() {
		return id;
	}

	public void setAceptadas(List<String> aceptadas) {
		this.aceptadas = aceptadas;
	}

	public void setRechazadas(List<String> rechazadas) {
		this.rechazadas = rechazadas;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}

	public void setAfp(Afp afp) {
		this.afp = afp;
	}
	
	public void setAFP(es.si.ProgramadorGenetico.ProblemaAFP.AFP afp) {
		int estados = afp.getEstados();
		this.afp.setEstados(afp.getEstados());
		
		String probFinales = "";
		double[] prob = afp.getProbabilidadesFinal();
		for (int i = 0; i < estados; i ++)
			probFinales += (probFinales.equals("") ? prob[i] : ";" + prob[i]);
		this.afp.setProbFinales(probFinales);
		
		String[] trans = new String[estados*2];
		double[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			for (int j = 0; j < 2; j ++) {
				trans[i*2 +j] = "" + i + ":" + j + ":";
				for (int k = 0; k < estados+1; k++) {
					trans[i*2+j] += (k == 0 ? transiciones[i] : ";" + transiciones[i]);
				}
			}
		}
		this.afp.setTransiciones(trans);
	}

	public void setEstados(Integer estados) {
		this.estados = estados;
	}

	public void setPobMax(Integer pobMax) {
		this.pobMax = pobMax;
	}
	
	
	
	
	
}
