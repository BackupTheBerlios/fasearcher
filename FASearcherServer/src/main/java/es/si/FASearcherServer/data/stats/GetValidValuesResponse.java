package es.si.FASearcherServer.data.stats;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class GetValidValuesResponse {
	
	private List<String> mutadores;
	
	private List<String> cruzadores;
	
	private List<String> funcBondad;

	private List<Integer> pobMax;
	
	private List<Integer> muestras;
	
	private List<Integer> estados;
	
	private List<Integer> pasos;
	
	public List<Integer> getPasos() {
		return pasos;
	}

	public void setPasos(List<Integer> pasos) {
		this.pasos = pasos;
	}

	public List<Integer> getPobMax() {
		return pobMax;
	}

	public void setPobMax(List<Integer> pobMax) {
		this.pobMax = pobMax;
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
	
	
}
