package es.si.FASearcherServer.data;

public class Configuracion {
	private int muestras;
	private int pobMax;
	private int estados;

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

	public int getEstados() {
		return estados;
	}

	public void setEstados(int estados) {
		this.estados = estados;
	}

	public Configuracion() {
	}
	
	public Configuracion(int muestras, int pobMax, int estados) {
		super();
		this.muestras = muestras;
		this.pobMax = pobMax;
		this.estados = estados;
	}

}
