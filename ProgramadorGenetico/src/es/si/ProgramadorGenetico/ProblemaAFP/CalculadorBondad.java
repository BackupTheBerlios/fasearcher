package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;

/**
 * Clase abstracta extendida por los calculadores de bondad.<p>
 * 
 * Los calculadores de bondad determinan que tan bueno es un AFP
 * para reconocer un lenguaje de acuerdo a diferentes criterios.
 * Esta clase contiene los métodos y funciones comunes a todos
 * ellos o que pueden ser utilizados por cualquiera de ellos.<br>
 * Esta clase implmenta la interfaz Runnable con el fin de que los
 * calculadores de bondad puedan aplicarse de forma paralela.
 *
 */
public abstract class CalculadorBondad implements Runnable {
	
	/**
	 * Resultado final de bondad calculada para el AFP
	 */
	protected double bondad;
	
	/**
	 * Booleano que indica si ya se termino de realizar el calculo
	 */
	private boolean termino;
	
	/**
	 * Boolean que indica si se está realizando el calculo de la bondad
	 */
	private boolean procesando;
	
	/**
	 * AFP del que se desea calcular la bondad
	 */
	private AFP afp;
	
	/**
	 * Cadenas aceptadas del lenguaje que se pretende que reconozca el AFP
	 */
	protected List<String> cadenasAceptadas;
	
	/**
	 * Cadenas rechazadas del lenguaje que se pretende que reconozca el AFP
	 */
	protected List<String> cadenasRechazadas;
	
	/**
	 * Atributo utilizado para el control en paralelo de los calculadores
	 * de bondad.
	 */
	CountDownLatch cdl = null;
	
	/**
	 * Constructor básico de los calculadores de bondad. Toma como parámetros
	 * el AFP y las cadenas aceptadas y rechazadas por el lenguaje.
	 * 
	 * @param afp
	 * AFP del que se quiere calcular la bondad.
	 * @param cadenasAceptadas
	 * Cadenas aceptadas por el lenguaje
	 * @param cadenasRechazadas
	 * Cadenas rechazadas por el lenguaje
	 */
	protected CalculadorBondad(AFP afp, List<String> cadenasAceptadas, List<String> cadenasRechazadas) {
		this.cadenasAceptadas = new ArrayList<String>(cadenasAceptadas);
		this.cadenasRechazadas = new ArrayList<String>(cadenasRechazadas);
		this.afp = afp;
		termino = false;
		procesando = false;
		bondad = 0;
	}
		
	public void setCountDownLatch(CountDownLatch cdl){
		this.cdl = cdl;
	}
	
	@Override
	public void run() {
		if (this.termino) return;
		this.procesando = true;
		Iterator<String> aceptadas = cadenasAceptadas.iterator();
		while(aceptadas.hasNext()) {
			ResolverAFP resolver = ResolverAFPFactory.getResolverAFP();
			resolver.setAFP(afp);
			resolver.setCadena(aceptadas.next());
			resolver.run();
			actualizarBondadAceptada(resolver.getProbabilidadAceptar());		
		}
		Iterator<String> rechazadas = cadenasRechazadas.iterator();
		while(rechazadas.hasNext()) {
			ResolverAFP resolver = ResolverAFPFactory.getResolverAFP();
			resolver.setAFP(afp);
			resolver.setCadena(rechazadas.next());
			resolver.run();
			actualizarBondadRechazada(1 - resolver.getProbabilidadAceptar());
		}
		this.procesando = false;
		this.termino = true;
		if (cdl != null)
			cdl.countDown();
	}
	
	public boolean getProcesando() {
		return procesando;
	}
	
	public boolean getTermino() {
		return termino;
	}
	
	public double getBondad() {
		return bondad;
	}
	
	public AFP getAFP() {
		return afp;
	}
	
	/**
	 * Método abstracto, implementado por los calculadores de bondad
	 * especificos, para actulizar la bondad con la probabilidad de
	 * aceptar una cadena aceptada por el lenguaje.
	 * @param probabilidad
	 * Probabilidad de que una cadena aceptada sea aceptada
	 */
	public abstract void actualizarBondadAceptada(double probabilidad);
	
	/**
	 * Método abstracto, implementado por los calculadores de bondad
	 * especificos, para actulizar la bondad con la probabilidad de
	 * rechazar una cadena rechazada por el lenguaje.
	 * @param probabilidad
	 * Probabilidad de que una cadena rechazada sea rechazada
	 */
	public abstract void actualizarBondadRechazada(double probabilidad);

	/**
	 * @param afp the afp to set
	 */
	public void setAfp(AFP afp) {
		this.afp = afp;
	}

	/**
	 * @param cadenasAceptadas the cadenasAceptadas to set
	 */
	public void setCadenasAceptadas(List<String> cadenasAceptadas) {
		this.cadenasAceptadas = cadenasAceptadas;
	}

	/**
	 * @param cadenasRechazadas the cadenasRechazadas to set
	 */
	public void setCadenasRechazadas(List<String> cadenasRechazadas) {
		this.cadenasRechazadas = cadenasRechazadas;
	}

}
