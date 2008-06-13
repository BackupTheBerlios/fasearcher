package es.si.ProgramadorGenetico.ProblemaAFP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.Poblacion;
import es.si.ProgramadorGenetico.Selector;
import es.si.ProgramadorGenetico.Writer;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
/**
 * Clase que implementa el Selector del algoritmo genetico
 * Selecciona los AFPs teniendo un calculador de bondad,
 * cadenas aceptadas y rechazadas y una poblacion de 
 * donde elegir los AFPs
 *
 */
public class SelectorAFP implements Selector {
	/**
	 * Calculadores de bondad
	 */
	static ArrayList<CalculadorBondad> calculadores;
	/**
	 * Poblacion de AFPs
	 */
	static Poblacion poblacion;
	/**
	 * Lista de cadenas aceptadas
	 */
	static List<String> aceptadas;
	/**
	 * Lista de cadenas rechazadas
	 */
	static List<String> rechazadas;
	
	/**
	 * Devuelve el mejor individuo de la poblacion
	 */
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
		//System.out.println("Maxima bondad: " + maxbondad);
		Writer.write(""+maxbondad);
		return mejor;
	}

	/**
	 * Selecciona la poblacion escogida a partir de una poblacion
	 * que se pasa por parametro 
	 */
	public Poblacion seleccionar(int cantidad, Poblacion poblacionParam) {
		calcularBondades(poblacionParam);
		double[] bondades = new double[cantidad];
		for (int i = 0; i < cantidad; i++) bondades[i] = 0;
		AFP[] afps = new AFP[cantidad];
		
		for (CalculadorBondad temp : calculadores) {
			for (int i = 0; i < cantidad; i++){
				if (bondades[i] < temp.getBondad()) {
					for (int j = i; j < cantidad -1; j++) {
						bondades[j] = bondades[j+1];
						afps[j] = afps[j+1];
					}
					bondades[i] = temp.getBondad();
					afps[i] = temp.getAFP();
					break;
				}
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
			while (afps.hasNext()) {
				CalculadorBondad calculador = CalculadorBondadAFPFactory.getCalculadorBondadAFP((AFP) afps.next(),aceptadas, rechazadas);
				calculadores.add(calculador);
				calculador.run();
			}
		}

	}

}
