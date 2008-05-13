package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.ServiceWS.FASearcherService;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherServiceBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetProblemasRequest;
import es.si.fasearcherserver.GetProblemasResponse;

public class GetProblemasWS {

	private List<Problema> problemas;
	
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
			
			GetProblemasRequest gpr = new GetProblemasRequest();
			
			GetProblemasResponse gpresponse = fas.getProblemas(gpr);

			problemas = new ArrayList<Problema>();
			for (int i = 0; i < gpresponse.getProblemas().size(); i++) {
				Problema problema = new Problema();
				problema.setId(gpresponse.getProblemas().get(i).getId());
				problema.setDescripcion(gpresponse.getProblemas().get(i).getDescripcion());
				problema.setSoluciones(gpresponse.getProblemas().get(i).getSoluciones());
				problemas.add(problema);
			}
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return true;
	}

	public class Problema {
		
		private String id;
		
		private String descripcion;

		private Integer soluciones;
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Integer getSoluciones() {
			return soluciones;
		}

		public void setSoluciones(Integer soluciones) {
			this.soluciones = soluciones;
		}
		
		
	}

	public List<Problema> getProblemas() {
		return problemas;
	}
	
}
