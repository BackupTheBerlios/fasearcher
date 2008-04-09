package es.si.ProgramadorGenetico.ProblemaAFP.Resolutores;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ResolverAFP;

/**
 * Clase que dado un automata y una cadena determina la probabilidad de que
 * esta sea reconocida.<p>
 * 
 * Esta clase toma como parámetros en su constructor el automata finito
 * probabilista y la cadena a reconocer. Tiene un método run que se puede
 * ejecutar en un thread independiente si se quiere y que guarda en la
 * variable <b>probabilidadAceptar</b> la probabilidad de que la cadena
 * sea aceptada una vez que termina con el procesamiento.<br>
 * Además tiene un flag <b>procesando</b> para indicar si esta trabajando y un
 * flag <b>termino</b> para indicar cuando se terminó el procesamiento.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public class ResolverAFPVectores implements ResolverAFP {

	public static final double VERSION = 1.0f;

	private AFP af;
	
	private String cadenaInicial;
	
	private boolean reconoce;
	
	private double[] probabilidades;
	
	private double[] probstemp;
	
	private double probabilidadAceptar;
	
	private boolean procesando;
	
	private boolean termino;
	
	private double[][][] trans;
	
	private int estados;
	
	public ResolverAFPVectores() {
		this.reconoce = false;
		procesando = false;
		termino = false;
	}
	
	
	@Override
	public void run() {
		if (this.termino) return;
		if (af == null) return;
		this.procesando = true;

		String cadena = cadenaInicial;
		
		probabilidades[0] = 1;
		for (int i = 1; i < estados; i ++) {
			probabilidades[i] = 0;
		}
		
		int simbolo;
		
		while(cadena!=null && !cadena.equals("")) {
			simbolo = obtenerSimbolo(cadena);
			cadena = actualizarCadena(cadena);
			for (int i = 0; i < estados; i++) {
				for (int j = 0; j < estados; j++) {
					if (i == 0) probstemp[j] = 0;
					probstemp[j] += probabilidades[i]*trans[i][simbolo][j+1];
				}
			}
			// posiblemente se puede usar System.arrayCopy() pero no es bueno para
			// arrays pequeños
			for (int i = 0; i < estados; i++)
				probabilidades[i] = probstemp[i];
		}
		
		probabilidadAceptar = 0;
		for (int i = 0; i < estados; i++) {
			probabilidadAceptar += probabilidades[i]*af.getProbabilidadFinal(i+1);
		}
		
		this.procesando = false;
		termino = true;
	}
	
	private int obtenerSimbolo(String cadena) {
		if (cadena == null) return -1;
		int valor = (int) (cadena.charAt(0) - '0');
		//if (valor == 0 || valor == 1)
			return valor;
		//return -1;
	}
	
	private String actualizarCadena(String cadena) {
		if (cadena.length() == 1) return null;
		return cadena.substring(1);
	}
	
	public boolean getReconoce() {
		return reconoce;
	}
	
	public double getProbabilidadAceptar() {
		return probabilidadAceptar;
	}
	
	public boolean getProcesando() {
		return procesando;
	}
	
	public boolean getTermino() {
		return termino;
	}


	public void setAFP(AFP afp) {
		this.af = afp;
		this.probabilidades = new double[af.getEstados()];
		this.trans = af.getTransiciones();
		this.estados = af.getEstados();
		this.probstemp = new double[estados];
	}


	public void setCadena(String cadena) {
		this.cadenaInicial = new String(cadena);
	}

}
