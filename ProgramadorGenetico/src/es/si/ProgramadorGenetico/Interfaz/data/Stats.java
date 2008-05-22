package es.si.ProgramadorGenetico.Interfaz.data;

import java.util.List;

public class Stats {
	private Integer numProblemas;
	
	private Float mediaSoluciones;
	
	private List<Double> histReconocimiento;
	
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
