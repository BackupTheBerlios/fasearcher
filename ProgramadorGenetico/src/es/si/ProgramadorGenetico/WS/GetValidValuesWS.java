package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.StatsWS.FASearcherStats;
import es.si.ProgramadorGenetico.StatsWS.FASearcherStatsBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetValidValuesRequest;
import es.si.fasearcherserver.GetValidValuesResponse;

/**
 * Esta clase es utilizada para obtener los valores válidos para
 * los distintos parámetros de configuración.<p>
 * 
 * Para usar el método correspondiente hay que crear una instacia,
 * luego dar valores a los distintos parámetos y por ultimo, al
 * llamar al método "ejecutar" se envía la información al servidor.
 * El servidor devuelve listas de cadenas con los posibles valores
 * que pueden tomar los distntos parámetros de una solución.
 */
public class GetValidValuesWS {

	/**
	 * Lista de identificadores de los problemas 
	 */
	private List<String> problemas;
	
	/**
	 * Configuración específica sobre la que se quieren
	 * conocer los parámetros válidos (opcional)
	 */
	private Integer configuracion;
	
	/**
	 * Lista de mutadores válidos como parámetros
	 */
	private List<String> mutadores;
	
	/**
	 * Lista de cruzadores válidos como parámetros
	 */
	private List<String> cruzadores;
	
	/**
	 * Lista de funciones de bondad válidas como parámetros
	 */
	private List<String> funcBondad;
	
	/**
	 * Lista de valores de población máxima válidos como parámetros
	 */
	private List<Integer> pobMax;
	
	/**
	 * Lista de valores de muestras válidos como parámetros
	 */
	private List<Integer> muestras;
	
	/**
	 * Lista de números de estados válidos como parámetros
	 */
	private List<Integer> estados;
	
	/**
	 * Lista de números de pasos como parámetros
	 */
	private List<Integer> pasos;
	
	/**
	 * Ejecutar la llamada al método del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecución del método fue satisfactoria
	 */
	public boolean ejecutar() {
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherStatsBeanService");
			URL server;
			if (!Config.getInstance().getProperty("FASearcherStatsServer").equals("classpath"))
				server = new URL(Config.getInstance().getProperty("FASearcherStatsServer"));
			else
				server = ClassLoader.getSystemResource("FASearcherStatsBean.wsdl");


			FASearcherStatsBeanService fasbs = new FASearcherStatsBeanService(server, service);
			FASearcherStats fas = fasbs.getFASearcherStatsBeanPort();
			
			GetValidValuesRequest gvvr = new GetValidValuesRequest();
			gvvr.setIdConfig(configuracion);
			for (String id : problemas)
				gvvr.getIdProblemas().add(id);
			
			GetValidValuesResponse gvvresponse= fas.getValidValues(gvvr);

			mutadores = gvvresponse.getMutadores();
			
			cruzadores = gvvresponse.getCruzadores();
			
			funcBondad = gvvresponse.getFuncBondad();
			
			pobMax = gvvresponse.getPobMax();
			
			muestras = gvvresponse.getMuestras();
			
			estados = gvvresponse.getEstados();
			
			pasos = gvvresponse.getPasos();
			
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return true;
	}

	public List<String> getMutadores() {
		return mutadores;
	}

	public List<String> getCruzadores() {
		return cruzadores;
	}

	public List<String> getFuncBondad() {
		return funcBondad;
	}

	public List<Integer> getPobMax() {
		return pobMax;
	}

	public List<Integer> getMuestras() {
		return muestras;
	}

	public List<Integer> getEstados() {
		return estados;
	}

	public void setProblemas(List<String> problemas) {
		this.problemas = problemas;
	}

	public void setConfiguracion(Integer configuracion) {
		this.configuracion = configuracion;
	}

	public List<Integer> getPasos() {
		return pasos;
	}


	
}
