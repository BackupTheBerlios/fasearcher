package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class CalculadorBondad implements Runnable {
	protected double bondad;
	
	private boolean termino;
	
	private boolean procesando;
	
	private AFP afp;
	
	List<String> cadenasAceptadas;
	
	List<String> cadenasRechazadas;
	
	public final static int SIMPLE = 0;
	
	CountDownLatch cdl = null;
	
	static int tipo = SIMPLE;
	
	protected CalculadorBondad(AFP afp, List<String> cadenasAceptadas, List<String> cadenasRechazadas) {
		this.cadenasAceptadas = new ArrayList<String>(cadenasAceptadas);
		this.cadenasRechazadas = new ArrayList<String>(cadenasRechazadas);
		this.afp = afp;
		termino = false;
		procesando = false;
		bondad = 0;
	}
	
	public static CalculadorBondad newCalculadorBondad(AFP afp, List<String> cadenasAceptadas, List<String> cadenasRechazadas) {
		if (tipo == SIMPLE) {
			return new CalculadorBondadSimple(afp, cadenasAceptadas, cadenasRechazadas);
		}
		
		return new CalculadorBondadSimple(afp, cadenasAceptadas, cadenasRechazadas);
			
	}
	
	public static void setTipo(int nuevotipo) {
		tipo = nuevotipo;
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
			Resolver resolver = new Resolver(afp, aceptadas.next());
			resolver.run();
			actualizarBondad(resolver.getProbabilidadAceptar());		
		}
		Iterator<String> rechazadas = cadenasRechazadas.iterator();
		while(rechazadas.hasNext()) {
			Resolver resolver = new Resolver(afp, rechazadas.next());
			resolver.run();
			actualizarBondad(1 - resolver.getProbabilidadAceptar());
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
	
	public abstract void actualizarBondad(double probabilidad);

}
