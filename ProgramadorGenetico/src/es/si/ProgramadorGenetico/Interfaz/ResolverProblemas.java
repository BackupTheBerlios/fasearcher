package es.si.ProgramadorGenetico.Interfaz;

import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;

public class ResolverProblemas implements Runnable {

	private boolean stopped = false;
	
	private InterfazGrafica interfaz;
	
	public ResolverProblemas(InterfazGrafica interfaz) {
		this.interfaz = interfaz;
	}
	
	@Override
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

	public void stop() {
		stopped = true;
	}
}
