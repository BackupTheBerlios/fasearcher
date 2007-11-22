package es.si.ProgramadorGenetico.ProblemaAFP.Pruebas;

import es.si.ProgramadorGenetico.ProblemaAFP.GeneradorAleatorioAFP;

public class PruebaGeneradorAleatorio {
	public static void main(String[] args) {
		for (int i = 1; i < 10; i++) {
			System.out.print(GeneradorAleatorioAFP.nuevo(5));
			System.out.print("*****************************************\n");
		}
	}
	
}
