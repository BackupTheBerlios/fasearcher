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
	
	private Cruzador cruzador;
	
	private Mutador mutador;
	
	private Poblacion poblacion;
	
	private PoblacionInicial poblacioninicial;
	
	private Individuo mejor;
	
	public static int MANTENER = 10;
	
	public static int POB_MAX = 1000;
	
	public Individuo run(int maxit) {
		
		long tiempo = System.currentTimeMillis();
		
		poblacion = poblacioninicial.generar();

		Writer.write("mejores=");
	
		mejor = selector.mejor(poblacion);
		
		int cont = 0;
		
		Individuo ultimomejor;
		
		
		do {
			Writer.write(",");

			ultimomejor = mejor;
			
			poblacion = selector.seleccionar(MANTENER, poblacion);
			
			poblacion = cruzador.entrecruzar(POB_MAX, poblacion);
			
			poblacion = mutador.mutar(poblacion);
			
			mejor = selector.mejor(poblacion);
			
			cont++;
			
			
		} while (cont < maxit && !mejor.equals(ultimomejor));
		
		Writer.write("\nTiempo: "+ (System.currentTimeMillis() - tiempo)/1000 + "s\n");
		Writer.write("--------------------\n");
		Writer.write(mejor.toString() + "\n");
		Writer.write("--------------------\n");
		return null;
	}



	public Selector getSelector() {
		return selector;
	}



	public void setSelector(Selector selector) {
		this.selector = selector;
	}


	public Mutador getMutador() {
		return mutador;
	}

	public Cruzador getCruzador() {
		return cruzador;
	}

	public void setMutador(Mutador mutador) {
		this.mutador = mutador;
	}
	
	public void setCruzador(Cruzador cruzador) {
		this.cruzador = cruzador;
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
