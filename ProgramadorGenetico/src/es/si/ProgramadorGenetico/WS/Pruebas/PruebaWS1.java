package es.si.ProgramadorGenetico.WS.Pruebas;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.BasicWS.FASearcherBasic;
import es.si.ProgramadorGenetico.BasicWS.FASearcherBasicBeanService;
import es.si.fasearcherserver.GetProblemaBasicRequest;
import es.si.fasearcherserver.GetProblemaBasicResponse;


public class PruebaWS1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherBeanService");
			FASearcherBasicBeanService fasbs = new FASearcherBasicBeanService(new URL("http://169.254.140.126:18080/fasearcher/FASearcherBean"), service);
			FASearcherBasic fas = fasbs.getFASearcherBasicBeanPort();
			
			GetProblemaBasicRequest gpr = new GetProblemaBasicRequest();
			gpr.setTamano(0);
			gpr.setTipoAutomata("AFP");
			
			GetProblemaBasicResponse gpresponse = fas.getProblemaBasic(gpr);
			
			System.out.println(gpresponse.getAceptadas());
			System.out.println(gpresponse.getRechazadas());

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
