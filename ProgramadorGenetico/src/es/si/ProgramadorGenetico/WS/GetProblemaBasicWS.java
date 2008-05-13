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

public class GetProblemaBasicWS {

	private Integer tamano;
	
	private String tipoAutomata;
	
	private Integer estados;
	
	private Integer pobMax;
	
	private Integer muestras;
	
	private Integer calculadorBondad;
	
	private Integer cruzador;
	
	private Integer mutador;
	
	private Integer resolver;
	
	private String id;
	
	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
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
	
}
