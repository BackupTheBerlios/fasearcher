package es.si.ProgramadorGenetico.WS.Pruebas;

import es.si.ProgramadorGenetico.WS.GetProblemaBasicWS;

public class PruebaWS2 {

	public static void main(String[] args) {
		GetProblemaBasicWS gpWS = new GetProblemaBasicWS();
		gpWS.ejecutar();
		
		System.out.println(gpWS.getId());
		System.out.println(gpWS.getAceptadas());
		System.out.println(gpWS.getRechazadas());
	}
}
