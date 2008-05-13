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
 * Clase Singelton que mantiene los valores específicos del problema que se quieres solucionar.<p>
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
	
	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private List<Integer> muestras;
	
	private List<Integer> pobmax;
	
	private String id;
	
	private int estados = 5;
	
	private int particiones = 100;
	
	private int numeroMuestras = 0;
	
	private int calculadorBondad = 3;
	
	private int mutador = 1;
	
	private int cruzador = 1;
	
	private int resolver = 0;

	private ParametrosAFP() {
		if (origen == FROM_WEB)
			deWeb();
		if (origen == FROM_FILE)
			deFichero();
		else
			deWeb();		
	}
	
	public static void recrear() {
		parametros = new ParametrosAFP();
	}
	
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
			
			numeroMuestras = aceptadas.size() + rechazadas.size();
			id = getProblemaWS.getId();
		}
	}
	
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
	
	public static ParametrosAFP getInstance() {
		if (parametros == null) {
			parametros = new ParametrosAFP();
		}
		return parametros;
	}

	public List<String> getAceptadas() {
		return aceptadas;
	}

	public void setAceptadas(List<String> aceptadas) {
		this.aceptadas = aceptadas;
	}

	public List<String> getRechazadas() {
		return rechazadas;
	}
	
	public int getNumeroMuestras() {
		return this.numeroMuestras;
	}
	
	public List<Integer> getMuestras() {
		return muestras;
	}

	public List<Integer> getPobmax() {
		return pobmax;
	}
	
	public void setRechazadas(List<String> rechazadas) {
		this.rechazadas = rechazadas;
	}
	
	public int getEstados() {
		return estados;
	}
	
	public void setEstados(int estados) {
		this.estados = estados;
	}
	
	public int getParticiones(){
		return particiones;
	}
	
	public void setParticiones(int particiones) {
		this.particiones = particiones;
	}
	
	public void setNumeroMuestras (int num) {
		numeroMuestras = num;
	}
	
	public String getId() {
		return id;
	}

	public int getCalculadorBondad() {
		return calculadorBondad;
	}

	public int getMutador() {
		return mutador;
	}

	public int getCruzador() {
		return cruzador;
	}

	public int getResolver() {
		return resolver;
	}
	
	/**
	 * @param origen the origen to set
	 */
	public static void setOrigen(int origen) {
		ParametrosAFP.origen = origen;
	}
}
