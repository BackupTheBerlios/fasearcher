package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherService;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherServiceBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetSolucionRequest;
import es.si.fasearcherserver.GetSolucionResponse;

public class GetSolucionWS {

	private String tipoAutomata;
	
	private AFP afp;
	
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}

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
			
			GetSolucionRequest gpr = new GetSolucionRequest();
			
			gpr.setId(id);
			
			GetSolucionResponse gpresponse = fas.getSolucion(gpr);

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

			tipoAutomata = gpresponse.getTipoAutomata();
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return true;
	}

	public class Solucion {
		private String id;
		
		private Integer estados;
		
		private Double reconocimiento;
		
		private Double parecidoAF;
		
		private String tipoAutomata;
		
		private Integer pasos;
		
		private String mutador;
		
		private String calculadorBondad;
		
		private String cruzador;
		
		private Integer pobMax;
		
		private String muestras;
		
		private Integer id_configuracion;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Integer getEstados() {
			return estados;
		}

		public void setEstados(Integer estados) {
			this.estados = estados;
		}

		public Double getReconocimiento() {
			return reconocimiento;
		}

		public void setReconocimiento(Double reconocimiento) {
			this.reconocimiento = reconocimiento;
		}

		public Double getParecidoAF() {
			return parecidoAF;
		}

		public void setParecidoAF(Double parecidoAF) {
			this.parecidoAF = parecidoAF;
		}

		public String getTipoAutomata() {
			return tipoAutomata;
		}

		public void setTipoAutomata(String tipoAutomata) {
			this.tipoAutomata = tipoAutomata;
		}

		public Integer getPasos() {
			return pasos;
		}

		public void setPasos(Integer pasos) {
			this.pasos = pasos;
		}

		public String getMutador() {
			return mutador;
		}

		public void setMutador(String mutador) {
			this.mutador = mutador;
		}

		public String getCalculadorBondad() {
			return calculadorBondad;
		}

		public void setCalculadorBondad(String calculadorBondad) {
			this.calculadorBondad = calculadorBondad;
		}

		public String getCruzador() {
			return cruzador;
		}

		public void setCruzador(String cruzador) {
			this.cruzador = cruzador;
		}

		public Integer getPobMax() {
			return pobMax;
		}

		public void setPobMax(Integer pobMax) {
			this.pobMax = pobMax;
		}

		public String getMuestras() {
			return muestras;
		}

		public void setMuestras(String muestras) {
			this.muestras = muestras;
		}

		public Integer getId_configuracion() {
			return id_configuracion;
		}

		public void setId_configuracion(Integer id_configuracion) {
			this.id_configuracion = id_configuracion;
		}
		
		
	}

	public class Configuracion {
		private int muestras;
		
		private int pobMax;
		
		private int estados;
		
		private int calculadorBondad;
		
		private int cruzador;
		
		private int mutador;
		
		private int resolver;
		
		private int id;

		public int getMuestras() {
			return muestras;
		}

		public void setMuestras(int muestras) {
			this.muestras = muestras;
		}

		public int getPobMax() {
			return pobMax;
		}

		public void setPobMax(int pobMax) {
			this.pobMax = pobMax;
		}

		public int getEstados() {
			return estados;
		}

		public void setEstados(int estados) {
			this.estados = estados;
		}

		public int getCalculadorBondad() {
			return calculadorBondad;
		}

		public void setCalculadorBondad(int calculadorBondad) {
			this.calculadorBondad = calculadorBondad;
		}

		public int getCruzador() {
			return cruzador;
		}

		public void setCruzador(int cruzador) {
			this.cruzador = cruzador;
		}

		public int getMutador() {
			return mutador;
		}

		public void setMutador(int mutador) {
			this.mutador = mutador;
		}

		public int getResolver() {
			return resolver;
		}

		public void setResolver(int resolver) {
			this.resolver = resolver;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}

	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public AFP getAfp() {
		return afp;
	}

	
	
}
