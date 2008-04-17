package es.si.ProgramadorGenetico.WS.Pruebas;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import es.si.fasearcherserver.GetProblemaRequest;
import es.si.fasearcherserver.GetProblemaResponse;
import es.si.fasearcherserver.ejb.FASearcher;
import es.si.fasearcherserver.ejb.FASearcherBeanService;


public class PruebaWS1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherBeanService");
			FASearcherBeanService fasbs = new FASearcherBeanService(new URL("http://169.254.140.126:18080/fasearcher/FASearcherBean"), service);
			FASearcher fas = fasbs.getFASearcherBeanPort();
			
			GetProblemaRequest gpr = new GetProblemaRequest();
			gpr.setTamano(0);
			gpr.setTipoAutomata("AFP");
			
			GetProblemaResponse gpresponse = fas.getProblema(gpr);
			
			System.out.println(gpresponse.getAceptadas());
			System.out.println(gpresponse.getRechazadas());

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
