package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.StatsWS.FASearcherStats;
import es.si.ProgramadorGenetico.StatsWS.FASearcherStatsBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetAdvancedStatsRequest;
import es.si.fasearcherserver.GetAdvancedStatsResponse;

/**
 * Esta clase es utilizada para obtener estadisticas avanzadas
 * desde el servidor.<p>
 * 
 * Para usar el método correspondiente hay que crear una instacia,
 * luego dar valores a los distintos parámetos y por ultimo, al
 * llamar al método "ejecutar" se envía la información al servidor.
 * Una vez enviada la información al servidor este consulta la base
 * de dato y devuelve las estadísticas correspondientes.
 */
public class GetAdvancedStatsWS {

	/**
	 * Lista de los problemas sobre los que se desean estadísticas
	 */
	private List<String> problemas;
	
	/**
	 * Lista de configuraciones que se desan incluir al realizar estadísticas
	 */
	private List<Integer> configuraciones; 
	
	/**
	 * Lista de los mutadores que se desean incluir para las estadisticas
	 * (por el nombre con la version)
	 */
	private List<String> mutadores;
	
	/**
	 * Lista de los cruzadores que se desean incluir para las estadísticas
	 * (por el nombre con la version)
	 */
	private List<String> cruzadores;
	
	/**
	 * Lista de los calculadores de bondad (funciones de bondad) que se desean
	 * incluir para las estadísticas (por el nombre con la version)
	 */
	private List<String> calculadoresBondad;
	
	/**
	 * Lista de los numeros de estados que se desean incluir en las estadísticas
	 */
	private List<Integer> estados;
	
	/**
	 * Lista de los numeros de muestras qu ese desean incluir en las estadísticas
	 */
	private List<Integer> muestras;
	
	/**
	 * Lista de los valores de poblacion maxima que se desean incluir en las
	 * estadísticas
	 */
	private List<Integer> pobMax;
	
	/**
	 * Lista de los numeros de pasos que se desean incluir en las estadísticas
	 */
	private List<Integer> pasos;
	
	
	/**
	 * Número de problemas que cumplen las restricciones 
	 */
	private Integer numProblemas;
	
	/**
	 * Media de soluciones por problemas que cumplen las restricciones
	 */
	private Float mediaSoluciones;
	
	/**
	 * Lista de valores que representan un histograma de la distribuición
	 * del reconocimiento de los autómatas
	 */
	private List<Double> histReconocimiento;
	
	/**
	 * Lista de valores que representan un histograma de la distribución
	 * del parecido de los AFPs con AFs
	 */
	private List<Double> histParecido;
	
	public Integer getNumProblemas() {
		return numProblemas;
	}

	public Float getMediaSoluciones() {
		return mediaSoluciones;
	}

	public List<Double> getHistReconocimiento() {
		return histReconocimiento;
	}

	public List<Double> getHistParecido() {
		return histParecido;
	}

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
			
			GetAdvancedStatsRequest gasr = new GetAdvancedStatsRequest();
			
			if (problemas != null && problemas.size() > 0) {
				for (String id : problemas)
					gasr.getIdProblemas().add(id);
			}
			
			if (configuraciones != null && configuraciones.size() > 0) {
				for (Integer id : configuraciones)
					gasr.getIdConfig().add(id);
			}
			
			if (mutadores != null && mutadores.size() > 0) {
				for (String mut : mutadores)
					gasr.getMutadores().add(mut);
			}
			
			if (cruzadores != null && cruzadores.size() > 0) {
				for (String cruz : cruzadores)
					gasr.getCruzadores().add(cruz);
			}
			
			if (calculadoresBondad != null && calculadoresBondad.size() > 0) {
				for (String calc : calculadoresBondad)
					gasr.getFuncBondad().add(calc);
			}
			
			if (muestras != null && muestras.size() > 0) {
				for (Integer muestra : muestras)
					gasr.getMuestras().add(muestra);
			}
			
			if (estados != null && estados.size() > 0) {
				for (Integer estado : estados)
					gasr.getEstados().add(estado);
			}
			
			if (pasos != null && pasos.size() > 0) {
				for (Integer paso : pasos)
					gasr.getPasos().add(paso);
			}
			
			if (pobMax != null && pobMax.size() > 0) {
				for (Integer pob : pobMax)
					gasr.getPobmax().add(pob);
			}
			
			GetAdvancedStatsResponse gasresponse= fas.getAdvancedStats(gasr);

			numProblemas = gasresponse.getNumProblemas();
			
			histParecido = gasresponse.getHistParecido();
			
			histReconocimiento = gasresponse.getHistReconocimiento();
			
			mediaSoluciones = gasresponse.getMediaSoluciones();
			
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return true;
	}

	public void setProblemas(List<String> problemas) {
		this.problemas = problemas;
	}

	public void setConfiguraciones(List<Integer> configuraciones) {
		this.configuraciones = configuraciones;
	}

	public void setMutadores(List<String> mutadores) {
		this.mutadores = mutadores;
	}

	public void setCruzadores(List<String> cruzadores) {
		this.cruzadores = cruzadores;
	}

	public void setCalculadoresBondad(List<String> calculadoresBondad) {
		this.calculadoresBondad = calculadoresBondad;
	}

	public void setEstados(List<Integer> estados) {
		this.estados = estados;
	}

	public void setMuestras(List<Integer> muestras) {
		this.muestras = muestras;
	}

	public void setPobMax(List<Integer> pobMax) {
		this.pobMax = pobMax;
	}

	public void setPasos(List<Integer> pasos) {
		this.pasos = pasos;
	}



	
}
