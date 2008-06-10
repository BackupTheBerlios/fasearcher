package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.ServiceWS.FASearcherService;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherServiceBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.RemoveProblemaRequest;

/**
 * Esta clase es utilizada para eliminar un problema de la base de datos.<p>
 * 
 * Para usar el método correspondiente hay que crear una instacia,
 * luego dar valores a los distintos parámetos y por ultimo, al
 * llamar al método "ejecutar" se envía la información al servidor.
 * El servidor elimina el problema correspondiente de la base de datos.
 */
public class RemoveProblemaWS {

	/**
	 * Id del problema a eliminar
	 */
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}

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
			
			RemoveProblemaRequest gpr = new RemoveProblemaRequest();
			
			gpr.setId(id);
			
			fas.removeProblema(gpr);

		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}


}
