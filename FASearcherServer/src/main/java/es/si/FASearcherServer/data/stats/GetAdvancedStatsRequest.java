package es.si.FASearcherServer.data.stats;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetAdvancedStatsRequest {

	/**
	 * Lista de los identificadores del problema
	 */
	private List<String> id_problemas;
	
	/**
	 * Lista de los identificadores de configuración
	 */
	private List<Integer> id_config;
	
	/**
	 * Lista de los mutadores
	 */
	private List<String> mutadores;
	
	/**
	 * Lista de los cruzadores
	 */
	private List<String> cruzadores;
	
	/**
	 * Lista de los calculadores de bondad
	 */
	private List<String> funcBondad;
	
	/**
	 * Lista del número de pasos
	 */
	private List<Integer> pasos;
	
	/**
	 * Lista del número de estados
	 */
	private List<Integer> estados;
	
	/**
	 * Lista de la población máxima
	 */
	private List<Integer> pobmax;
	
	/**
	 * Lista del número de muestras
	 */
	private List<Integer> muestras;

	public List<String> getId_problemas() {
		return id_problemas;
	}

	public void setId_problemas(List<String> id_problemas) {
		this.id_problemas = id_problemas;
	}

	public List<Integer> getId_config() {
		return id_config;
	}

	public void setId_config(List<Integer> id_config) {
		this.id_config = id_config;
	}

	public List<String> getMutadores() {
		return mutadores;
	}

	public void setMutadores(List<String> mutadores) {
		this.mutadores = mutadores;
	}

	public List<String> getCruzadores() {
		return cruzadores;
	}

	public void setCruzadores(List<String> cruzadores) {
		this.cruzadores = cruzadores;
	}

	public List<String> getFuncBondad() {
		return funcBondad;
	}

	public void setFuncBondad(List<String> funcBondad) {
		this.funcBondad = funcBondad;
	}

	public List<Integer> getPasos() {
		return pasos;
	}

	public void setPasos(List<Integer> pasos) {
		this.pasos = pasos;
	}

	public List<Integer> getPobmax() {
		return pobmax;
	}

	public void setPobmax(List<Integer> pobmax) {
		this.pobmax = pobmax;
	}

	public List<Integer> getMuestras() {
		return muestras;
	}

	public void setMuestras(List<Integer> muestras) {
		this.muestras = muestras;
	}

	public List<Integer> getEstados() {
		return estados;
	}

	public void setEstados(List<Integer> estados) {
		this.estados = estados;
	}
		
}
