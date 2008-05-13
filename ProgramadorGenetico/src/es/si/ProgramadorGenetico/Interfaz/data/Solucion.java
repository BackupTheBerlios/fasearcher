package es.si.ProgramadorGenetico.Interfaz.data;

public class Solucion {

	private Integer id;
	
	private int estados;
	
	private double reconocmiento;
	
	private double parecidoAF;
	
	private String tipoAutomata;
	
	private int pasos;
	
	private String mutador;
	
	private String calculadorBondad;
	
	private String cruzador;
	
	private String metodoRes;
	
	private String pobMax;
	
	private String muestras;
	
	private String particiones;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getEstados() {
		return estados;
	}

	public void setEstados(int estados) {
		this.estados = estados;
	}

	public double getReconocmiento() {
		return reconocmiento;
	}

	public void setReconocmiento(double reconocmiento) {
		this.reconocmiento = reconocmiento;
	}

	public double getParecidoAF() {
		return parecidoAF;
	}

	public void setParecidoAF(double parecidoAF) {
		this.parecidoAF = parecidoAF;
	}

	public String getTipoAutomata() {
		return tipoAutomata;
	}

	public void setTipoAutomata(String tipoAutomata) {
		this.tipoAutomata = tipoAutomata;
	}

	public int getPasos() {
		return pasos;
	}

	public void setPasos(int pasos) {
		this.pasos = pasos;
	}

	public String getMutador() {
		return mutador;
	}

	public void setMutador(String mutador) {
		this.mutador = mutador;
	}

	public String getCalculadorBondad() {
		return calculadorBondad;
	}

	public void setCalculadorBondad(String calculadorBondad) {
		this.calculadorBondad = calculadorBondad;
	}

	public String getCruzador() {
		return cruzador;
	}

	public void setCruzador(String cruzador) {
		this.cruzador = cruzador;
	}

	public String getMetodoRes() {
		return metodoRes;
	}

	public void setMetodoRes(String metodoRes) {
		this.metodoRes = metodoRes;
	}

	public String getPobMax() {
		return pobMax;
	}

	public void setPobMax(String pobMax) {
		this.pobMax = pobMax;
	}

	public String getMuestras() {
		return muestras;
	}

	public void setMuestras(String muestras) {
		this.muestras = muestras;
	}

	public String getParticiones() {
		return particiones;
	}

	public void setParticiones(String particiones) {
		this.particiones = particiones;
	}
}
