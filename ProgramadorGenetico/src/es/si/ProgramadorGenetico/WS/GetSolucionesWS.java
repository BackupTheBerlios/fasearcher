package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.ServiceWS.FASearcherService;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherServiceBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetSolucionesRequest;
import es.si.fasearcherserver.GetSolucionesResponse;

public class GetSolucionesWS {

	private List<Solucion> soluciones;
	
	private List<Configuracion> configuraciones;
	
	public boolean ejecutar() {
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherServiceBeanService");
			URL server;
			if (!Config.getInstance().getProperty("FASearcherServiceServer").equals("classpath"))
				server = new URL(Config.getInstance().getProperty("FASearcherServiceServer"));
			else
				server = ClassLoader.getSystemResource("FASearcherServiceBean.wsdl");

			FASearcherServiceBeanService fasbs = new FASearcherServiceBeanService(server, service);
			FASearcherService fas = fasbs.getFASearcherServiceBeanPort();
			
			GetSolucionesRequest gpr = new GetSolucionesRequest();
			
			GetSolucionesResponse gpresponse = fas.getSoluciones(gpr);


			
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return true;
	}

	public class Solucion {
		
	}

	public class Configuracion {
		
	}

	public List<Solucion> getSoluciones() {
		return soluciones;
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	
	
}
