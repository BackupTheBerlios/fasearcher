package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.Selector;

public class SelectorAFP implements Selector {

	static ArrayList<CalculadorBondad> calculadores;
	
	static Poblacion poblacion;
	
	static List<String> aceptadas;
	
	static List<String> rechazadas;
	
	@Override
	public Individuo mejor(Poblacion poblacionParam) {
		calcularBondades(poblacionParam);
		Iterator<CalculadorBondad> it = calculadores.iterator();
		double maxbondad = 0;
		Individuo mejor = null;
		while (it.hasNext()) {
			CalculadorBondad temp = it.next();
			if (temp.getBondad() > maxbondad) {
				maxbondad = temp.getBondad();
				mejor = (Individuo) temp.getAFP();
			}
		}
		System.out.println("Maxima bondad: " + maxbondad);
		return mejor;
	}

	@Override
	public Poblacion seleccionar(int cantidad, Poblacion poblacionParam) {
		calcularBondades(poblacionParam);
		double[] bondades = new double[cantidad];
		for (int i = 0; i < cantidad; i++) bondades[i] = 0;
		AFP[] afps = new AFP[cantidad];
		
		Iterator<CalculadorBondad> it = calculadores.iterator();
		while (it.hasNext()) {
			CalculadorBondad temp = it.next();
			for (int i = 0; i < cantidad; i++){
				if (bondades[i] < temp.getBondad()) {
					if (i == cantidad - 1) {
						for (int j = 0; j < cantidad -1; j++) {
							bondades[j] = bondades[j+1];
							afps[j] = afps[j+1];
						}
						bondades[i] = temp.getBondad();
						afps[i] = temp.getAFP();
					}
				}
				else if (i > 0) {
					bondades[i - 1] = temp.getBondad();
					afps[i-1] = temp.getAFP();
					break;
				}
				else
					break;
			}
		}
		Poblacion pob = new Poblacion();
		for (int i = 0; i < cantidad; i++) {
			if (afps[i] != null)
				pob.agregarMiembro(afps[i]);
		}
		
		return pob;
	}
	
	/**
	 * Método que calcula la bondad para cada uno de los integrantes de la poblacion<p>
	 * Este método calcula las bondades para todos los miembros de una población de afps.<br>
	 * Está preparado para ejecutarse solo cuando cambio la población
	 * o las cadenas aceptadas y rechazadas.<br>
	 * Además, esta listo para poder implementarse
	 * de tal forma que se ejecute cada calculo de la bondad en paralelo. Para esto se
	 * debe ejecutar cada <b>CalculadorBondad</b> como un thread diferente y se recomienda
	 * usar la clase de java <i>java.util.concurrent.CountDownLatch</i> para la sincronización.
	 * 
	 * @param poblacionParam
	 */
	private void calcularBondades(Poblacion poblacionParam) {
		if (calculadores == null || poblacionParam != poblacion
				|| aceptadas != ParametrosAFP.getInstance().getAceptadas()
				|| rechazadas != ParametrosAFP.getInstance().getAceptadas()) {
			poblacion = poblacionParam;
			calculadores = new ArrayList<CalculadorBondad>();
			aceptadas = ParametrosAFP.getInstance().getAceptadas();
			rechazadas = ParametrosAFP.getInstance().getRechazadas();
			Iterator<Individuo> afps = poblacion.getIterator();
			CountDownLatch cdl = new CountDownLatch(poblacion.getCantidad());
			while (afps.hasNext()) {
				CalculadorBondad calculador = CalculadorBondad.newCalculadorBondad((AFP) afps.next(),aceptadas, rechazadas);
				calculadores.add(calculador);
				calculador.setCountDownLatch(cdl);
				Thread thread = new Thread(calculador);
				thread.start();
				//calculador.run();
			}
			
			try {
				cdl.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
