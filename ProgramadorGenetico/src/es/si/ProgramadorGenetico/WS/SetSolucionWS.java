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
 * Esta clase es utilizada para a�adir una nueva soluci�n a un problema.<p>
 * 
 * Para usar el m�todo correspondiente hay que crear una instacia,
 * luego dar valores a los distintos par�metos y por ultimo, al
 * llamar al m�todo "ejecutar" se env�a la informaci�n al servidor.
 * El servidor almacena la informaci�n de la soluci�n en al base de datos.
 */
public class SetSolucionWS {

	/**
	 * Id del problema
	 */
	private String id;
	
	/**
	 * Valor de reconocimento de la soluci�n
	 */
	private String reconocimiento;
	
	/**
	 * Valor de parecido con un AF de la soluci�n
	 */
	private String parecidoAF;
	
	/**
	 * AFP resultante de la resoluci�n del problmea
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
	 * M�todo resolutor utilizado, incluyendo la version
	 */
	private String metodoRes;
	
	/**
	 * Funcion de bondad utilizada, incluyendo la version
	 */
	private String funcbondad;
	
	/**
	 * Cruzador utilizado, incluyendo la versi�n
	 */
	private String cruzador;
	
	/**
	 * Poblaci�n m�xima utilizada
	 */
	private Integer pobmax;
	
	/**
	 * N�mero de muestras  utilizadas
	 */
	private Integer muestras;
	
	/**
	 * Particiones utilizadas para generar el automata original
	 */
	private Integer particiones;
	
	/**
	 * Identificador de la configuraci�n pasada como par�metro 
	 */
	private Integer id_config;
	
	public void setId_config(Integer id_config) {
		this.id_config = id_config;
	}

	public void setParticiones(Integer particiones) {
		this.particiones = particiones;
	}

	/**
	 * Ejecutar la llamada al m�todo del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecuci�n del m�todo fue satisfactoria
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
