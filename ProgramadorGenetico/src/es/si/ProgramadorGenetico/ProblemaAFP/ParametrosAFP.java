package es.si.ProgramadorGenetico.ProblemaAFP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

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
	
	private List<String> aceptadas;
	
	private List<String> rechazadas;
	
	private List<Integer> muestras;
	
	private List<Integer> pobmax;
	
	private int estados = 5;
	
	private int particiones = 200;
		
	private ParametrosAFP() {
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
		}
		
		st = new StringTokenizer(valores.getProperty("rechazadas", ""), ",");
		while(st.hasMoreTokens()) {
			rechazadas.add(st.nextToken());
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
	
}
