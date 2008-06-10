package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.BasicWS.Afp;
import es.si.ProgramadorGenetico.BasicWS.FASearcherBasic;
import es.si.ProgramadorGenetico.BasicWS.FASearcherBasicBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.SetSolucionRequest;
import es.si.fasearcherserver.SetSolucionResponse;

/**
 * Esta clase es utilizada para añadir una nueva solución a un problema.<p>
 * 
 * Para usar el método correspondiente hay que crear una instacia,
 * luego dar valores a los distintos parámetos y por ultimo, al
 * llamar al método "ejecutar" se envía la información al servidor.
 * El servidor almacena la información de la solución en al base de datos.
 */
public class SetSolucionWS {

	/**
	 * Id del problema
	 */
	private String id;
	
	/**
	 * Valor de reconocimento de la solución
	 */
	private String reconocimiento;
	
	/**
	 * Valor de parecido con un AF de la solución
	 */
	private String parecidoAF;
	
	/**
	 * AFP resultante de la resolución del problmea
	 */
	private Afp afp;
	
	/**
	 * Numero de pasos utilizados para llegar al AFP
	 */
	private Integer pasos;
	
	/**
	 * Mutador utilizado, incluyendo la version
	 */
	private String mutador;
	
	/**
	 * Método resolutor utilizado, incluyendo la version
	 */
	private String metodoRes;
	
	/**
	 * Funcion de bondad utilizada, incluyendo la version
	 */
	private String funcbondad;
	
	/**
	 * Cruzador utilizado, incluyendo la versión
	 */
	private String cruzador;
	
	/**
	 * Población máxima utilizada
	 */
	private Integer pobmax;
	
	/**
	 * Número de muestras  utilizadas
	 */
	private Integer muestras;
	
	/**
	 * Particiones utilizadas para generar el automata original
	 */
	private Integer particiones;
	
	/**
	 * Identificador de la configuración pasada como parámetro 
	 */
	private Integer id_config;
	
	public void setId_config(Integer id_config) {
		this.id_config = id_config;
	}

	public void setParticiones(Integer particiones) {
		this.particiones = particiones;
	}

	/**
	 * Ejecutar la llamada al método del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecución del método fue satisfactoria
	 */
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
			
			SetSolucionRequest ssr = new SetSolucionRequest();

			ssr.setId(id);
			ssr.setReconocimiento(reconocimiento);
			ssr.setParecidoAF(parecidoAF);
			ssr.setAfp(afp);
			ssr.setPasos(pasos);
			ssr.setMutador(mutador);
			ssr.setFuncbondad(funcbondad);
			ssr.setCruzador(cruzador);
			ssr.setMetodoRes(metodoRes);
			ssr.setPobmax(pobmax);
			ssr.setMuestras(muestras);
			ssr.setParticiones(particiones);
			ssr.setIdConfiguracion(id_config);
			
			SetSolucionResponse ssresponse = fas.setSolucion(ssr);

			if (ssresponse.getResult().equals("OK"))
				return true;
			else
				return false;
			
		}  catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setReconocimiento(String reconocimiento) {
		this.reconocimiento = reconocimiento;
	}

	public void setParecidoAF(String parecidoAF) {
		this.parecidoAF = parecidoAF;
	}
	
	public void setAfp(Afp afp) {
		this.afp = afp;
	}
	
	public void setAFP(es.si.ProgramadorGenetico.ProblemaAFP.AFP afp) {
		int estados = afp.getEstados();
		this.afp = new Afp();
		this.afp.setEstados(afp.getEstados());
		
		String probFinales = "";
		float[] prob = afp.getProbabilidadesFinal();
		for (int i = 0; i < estados; i ++)
			probFinales += (probFinales.equals("") ? prob[i] : ";" + prob[i]);
		this.afp.setProbFinales(probFinales);
		
		String[] trans = new String[estados*2];
		float[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			for (int j = 0; j < 2; j ++) {
				trans[i*2 +j] = "" + i + ":" + j + ":";
				for (int k = 0; k < estados+1; k++) {
					double temp = (transiciones[i][j][k] < 0.0001 ? 0 : transiciones[i][j][k]);
					temp = (temp > 0.9999 ? 1 : temp);
					NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
					format.setMaximumFractionDigits(4);
					String temp2 = format.format(temp);
					trans[i*2+j] += (k == 0 ? temp2 : ";" + temp2);
				}
				System.out.println(trans[i*2+j]);
			}
		}
		
		for (int i = 0; i < trans.length; i++)
			this.afp.getTransiciones().add(trans[i]);
	}
  
	public void setPasos(Integer pasos) {
		this.pasos = pasos;
	}

	public void setFuncbondad(String funcbondad) {
		this.funcbondad = funcbondad;
	}

	public void setCruzador(String cruzador) {
		this.cruzador = cruzador;
	}

	public void setPobmax(Integer pobmax) {
		this.pobmax = pobmax;
	}

	public void setMuestras(Integer muestras) {
		this.muestras = muestras;
	}

	public void setMutador(String mutador) {
		this.mutador = mutador;
	}

	public void setMetodoRes(String metodoRes) {
		this.metodoRes = metodoRes;
	}
	
	
}
