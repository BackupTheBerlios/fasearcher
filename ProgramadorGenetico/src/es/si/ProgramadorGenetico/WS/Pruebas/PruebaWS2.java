package es.si.ProgramadorGenetico.WS.Pruebas;

import es.si.ProgramadorGenetico.WS.GetProblemaWS;

public class PruebaWS2 {

	public static void main(String[] args) {
		GetProblemaWS gpWS = new GetProblemaWS();
		gpWS.ejecutar();
		
		System.out.println(gpWS.getId());
		System.out.println(gpWS.getAceptadas());
		System.out.println(gpWS.getRechazadas());
		
		System.out.println(gpWS.getClass().getResource("resources/config.properties"));

	}
}
