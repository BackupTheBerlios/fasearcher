package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetProblemaRequest;
import es.si.fasearcherserver.GetProblemaResponse;
import es.si.fasearcherserver.ejb.FASearcher;
import es.si.fasearcherserver.ejb.FASearcherBeanService;

public class GetProblemaWS {

	private Integer tamano;
	
	private String tipoAutomata;
	
	private Integer estados;
	
	private Integer pobMax;
	
	private Integer muestras;
	
	private String id;
	
	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	public boolean ejecutar() {
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherBeanService");
			URL server = new URL(Config.getInstance().getProperty("FASearcherServer"));

			FASearcherBeanService fasbs = new FASearcherBeanService(server, service);
			FASearcher fas = fasbs.getFASearcherBeanPort();
			
			GetProblemaRequest gpr = new GetProblemaRequest();
			if (tamano != null)
				gpr.setTamano(tamano);
			if (tipoAutomata != null)
				gpr.setTipoAutomata(tipoAutomata);
			
			GetProblemaResponse gpresponse = fas.getProblema(gpr);

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
	
}
