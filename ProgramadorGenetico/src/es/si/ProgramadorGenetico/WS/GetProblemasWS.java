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

/**
 * Esta clase es utilizada para obtener una lista de los problemas
 * que estan en la base de datos.<p>
 * 
 * Para usar el método correspondiente hay que crear una instacia,
 * luego dar valores a los distintos parámetos y por ultimo, al
 * llamar al método "ejecutar" se envía la información al servidor.
 * El servidor devuelve una lista con todos los problemas que estan
 * en la base de datos.
 */
public class GetProblemasWS {

	/**
	 * Lista de los problemas de la bae de datos
	 */
	private List<Problema> problemas;
	
	/**
	 * Ejecutar la llamada al método del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecución del método fue satisfactoria
	 */
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

	/**
	 * Clase interna que incluye la infromación que describe un
	 * problema en la base de datos.
	 */
	public class Problema {
		
		/**
		 * Id del problema
		 */
		private String id;
		
		/**
		 * Descripción del problema
		 */
		private String descripcion;

		/**
		 * Número de soluciones que tiene el problema en la base de datos
		 */
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
