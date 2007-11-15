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
	
	private int estados = 5;
	
	private ParametrosAFP() {
		aceptadas = new ArrayList<String>();
		rechazadas = new ArrayList<String>();
		
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
			estados = Integer.getInteger(valores.getProperty("estados")).intValue();
		}
		
		StringTokenizer st = new StringTokenizer(valores.getProperty("aceptadas", ""), ",");
		while(st.hasMoreTokens()) {
			aceptadas.add(st.nextToken());
		}
		
		st = new StringTokenizer(valores.getProperty("rechazadas", ""), ",");
		while(st.hasMoreTokens()) {
			rechazadas.add(st.nextToken());
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

	public void setRechazadas(List<String> rechazadas) {
		this.rechazadas = rechazadas;
	}
	
	public int getEstados() {
		return estados;
	}
	
	public void setEstados(int estados) {
		this.estados = estados;
	}
	
}
