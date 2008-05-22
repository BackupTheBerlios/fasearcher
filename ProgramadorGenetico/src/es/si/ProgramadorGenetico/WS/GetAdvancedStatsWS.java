package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.StatsWS.FASearcherStats;
import es.si.ProgramadorGenetico.StatsWS.FASearcherStatsBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetAdvancedStatsRequest;
import es.si.fasearcherserver.GetAdvancedStatsResponse;
import es.si.fasearcherserver.GetValidValuesRequest;
import es.si.fasearcherserver.GetValidValuesResponse;

public class GetAdvancedStatsWS {

	private List<String> problemas;
	
	private List<Integer> configuraciones; 
	
	private List<String> mutadores;
	
	private List<String> cruzadores;
	
	private List<String> calculadoresBondad;
	
	private List<Integer> estados;
	
	private List<Integer> muestras;
	
	private List<Integer> pobMax;
	
	private List<Integer> pasos;
	
	
	private Integer numProblemas;
	
	private Float mediaSoluciones;
	
	private List<Double> histReconocimiento;
	
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
