package es.si.ProgramadorGenetico.ProblemaAFP;

import es.si.ProgramadorGenetico.Algoritmo;

public class AplicarAlgortimoAFP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Algoritmo alg = new Algoritmo();
		alg.setPoblacioninicial(new AFPIniciales());
		alg.setReproductor(new ReproductorAFP());
		alg.setSelector(new SelectorAFP());

		CalculadorBondadSimple calc = new CalculadorBondadSimple((AFP)alg.run(4), ParametrosAFP.getInstance().getAceptadas(), ParametrosAFP.getInstance().getRechazadas());
		System.out.println("Bondad final: " + calc.getBondad());
	}

}
