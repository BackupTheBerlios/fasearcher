package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.ServiceWS.FASearcherService;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherServiceBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetSolucionesRequest;
import es.si.fasearcherserver.GetSolucionesResponse;

/**
 * Esta clase es utilizada para obtener soluciones de un problema
 * desde la base de datos.<p>
 * 
 * Para usar el método correspondiente hay que crear una instacia,
 * luego dar valores a los distintos parámetos y por ultimo, al
 * llamar al método "ejecutar" se envía la información al servidor.
 * El servidor devuelve una lista de las soluciones del problema.
 */
public class GetSolucionesWS {

	/**
	 * Lista de las soluciones del problema
	 */
	private List<Solucion> soluciones;
	
	/**
	 * Lista de las configuraciones del problema
	 */
	private List<Configuracion> configuraciones;
	
	/**
	 * Id del problema del que se quiere obtener información
	 */
	private String id;
	
	/**
	 * Id de la configuracion de la que se quieren obtener las soluciones
	 */
	private Integer id_configuracion;
	
	public void setId(String id) {
		this.id = id;
	}

	public void setId_configuracion(Integer id_configuracion) {
		this.id_configuracion = id_configuracion;
	}

	/**
	 * Ejecutar la llamada al método del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecución del método fue satisfactoria
	 */
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
			
			GetSolucionesRequest gpr = new GetSolucionesRequest();
			
			gpr.setId(id);
			gpr.setIdConfig(id_configuracion);
			
			GetSolucionesResponse gpresponse = fas.getSoluciones(gpr);

			soluciones = new ArrayList<Solucion>();
			for (int i = 0; i < gpresponse.getSoluciones().size(); i++) {
				Solucion sol = new Solucion();
				sol.setCalculadorBondad(gpresponse.getSoluciones().get(i).getFuncBondad());
				sol.setCruzador(gpresponse.getSoluciones().get(i).getCruzador());
				sol.setEstados(gpresponse.getSoluciones().get(i).getEstados());
				sol.setId(gpresponse.getSoluciones().get(i).getId());
				sol.setId_configuracion(gpresponse.getSoluciones().get(i).getIdConfiguracion());
				sol.setMuestras(gpresponse.getSoluciones().get(i).getMuestras());
				sol.setMutador(gpresponse.getSoluciones().get(i).getMutador());
				sol.setParecidoAF(gpresponse.getSoluciones().get(i).getParecidoAF());
				sol.setPasos(gpresponse.getSoluciones().get(i).getPasos());
				sol.setPobMax(gpresponse.getSoluciones().get(i).getPobMax());
				sol.setReconocimiento(gpresponse.getSoluciones().get(i).getReconocimiento());
				sol.setTipoAutomata(gpresponse.getSoluciones().get(i).getTipoAutomata());
				soluciones.add(sol);
			}
			
			if (gpresponse.getConfiguraciones() != null && gpresponse.getConfiguraciones().size() > 0) {
				configuraciones = new ArrayList<Configuracion>();
				for (int i = 0; i < gpresponse.getConfiguraciones().size(); i++) {
					Configuracion config = new Configuracion();
					config.setCalculadorBondad(gpresponse.getConfiguraciones().get(i).getCalculadorBondad());
					config.setCruzador(gpresponse.getConfiguraciones().get(i).getCruzador());
					config.setEstados(gpresponse.getConfiguraciones().get(i).getEstados());
					config.setId(gpresponse.getConfiguraciones().get(i).getId());
					config.setMuestras(gpresponse.getConfiguraciones().get(i).getMuestras());
					config.setMutador(gpresponse.getConfiguraciones().get(i).getMutador());
					config.setPobMax(gpresponse.getConfiguraciones().get(i).getPobMax());
					config.setResolver(gpresponse.getConfiguraciones().get(i).getResolver());
					configuraciones.add(config);
				}
			}
			
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

	public List<Solucion> getSoluciones() {
		return soluciones;
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	
	
}
