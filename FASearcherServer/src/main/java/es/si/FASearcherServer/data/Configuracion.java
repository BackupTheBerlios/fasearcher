package es.si.FASearcherServer.data;

/**
 * Clase que guarda la información de la configuración.<p>
 * 
 *
 */
public class Configuracion {
	
	/**
	 * Número de muestras
	 */
	private int muestras;
	
	/**
	 * Población máxima
	 */
	private int pobMax;
	
	/**
	 * Número de estados
	 */
	private int estados;
	
	/**
	 * Calculador de bondad
	 */
	private int calculadorBondad;
	
	/**
	 * Cruzador
	 */
	private int cruzador;
	
	/**
	 * Mutador
	 */
	private int mutador;
	
	/**
	 * Resolutor
	 */
	private int resolver;
	
	/**
	 * Identificador de la configuración
	 */
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		this();
		this.muestras = muestras;
		this.pobMax = pobMax;
		this.estados = estados;
	}
	
	public Configuracion(int id, int muestras, int pobMax, int estados, int calculadorBondad, int cruzador, int mutador, int resolver) {
		this(muestras,pobMax,estados);
		this.id = id;
		this.calculadorBondad = calculadorBondad;
		this.cruzador = cruzador;
		this.mutador = mutador;
		this.resolver = resolver;
	}
}
