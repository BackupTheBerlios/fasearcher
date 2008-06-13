package es.si.ProgramadorGenetico.ProblemaAFP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import es.si.ProgramadorGenetico.WS.GetProblemaBasicWS;

/**
 * Clase Singleton que mantiene los valores específicos del problema que se quieres solucionar.<p>
 * 
 * Esta clase mantiene los valores del problema, en este caso las cadenas aceptadas y rechazadas por
 * el automata.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public class ParametrosAFP {
	
	private static ParametrosAFP parametros;
	
	private static int origen = 1;
	
	public static final int FROM_FILE = 1;
	
	public static final int FROM_WEB = 2;
	/**
	 * Lista de cadenas aceptadas
	 */
	private List<String> aceptadas;
	/**
	 * Lista de cadenas rechazadas
	 */
	private List<String> rechazadas;
	/**
	 * Lista de muestras
	 */
	private List<Integer> muestras;
	/**
	 * Poblacion maxima
	 */
	private List<Integer> pobmax;
	/**
	 * Id del problema
	 */
	private String id;
	/**
	 * Numero de estados por defecto
	 */
	private int estados = 5;
	/**
	 * Numero de particiones o iteraciones por defecto
	 */
	private int particiones = 100;
	/**
	 * Numero de muestras
	 */
	private int numeroMuestras = 0;
	/**
	 * Calculador de bondad seleccionado
	 */
	private int calculadorBondad = 3;
	/**
	 * Mutador seleccionado
	 */
	private int mutador = 1;
	/**
	 * Cruzador seleccionado
	 */
	private int cruzador = 1;
	/**
	 * Resolutor seleccionado
	 */
	private int resolver = 0;
	/**
	 * Id de la configuracion
	 */
	private Integer id_config;

	/**
	 * Connstructora que coge el problema correspondiente
	 * de Internet o de un fichero
	 */
	private ParametrosAFP() {
		if (origen == FROM_WEB)
			deWeb();
		if (origen == FROM_FILE)
			deFichero();
		else
			deWeb();		
	}
	/**
	 * Se crea el objeto en side forma estatica
	 */
	public static void recrear() {
		parametros = new ParametrosAFP();
	}
	/**
	 * Obtiene el problema de la base de datos, y coge
	 * todos los parámetros, o de un fichero de
	 * texto si no puede completar la operacion
	 */
	private void deWeb() {
		GetProblemaBasicWS getProblemaWS = new GetProblemaBasicWS();
		if (!getProblemaWS.ejecutar()) {
			System.out.println("Error al buscar información de web");
			deFichero();
		}
		else {
			aceptadas = getProblemaWS.getAceptadas();
			rechazadas = getProblemaWS.getRechazadas();
			muestras = new ArrayList<Integer>();
			muestras.add(new Integer(getProblemaWS.getMuestras()));
			pobmax = new ArrayList<Integer>();
			pobmax.add(new Integer(getProblemaWS.getPobMax()));
			estados = getProblemaWS.getEstados();
			particiones = 100;
			calculadorBondad = getProblemaWS.getCalculadorBondad();
			cruzador = getProblemaWS.getCruzador();
			mutador = getProblemaWS.getMutador();
			resolver = getProblemaWS.getResolver();
			id_config = getProblemaWS.getId_config();
			
			numeroMuestras = aceptadas.size() + rechazadas.size();
			id = getProblemaWS.getId();
		}
	}
	/**
	 * Coge los datos de un fichero de texto
	 */
	private void deFichero() {
		aceptadas = new ArrayList<String>();
		rechazadas = new ArrayList<String>();
		muestras = new ArrayList<Integer>();
		pobmax = new ArrayList<Integer>();
		
		Properties valores = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("./valores.txt");
			valores.load(fis);
		} catch (FileNotFoundException e) {
			System.out.print("No se encontro el fichero valores.txt");
		} catch (IOException e) {
			System.out.print("Problemas leyendo el fichero de entrada");
		}
		
		if (valores.getProperty("estados") != null) {
			estados = Integer.valueOf(valores.getProperty("estados")).intValue();
		}
				
		particiones = Integer.valueOf(valores.getProperty("particiones", "200")).intValue();
		
		StringTokenizer st = new StringTokenizer(valores.getProperty("aceptadas", ""), ",");
		while(st.hasMoreTokens()) {
			aceptadas.add(st.nextToken());
			numeroMuestras++;
		}
		
		st = new StringTokenizer(valores.getProperty("rechazadas", ""), ",");
		while(st.hasMoreTokens()) {
			rechazadas.add(st.nextToken());
			numeroMuestras++;
		}
		
		if (valores.getProperty("muestras") != null) {
			st = new StringTokenizer(valores.getProperty("muestras", ""), ",");
			while(st.hasMoreTokens()) {
				muestras.add(Integer.parseInt(st.nextToken()));
			}			
		}

		if (valores.getProperty("pobmax") != null) {
			st = new StringTokenizer(valores.getProperty("pobmax", ""), ",");
			while(st.hasMoreTokens()) {
				pobmax.add(Integer.parseInt(st.nextToken()));
			}			
		}
	}
	/**
	 * Devuelve la instancia del objeto ParametrosAFP
	 * @return
	 */
	public static ParametrosAFP getInstance() {
		if (parametros == null) {
			parametros = new ParametrosAFP();
		}
		return parametros;
	}
	/**
	 * Devuelve las cadenas aceptadas
	 * @return
	 */
	public List<String> getAceptadas() {
		return aceptadas;
	}
	/**
	 * Actualiza las cadenas aceptadas
	 * @param aceptadas
	 */
	public void setAceptadas(List<String> aceptadas) {
		this.aceptadas = aceptadas;
	}
	/**
	 * Devuelve las cadenas rechazadas
	 * @return
	 */
	public List<String> getRechazadas() {
		return rechazadas;
	}
	/**
	 * Devuelve el numero de muestras 
	 * @return
	 */
	public int getNumeroMuestras() {
		return this.numeroMuestras;
	}
	/**
	 * Devuelve la lista de muestras
	 * @return
	 */
	public List<Integer> getMuestras() {
		return muestras;
	}
	/**
	 * Devuelve la poblacion maxima
	 * @return
	 */
	public List<Integer> getPobmax() {
		return pobmax;
	}
	/**
	 * Actualiza las cadenas rechazadas
	 * @param rechazadas
	 */
	public void setRechazadas(List<String> rechazadas) {
		this.rechazadas = rechazadas;
	}
	/**
	 * Devuelve el numero de estados
	 * @return
	 */
	public int getEstados() {
		return estados;
	}
	/**
	 * Actualiza el numero de estados
	 * @param estados
	 */
	public void setEstados(int estados) {
		this.estados = estados;
	}
	/**
	 * Devuelve el numero de particiones
	 * @return
	 */
	public int getParticiones(){
		return particiones;
	}
	/**
	 * Actualiza el numero de particiones
	 * @param particiones
	 */
	public void setParticiones(int particiones) {
		this.particiones = particiones;
	}
	/**
	 * Actualiza el numero de muestras
	 * @param num
	 */
	public void setNumeroMuestras (int num) {
		numeroMuestras = num;
	}
	/**
	 * Devuelve el id del problema
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * Devuelve el calculador de bondad
	 * @return
	 */
	public int getCalculadorBondad() {
		return calculadorBondad;
	}
	/**
	 * Devuelve el mutador
	 * @return
	 */
	public int getMutador() {
		return mutador;
	}
	/**
	 * Devuelve el cruzador
	 * @return
	 */
	public int getCruzador() {
		return cruzador;
	}
	/**
	 * Devuelve el resolutor
	 * @return
	 */
	public int getResolver() {
		return resolver;
	}
	
	/**
	 * Actualiza el origen
	 * @param origen
	 */
	public static void setOrigen(int origen) {
		ParametrosAFP.origen = origen;
	}
	/**
	 * Devuelve el id de la configuracion
	 * @return
	 */
	public Integer getId_config() {
		return id_config;
	}
}
