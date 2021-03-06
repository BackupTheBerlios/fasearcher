package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.ServiceWS.Afp;
import es.si.ProgramadorGenetico.ServiceWS.Configuracion;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherService;
import es.si.ProgramadorGenetico.ServiceWS.FASearcherServiceBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.AddProblemaRequest;
import es.si.fasearcherserver.AddProblemaResponse;



/**
 * Esta clase es utilizada para contactar al serividor y a�adir
 * un nuevo problema en la BBDD.<p>
 * 
 * Para usar el m�todo correspondiente hay que crear una instacia,
 * luego dar valores a los distintos par�metos y por ultimo, al
 * llamar al m�todo "ejecutar" se env�a la informaci�n al servidor.
 * Una vez enviada la informaci�n al servidor este devuevle
 * el id del problema, que se puede obtener de la instacia.
 */
public class AddProblemaWS {

	/**
	 * Lista de palabras aceptadas por el problema
	 */
	private List<String> aceptadas;
	
	/**
	 * Lista de palabras rechazadaas por el problema
	 */
	private List<String> rechazadas;
	
	/**
	 * Lista de configuraciones para resolver el problema
	 */
	private List<Configuracion> configs;
	
	/**
	 * Tipo del automata para el que fue creado el problema
	 */
	private String tipoAutomata;
	
	/**
	 * Automata original con el que se creo el lenguaje
	 */
	private Afp afp;
	
	/**
	 * Numero de estados que tenia el automata original
	 */
	private Integer estados;
	
	private Integer pobMax;
	
	/**
	 * Descripci�n del problema que se esta subiendo a la base de datos
	 */
	private String descripcion;

	/**
	 * Id del nuevo problema a�adido a la base de datos
	 */
	private String id;

	public AddProblemaWS() {
		configs = new ArrayList<Configuracion>();
	}
	
	/**
	 * Ejecutar la llamada al m�todo del servidor.
	 * 
	 * @return
	 * Devuevle un booleano indicando si la ejecuci�n del m�todo fue satisfactoria
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
				
				AddProblemaRequest apr = new AddProblemaRequest();

				Iterator<String> it = aceptadas.iterator();
				while (it.hasNext())
					apr.getAceptadas().add(it.next());
				it = rechazadas.iterator();
				while (it.hasNext())
					apr.getRechazadas().add(it.next());
				apr.setTipoAutomata(tipoAutomata);
				apr.setAfp(afp);
				apr.setEstados(estados);
				apr.setPobMax(pobMax);
				apr.getConfiguraciones().addAll(configs);
				apr.setDescripcion(descripcion);
				
				AddProblemaResponse gpresponse = fas.addProblema(apr);

				id = gpresponse.getId();

			
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return false;
			}
			
		return true;
	}

	public String getId() {
		return id;
	}

	public void setAceptadas(List<String> aceptadas) {
		this.aceptadas = aceptadas;
	}

	public void setRechazadas(List<String> rechazadas) {
		this.rechazadas = rechazadas;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
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
					float temp = (transiciones[i][j][k] < 0.0001 ? 0 : transiciones[i][j][k]);
					temp = (temp > 0.9999 ? 1 : temp);
					NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
					format.setMaximumFractionDigits(4);
					String temp2 = format.format(temp);
					trans[i*2+j] += (k == 0 ? temp2 : ";" + temp2);
				}
			}
		}
		for (int i = 0; i < trans.length; i++)
			this.afp.getTransiciones().add(trans[i]);
	}

	public void setEstados(Integer estados) {
		this.estados = estados;
	}

	public void setPobMax(Integer pobMax) {
		this.pobMax = pobMax;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void addConfiguracion(int muestras, int pobMax, int estados, int calculadorBondad, int cruzador, int mutador, int resolver) {
		Configuracion conf = new Configuracion();
		conf.setMuestras(muestras);
		conf.setPobMax(pobMax);
		conf.setEstados(estados);
		conf.setCalculadorBondad(calculadorBondad);
		conf.setCruzador(cruzador);
		conf.setMutador(mutador);
		conf.setResolver(resolver);
		configs.add(conf);
	}
	
	
	
}
