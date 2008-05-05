package es.si.ProgramadorGenetico.Interfaz.data;

public class Configuracion {

	private int estados;
	
	private int muestras;
	
	private int pobMax;
	
	private int calculadorBondad;
	
	private int cruzador;
	
	private int mutador;
	
	private int resolver;

	public int getEstados() {
		return estados;
	}

	public void setEstados(int estados) {
		this.estados = estados;
	}

	public int getMuestras() {
		return muestras;
	}

	public void setMuestras(int muestras) {
		this.muestras = muestras;
	}

	public int getPobMax() {
		return pobMax;
	}

	public void setPobMax(int pobMax) {
		this.pobMax = pobMax;
	}

	public int getCalculadorBondad() {
		return calculadorBondad;
	}

	public void setCalculadorBondad(int calculadorBondad) {
		this.calculadorBondad = calculadorBondad;
	}

	public int getCruzador() {
		return cruzador;
	}

	public void setCruzador(int cruzador) {
		this.cruzador = cruzador;
	}

	public int getMutador() {
		return mutador;
	}

	public void setMutador(int mutador) {
		this.mutador = mutador;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}


}
