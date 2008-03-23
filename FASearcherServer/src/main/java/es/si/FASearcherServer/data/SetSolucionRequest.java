package es.si.FASearcherServer.data;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://FASearcherServer.si.es/")
public class SetSolucionRequest {

	private String id;
	
	private String mejorValor;
	
	private AFP afp;
	
	private Integer pasos;
	
	private String algoritmo;
	
	private String funcbondad;
	
	private String cruzador;
	
	private Integer pobmax;
	
	private Integer muestras;

	public Integer getPasos() {
		return pasos;
	}

	public void setPasos(Integer pasos) {
		this.pasos = pasos;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getFuncbondad() {
		return funcbondad;
	}

	public void setFuncbondad(String funcbondad) {
		this.funcbondad = funcbondad;
	}

	public String getCruzador() {
		return cruzador;
	}

	public void setCruzador(String cruzador) {
		this.cruzador = cruzador;
	}

	public Integer getPobmax() {
		return pobmax;
	}

	public void setPobmax(Integer pobmax) {
		this.pobmax = pobmax;
	}

	public Integer getMuestras() {
		return muestras;
	}

	public void setMuestras(Integer muestras) {
		this.muestras = muestras;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMejorValor() {
		return mejorValor;
	}

	public void setMejorValor(String mejorValor) {
		this.mejorValor = mejorValor;
	}

	public AFP getAfp() {
		return afp;
	}

	public void setAfp(AFP afp) {
		this.afp = afp;
	}

	

}
