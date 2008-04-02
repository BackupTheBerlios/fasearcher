package es.si.ProgramadorGenetico.WS;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;

import es.si.ProgramadorGenetico.WS.FASearcherBeanServiceStub.GetProblema;
import es.si.ProgramadorGenetico.WS.FASearcherBeanServiceStub.GetProblema2;
import es.si.ProgramadorGenetico.WS.FASearcherBeanServiceStub.GetProblemaRequest;
import es.si.ProgramadorGenetico.WS.FASearcherBeanServiceStub.GetProblemaResponse3;
import es.si.ProgramadorGenetico.util.Config;

public class GetProblemaWS {

	private Integer tamano;
	
	private String tipoAutomata;
	
	private Integer estados;
	
	private Integer pobMax;
	
	private String id;
	
	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	public boolean ejecutar() {
		try {
			FASearcherBeanServiceStub fasbss;
			String server = Config.getInstance().getProperty("FASearcherServer");
			fasbss = new FASearcherBeanServiceStub(server);
			GetProblema2 gp2 = new GetProblema2();
			GetProblema gp = new GetProblema();
			GetProblemaRequest gpr = new GetProblemaRequest();
			if (tamano != null)
				gpr.setTamano(tamano);
			if (tipoAutomata != null)
				gpr.setTipoAutomata(tipoAutomata);
			gp.setGetProblemaRequest(gpr);
			gp2.setGetProblema(gp);
			
			GetProblemaResponse3 gpresponse3 = fasbss.getProblema(gp2);
			
			estados = gpresponse3.getGetProblemaResponse().getGetProblemResponse().getEstados();
			id = gpresponse3.getGetProblemaResponse().getGetProblemResponse().getId();
			
			aceptadas = new ArrayList<String>();
			String[] arrayAceptadas = gpresponse3.getGetProblemaResponse().getGetProblemResponse().getAceptadas().split("\\;");
			for (int i = 0; i < arrayAceptadas.length; i++)
				aceptadas.add(arrayAceptadas[i]);
			
			rechazadas = new ArrayList<String>();
			String[] arrayRechazadas = gpresponse3.getGetProblemaResponse().getGetProblemResponse().getRechazadas().split("\\;");
			for (int i = 0; i < arrayRechazadas.length; i++)
				rechazadas.add(arrayRechazadas[i]);
			
			pobMax = gpresponse3.getGetProblemaResponse().getGetProblemResponse().getPobMax();
			tipoAutomata = gpresponse3.getGetProblemaResponse().getGetProblemResponse().getTipoAutomata();
			
		} catch (AxisFault e) {
			e.printStackTrace();
			return false;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	
		
		return true;
	}

	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}

	public Integer getEstados() {
		return estados;
	}

	public Integer getPobMax() {
		return pobMax;
	}

	public String getId() {
		return id;
	}

	public List<String> getAceptadas() {
		return aceptadas;
	}

	public List<String> getRechazadas() {
		return rechazadas;
	}

	public void setTamano(Integer tamano) {
		this.tamano = tamano;
	}
	
}
