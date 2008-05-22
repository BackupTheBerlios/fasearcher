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

public class GetValidValuesWS {

	private List<String> problemas;
	
	private Integer configuracion;
	
	private List<String> mutadores;
	
	private List<String> cruzadores;
	
	private List<String> funcBondad;
	
	private List<Integer> pobMax;
	
	private List<Integer> muestras;
	
	private List<Integer> estados;
	
	private List<Integer> pasos;
	
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
