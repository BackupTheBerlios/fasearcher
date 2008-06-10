package es.si.ProgramadorGenetico.Interfaz;

import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;

/**
 * Clase que se encarga de poner en marcha la resolucion de problemas
 */
public class ResolverProblemas implements Runnable {

	/**
	 * Atributo que indica si la ejecucion esta parada
	 */
	private boolean stopped = false;
	/**
	 * Atributo que guarda la interfaz sobre la que actuar
	 */
	private InterfazGrafica interfaz;
	/**
	 * Constructora principal que inicializa el valor de la interfaz
	 * @param interfaz
	 */
	public ResolverProblemas(InterfazGrafica interfaz) {
		this.interfaz = interfaz;
	}
	/**
	 * Metodo run que inicia la resolucion de los problemas
	 */
	public void run() {
		try {
			while (!stopped) {
				ProblemaAFP problemaAFP = new ProblemaAFP();
				problemaAFP.ejecutar();
				if (interfaz != null) {
					interfaz.terminoResolverMuchos(true, problemaAFP.getMejor());
				}
			}
		} catch (Exception e) {
			interfaz.terminoResolverMuchos(false, null);
		}
	}
	/**
	 * Metodo que para la ejecucion
	 */
	public void stop() {
		stopped = true;
	}
}
