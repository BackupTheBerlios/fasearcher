package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherService;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherServiceBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetProblemaRequest;
import es.si.fasearcherserver.GetProblemaResponse;

public class GetProblemaWS {

	private String id;
	
	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private String descripcion;
	
	private Integer estados;
	
	private Integer pobMax;
	
	private String tipoAutomata;
	
	private List<Configuracion> configuraciones;
	
	private AFP afp;
	
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
			
			GetProblemaRequest gpr = new GetProblemaRequest();
			
			if (id != null) {
				gpr.setId(id);
				
				GetProblemaResponse gpresponse = fas.getProblema(gpr);
				
				if (gpresponse.getAceptadas() != null)
					aceptadas = gpresponse.getAceptadas();
				else
					aceptadas = new ArrayList<String>();
				
				if (gpresponse.getRechazadas() != null)
					rechazadas = gpresponse.getRechazadas();
				else
					rechazadas = new ArrayList<String>();
				
				descripcion = gpresponse.getDescripcion();
				
				estados = gpresponse.getEstados();
				
				pobMax = gpresponse.getPobMax();
				
				tipoAutomata = gpresponse.getTipoAutomata();
				
				configuraciones = new ArrayList<Configuracion>();
				for (int i = 0; i < gpresponse.getConfiguraciones().size(); i++) {
					Configuracion config = new Configuracion();
					config.setCalculadorBondad(gpresponse.getConfiguraciones().get(i).getCalculadorBondad());
					config.setCruzador(gpresponse.getConfiguraciones().get(i).getCruzador());
					config.setEstados(gpresponse.getConfiguraciones().get(i).getEstados());
					config.setMuestras(gpresponse.getConfiguraciones().get(i).getMuestras());
					config.setMutador(gpresponse.getConfiguraciones().get(i).getMutador());
					config.setPobMax(gpresponse.getConfiguraciones().get(i).getPobMax());
					config.setResolver(gpresponse.getConfiguraciones().get(i).getResolver());
					configuraciones.add(config);
				}
				
				afp = new AFP(gpresponse.getAfp().getEstados().intValue());
				
				float[][][] transiciones = afp.getTransiciones();

				List<String> lista = gpresponse.getAfp().getTransiciones();
				Iterator<String> it = lista.iterator();
				while (it.hasNext()) {
					String temp = it.next();
					String[] partes = temp.split(":");
					int entrada = Integer.parseInt(partes[1]);
					int estado = Integer.parseInt(partes[0]);

					String[] valores = partes[2].split(";");
					for (int i = 0; i < valores.length; i++) {
						System.out.println("Entrada: " + entrada + " Estado: " + estado + " Valor: " + valores[i]);
						transiciones[estado][entrada][i] = Float.parseFloat(valores[i]);
					}
				}
				afp.setTransiciones(transiciones);

				float[] finales = afp.getProbabilidadesFinal();
				String[] probs = gpresponse.getAfp().getProbFinales().split(";");
				for (int i = 0; i < probs.length; i++) {
					finales[i] = Float.parseFloat(probs[i]);
				}
				afp.setProbabilidadFinal(finales);
			} else {
				id = "-1";
			}
			
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			id = "-1";
		} catch (Exception e) {
			e.printStackTrace();
			id = "-1";
		}
	
		
		return true;
	}

	public List<String> getAceptadas() {
		return aceptadas;
	}

	public List<String> getRechazadas() {
		return rechazadas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getEstados() {
		return estados;
	}

	public Integer getPobMax() {
		return pobMax;
	}

	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public AFP getAfp() {
		return afp;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	
}
