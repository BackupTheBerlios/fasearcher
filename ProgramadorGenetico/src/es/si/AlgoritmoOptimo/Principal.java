package es.si.AlgoritmoOptimo;

import es.si.ProgramadorGenetico.WS.GetProblemaWS;

public class Principal {

	public static void main(String[] args) {
		Algoritmo alg = new AlgoritmoLimite();
		alg.setK(20);
		/*
		alg.addAceptada("1");
		alg.addAceptada("01");
		alg.addAceptada("10");
		alg.addAceptada("11");
		alg.addAceptada("0100");
		alg.addAceptada("010");
		alg.addAceptada("1001");
		alg.addAceptada("0001");
		alg.addAceptada("100");
		alg.addAceptada("01100");
		alg.addAceptada("11110");
		alg.addAceptada("010011");
		alg.addAceptada("001");
		alg.addAceptada("0010");
		alg.addAceptada("1001");
		
		alg.addRechazada("0");
		alg.addRechazada("00");
		alg.addRechazada("000");
		alg.addRechazada("0000");
		alg.addRechazada("00000");
*/
	
		GetProblemaWS gpws = new GetProblemaWS();
		gpws.setId("9777918"); //15 
		// Algoritmo1 -> k=20 -> 215276 ms
		// AlgoritmoLimite -> k=20 -> 845696 ms
		
		
		
//		gpws.setId("4723571"); //5
//		gpws.setId("6327041"); //6
		gpws.ejecutar();
 		for (String cadena : gpws.getAceptadas())
 			alg.addAceptada(cadena);
 		for (String cadena : gpws.getRechazadas())
 			alg.addRechazada(cadena);
 		
		alg.ejecutar();
	}
	
}
