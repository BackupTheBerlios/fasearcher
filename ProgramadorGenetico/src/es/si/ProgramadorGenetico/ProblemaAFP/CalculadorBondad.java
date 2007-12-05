package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadBalanceado;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadCuadratico;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadPrefernciaDet;
import es.si.ProgramadorGenetico.ProblemaAFP.CalculadoresBondad.CalculadorBondadSimple;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;

public abstract class CalculadorBondad implements Runnable {
	protected double bondad;
	
	private boolean termino;
	
	private boolean procesando;
	
	private AFP afp;
	
	protected List<String> cadenasAceptadas;
	
	protected List<String> cadenasRechazadas;
	
	public final static int SIMPLE = 0;
	
	public final static int CUADRATICO = 1;
	
	public final static int BALANACEADO = 2;
	
	public final static int PREFERNCIADET = 3;
	
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
		} else if (tipo == CUADRATICO) {
			return new CalculadorBondadCuadratico(afp, cadenasAceptadas, cadenasRechazadas);
		} else if (tipo == BALANACEADO) {
			return new CalculadorBondadBalanceado(afp, cadenasAceptadas, cadenasRechazadas);
		} else if (tipo == PREFERNCIADET) {
			return new CalculadorBondadPrefernciaDet(afp, cadenasAceptadas, cadenasRechazadas);
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

}
