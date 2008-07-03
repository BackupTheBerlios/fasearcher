package es.si.ProgramadorGenetico;

import java.util.ArrayList;
import java.util.List;

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
	/**
	 * Selector que se utilizara al elegir los individuos que deben pasar
	 * de generacion
	 */
	private Selector selector;
	
	/**
	 * Cruzador que se selecciona para el algoritmo
	 */
	private Cruzador cruzador;
	
	/**
	 * Mutador que se utiliza en el algoritmo
	 */
	private Mutador mutador;
	
	/**
	 * Poblacion que manejara los miembros
	 */
	private Poblacion poblacion;
	
	/**
	 * Poblacion inicial de la que se dispone
	 */
	private PoblacionInicial poblacioninicial;
	
	/**
	 * Mejor individuo al final de la ejecucion
	 */
	private Individuo mejor;
	
	/**
	 * Lista de mejores individuos
	 */
	private List<Individuo> mejores;
	
	/**
	 * Numero de pasos dados
	 */
	private int pasos;
	
	/**
	 * Numero de muestras por defecto
	 */
	public static int MANTENER = 10;
	
	/**
	 * Poblacion maxima por defecto
	 */
	public static int POB_MAX = 1000;
	
	/**
	 * Este metodo ejecuta el algoritmo
	 * Se genera una poblacion inicial, se escogen los mejores
	 * y se comienza el algoritmo. Se seleccionan los mejores de la poblacion,
	 * se cruzan, se mutan y se añaden los mejores de los nuevos individuos
	 * a la lista de mejores.
	 * Cuando se alcanza el numero de pasos limite, se devuelve el mejor individuo 
	 * @param maxit
	 * @return
	 */
	public Individuo run(int maxit) {
		
		long tiempo = System.currentTimeMillis();
		
		poblacion = poblacioninicial.generar();
		
		Writer.write("mejores=");
	
		mejor = selector.mejor(poblacion);
		
		int cont = 0;
		
		Individuo ultimomejor;
		
		mejores = new ArrayList<Individuo>();
		
		mejores.add(mejor);
		
		do {
			Writer.write(",");

			ultimomejor = mejor;
			
			poblacion = selector.seleccionar(MANTENER, poblacion);
			
			poblacion = cruzador.entrecruzar(POB_MAX, poblacion);
			
			poblacion = mutador.mutar(poblacion);
			
			mejor = selector.mejor(poblacion);
			
			mejores.add(mejor);
			
			cont++;

		} while (cont < maxit && !mejor.equals(ultimomejor));
		
		pasos = cont;
		
		Writer.write("\nTiempo: "+ (System.currentTimeMillis() - tiempo) + "s\n");
		Writer.write("--------------------\n");
		Writer.write(mejor.toString() + "\n");
		Writer.write("--------------------\n");
		return mejor;
	}
	/**
	 * Devuelve el selector utilizado
	 * @return
	 */
	public Selector getSelector() {
		return selector;
	}
	/**
	 * Modifica el selector
	 * @param selector
	 */
	public void setSelector(Selector selector) {
		this.selector = selector;
	}
	/**
	 * Devuelve el mutador
	 * @return
	 */
	public Mutador getMutador() {
		return mutador;
	}
	/**
	 * Devuelve el cruzador
	 * @return
	 */
	public Cruzador getCruzador() {
		return cruzador;
	}
	/**
	 * Actualiza el mutador
	 * @param mutador
	 */
	public void setMutador(Mutador mutador) {
		this.mutador = mutador;
	}
	/**
	 * Actualiza el cruzador
	 * @param cruzador
	 */
	public void setCruzador(Cruzador cruzador) {
		this.cruzador = cruzador;
	}
	/**
	 * Devuelve la poblacion
	 * @return
	 */
	public Poblacion getPoblacion() {
		return poblacion;
	}
	/**
	 * Modifica la poblacion
	 * @param poblacion
	 */
	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}
	/**
	 * Devuelve la poblacion inicial
	 * @return
	 */
	public PoblacionInicial getPoblacioninicial() {
		return poblacioninicial;
	}
	/**
	 * Devuelve el mejor individuo
	 * @return
	 */
	public Individuo getMejor () {
		return mejor;
	}
	/**
	 * Modifica la poblacion inicial
	 * @param poblacioninicial
	 */
	public void setPoblacioninicial(PoblacionInicial poblacioninicial) {
		this.poblacioninicial = poblacioninicial;
	}
	/**
	 * Devuelve el numero de pasos
	 * @return
	 */
	public int getPasos() {
		return pasos;
	}
	/**
	 * Devuelve los mejores
	 * @return
	 */
	public List<Individuo> getMejores() {
		return mejores;
	}
}
