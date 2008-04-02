package es.si.ProgramadorGenetico.WS.Pruebas;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.si.ProgramadorGenetico.WS.FASearcherBeanServiceStub;
import es.si.ProgramadorGenetico.WS.FASearcherBeanServiceStub.*;

public class PruebaWS1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			//FASearcherBeanServiceCallbackHandler fasbsch = new FASearcherBeanServiceCallbackHandler();
			FASearcherBeanServiceStub fasbss = new FASearcherBeanServiceStub("http://169.254.140.126:18080/fasearcher/FASearcherBean");
			GetProblema2 gp2 = new GetProblema2();
			GetProblema gp = new GetProblema();
			GetProblemaRequest gpr = new GetProblemaRequest();
			gpr.setTamano(0);
			gpr.setTipoAutomata("AFP");
			gp.setGetProblemaRequest(gpr);
			gp2.setGetProblema(gp);
			GetProblemaResponse3 gpresponse3 = fasbss.getProblema(gp2);
			int estados = gpresponse3.getGetProblemaResponse().getGetProblemResponse().getEstados();

			
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
