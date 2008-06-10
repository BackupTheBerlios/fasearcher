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
 * Para usar el m�todo correspondiente hay que crear una instacia,
 * luego dar valores a los distintos par�metos y por ultimo, al
 * llamar al m�todo "ejecutar" se env�a la informaci�n al servidor.
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
	 * Ejecutar la llamada al m�todo del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecuci�n del m�todo fue satisfactoria
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
