package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;

public abstract class CalculadorBondad implements Runnable {
	protected double bondad;
	
	private boolean termino;
	
	private boolean procesando;
	
	private AFP afp;
	
	protected List<String> cadenasAceptadas;
	
	protected List<String> cadenasRechazadas;
	
	CountDownLatch cdl = null;
	
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
	
	public abstract void actualizarBondadAceptada(double probabilidad);
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
