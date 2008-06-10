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
 * Esta clase es utilizada para obtener los valores v�lidos para
 * los distintos par�metros de configuraci�n.<p>
 * 
 * Para usar el m�todo correspondiente hay que crear una instacia,
 * luego dar valores a los distintos par�metos y por ultimo, al
 * llamar al m�todo "ejecutar" se env�a la informaci�n al servidor.
 * El servidor devuelve listas de cadenas con los posibles valores
 * que pueden tomar los distntos par�metros de una soluci�n.
 */
public class GetValidValuesWS {

	/**
	 * Lista de identificadores de los problemas 
	 */
	private List<String> problemas;
	
	/**
	 * Configuraci�n espec�fica sobre la que se quieren
	 * conocer los par�metros v�lidos (opcional)
	 */
	private Integer configuracion;
	
	/**
	 * Lista de mutadores v�lidos como par�metros
	 */
	private List<String> mutadores;
	
	/**
	 * Lista de cruzadores v�lidos como par�metros
	 */
	private List<String> cruzadores;
	
	/**
	 * Lista de funciones de bondad v�lidas como par�metros
	 */
	private List<String> funcBondad;
	
	/**
	 * Lista de valores de poblaci�n m�xima v�lidos como par�metros
	 */
	private List<Integer> pobMax;
	
	/**
	 * Lista de valores de muestras v�lidos como par�metros
	 */
	private List<Integer> muestras;
	
	/**
	 * Lista de n�meros de estados v�lidos como par�metros
	 */
	private List<Integer> estados;
	
	/**
	 * Lista de n�meros de pasos como par�metros
	 */
	private List<Integer> pasos;
	
	/**
	 * Ejecutar la llamada al m�todo del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecuci�n del m�todo fue satisfactoria
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
