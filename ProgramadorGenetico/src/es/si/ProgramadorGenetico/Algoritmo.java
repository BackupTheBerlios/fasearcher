package es.si.ProgramadorGenetico;

/**
 * Algoritmo genético básico.<p>
 * 
 * Esquema básico del algoritmo genético. Necesita un problema específico al que aplicarse
 * definido mediante implementaciones concretas de Individuo, PoblacionInicial, Reproductor y Selector.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public class Algoritmo {
	
	private Selector selector;
	
	private Reproductor reproductor;
	
	private Poblacion poblacion;
	
	private PoblacionInicial poblacioninicial;
	
	private Individuo mejor;
	
	public static int MANTENER = 10;
	
	public static int POB_MAX = 1000;
	
	public Individuo run(int maxit) {
		
		poblacion = poblacioninicial.generar();
	
		mejor = selector.mejor(poblacion);
		
		int cont = 0;
		
		Individuo ultimomejor;
		
		do {
			ultimomejor = mejor;
			
			poblacion = selector.seleccionar(MANTENER, poblacion);
			
			poblacion = reproductor.entrecruzar(POB_MAX, poblacion);
			
			poblacion = reproductor.mutar(poblacion);
			
			mejor = selector.mejor(poblacion);
			cont++;
		} while (cont < maxit && !mejor.equals(ultimomejor));
		
		return null;
	}



	public Selector getSelector() {
		return selector;
	}



	public void setSelector(Selector selector) {
		this.selector = selector;
	}



	public Reproductor getReproductor() {
		return reproductor;
	}



	public void setReproductor(Reproductor reproductor) {
		this.reproductor = reproductor;
	}



	public Poblacion getPoblacion() {
		return poblacion;
	}



	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}



	public PoblacionInicial getPoblacioninicial() {
		return poblacioninicial;
	}



	public void setPoblacioninicial(PoblacionInicial poblacioninicial) {
		this.poblacioninicial = poblacioninicial;
	};
	
}
