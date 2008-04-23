package es.si.ProgramadorGenetico.GeneradorAutomatico.Pruebas;

import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.Interfaz.componentes.AF;

public class PruebaGeneradorAF {
	private static int estados;
	
	public static void main (String[] args) {
		inicializar();	
	}
	
	public static void inicializar () {		
		setValoresEntrada();				
	}
	
	public static void setValoresEntrada() {		
		estados = 4;
		AF af1 = new AF(estados);
		af1.setTransicion(0,0,1);
		af1.setTransicion(0,1,2);
		af1.setTransicion(1,0,1);
		af1.setTransicion(1,1,2);
		af1.setTransicion(2,0,3);
		af1.setTransicion(2,1,3);
		af1.setTransicion(2,0,3);
		af1.setTransicion(2,1,3);
		af1.setTransicion(3,0,3);
		af1.setTransicion(3,1,3);
		af1.setFinal(0);
		af1.setFinal(2);
		af1.setFinal(3);		
		GeneradorCadenas genCad = new GeneradorCadenas (af1);
		System.out.println(genCad.toString());		
	}
}
