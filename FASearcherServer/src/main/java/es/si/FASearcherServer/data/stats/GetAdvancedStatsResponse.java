package es.si.FASearcherServer.data.stats;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetAdvancedStatsResponse {
	
	/**
	 * Número de problemas en la base de datos
	 */
	private Integer numProblemas;
	
	/**
	 * Media de soluciones por problema en la base de datos
	 */
	private Float mediaSoluciones;
	
	/**
	 * Datos del histograma de reconocimiento
	 */
	private List<Double> histReconocimiento;
	
	/**
	 * Datos del histograma de "Parecido AF"
	 */
	private List<Double> histParecido;

	public Integer getNumProblemas() {
		return numProblemas;
	}

	public void setNumProblemas(Integer numProblemas) {
		this.numProblemas = numProblemas;
	}

	public Float getMediaSoluciones() {
		return mediaSoluciones;
	}

	public void setMediaSoluciones(Float mediaSoluciones) {
		this.mediaSoluciones = mediaSoluciones;
	}

	public List<Double> getHistReconocimiento() {
		return histReconocimiento;
	}

	public void setHistReconocimiento(List<Double> histReconocimiento) {
		this.histReconocimiento = histReconocimiento;
	}

	public List<Double> getHistParecido() {
		return histParecido;
	}

	public void setHistParecido(List<Double> histParecido) {
		this.histParecido = histParecido;
	}
	
	
	
}
