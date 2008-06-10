package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.BasicWS.FASearcherBasic;
import es.si.ProgramadorGenetico.BasicWS.FASearcherBasicBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetProblemaBasicRequest;
import es.si.fasearcherserver.GetProblemaBasicResponse;

/**
 * Esta clase es utilizada para obtener un problema para resolver
 * desde el servidor.<p>
 * 
 * Para usar el método correspondiente hay que crear una instacia,
 * luego dar valores a los distintos parámetos y por ultimo, al
 * llamar al método "ejecutar" se envía la información al servidor.
 * El servidor devuelve la información necesaria para resolver un
 * problema con las palabaras aceptadas, rechazadas y la configuración.
 */
public class GetProblemaBasicWS {

	/**
	 * Tamaño, en forma de numero de estados, del automata que se desa obtener
	 */
	private Integer tamano;
	
	/**
	 * Tipo del automata que se pretende obtener
	 */
	private String tipoAutomata;
	
	/**
	 * Numero de estados del automata que se desea generar
	 */
	private Integer estados;
	
	/**
	 * Poblacion maxima que se debe utilizar para generar el problema
	 */
	private Integer pobMax;
	
	/**
	 * Número de muestras que se deben utilizar para generar el problema
	 */
	private Integer muestras;
	
	/**
	 * Identificador para calculador de bondad que se debe utilizar
	 */
	private Integer calculadorBondad;
	
	/**
	 * Identificador para el curzador que se debe utilizar
	 */
	private Integer cruzador;
	
	/**
	 * Identificador para el mutador que se debe utilizar
	 */
	private Integer mutador;
	
	/**
	 * Identificador para el metodo resolutor que se debe utilizar
	 */
	private Integer resolver;
	
	/**
	 * Id del problema que se debe resolver
	 */
	private String id;
	
	/**
	 * Id de la configuracion que se esta utilizando para resolver el problema
	 */
	private Integer id_config;
	
	/**
	 * Lista de cadenas aceptadas por el problema
	 */
	private List<String> aceptadas;
	
	/**
	 * Lista de cadenas rechazadas por el problema
	 */
	private List<String> rechazadas;
	
	/**
	 * Ejecutar la llamada al método del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecución del método fue satisfactoria
	 */
	public boolean ejecutar() {
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherBasicBeanService");
			URL server;
			if (!Config.getInstance().getProperty("FASearcherBasicServer").equals("classpath"))
				server = new URL(Config.getInstance().getProperty("FASearcherBasicServer"));
			else
				server = ClassLoader.getSystemResource("FASearcherBasicBean.wsdl");


			FASearcherBasicBeanService fasbs = new FASearcherBasicBeanService(server, service);
			FASearcherBasic fas = fasbs.getFASearcherBasicBeanPort();
			
			GetProblemaBasicRequest gpr = new GetProblemaBasicRequest();
			if (tamano != null)
				gpr.setTamano(tamano);
			if (tipoAutomata != null)
				gpr.setTipoAutomata(tipoAutomata);
			
			GetProblemaBasicResponse gpresponse = fas.getProblemaBasic(gpr);

			estados = gpresponse.getEstados();
			id = gpresponse.getId();
			
			aceptadas = new ArrayList<String>();
			String[] arrayAceptadas = gpresponse.getAceptadas().split("\\,");
			for (int i = 0; i < arrayAceptadas.length; i++)
				aceptadas.add(arrayAceptadas[i]);
			
			rechazadas = new ArrayList<String>();
			String[] arrayRechazadas = gpresponse.getRechazadas().split("\\,");
			for (int i = 0; i < arrayRechazadas.length; i++)
				rechazadas.add(arrayRechazadas[i]);
			
			muestras = gpresponse.getMuestras();
			pobMax = gpresponse.getPobMax();
			tipoAutomata = gpresponse.getTipoAutomata();
			calculadorBondad = gpresponse.getCalculadorBondad();
			cruzador = gpresponse.getCruzador();
			mutador = gpresponse.getMutador();
			resolver = gpresponse.getResolver();
			id_config = gpresponse.getIdConfiguracion();
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public Integer getMuestras() {
		return muestras;
	}

	public Integer getCalculadorBondad() {
		return calculadorBondad;
	}

	public Integer getCruzador() {
		return cruzador;
	}

	public Integer getMutador() {
		return mutador;
	}

	public Integer getResolver() {
		return resolver;
	}

	public Integer getId_config() {
		return id_config;
	}
	
}
