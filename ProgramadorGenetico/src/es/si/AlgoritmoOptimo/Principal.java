package es.si.AlgoritmoOptimo;

public class Principal {

	public static void main(String[] args) {
		Algoritmo alg = new Algoritmo();
				
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
		
		alg.ejecutar();
	}
	
}
