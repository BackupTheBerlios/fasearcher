package es.si.ProgramadorGenetico.ProblemaAFP.Factorias;

import es.si.ProgramadorGenetico.ProblemaAFP.ResolverAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Resolutores.ResolverAFPPila;
import es.si.ProgramadorGenetico.ProblemaAFP.Resolutores.ResolverAFPVectores;

public class ResolverAFPFactory {
	
	public static final int VECTORES = 0;
	
	public static final int PILA = 1;
	
	private static int tipo = VECTORES;
	
	public static ResolverAFP getResolverAFP() {
		if (tipo == VECTORES) {
			return new ResolverAFPVectores();
		}
		if (tipo == PILA) {
			return new ResolverAFPPila();
		}
		return new ResolverAFPVectores();
	}
	
	public static void setTipo(int tipo_nuevo) {
		tipo = tipo_nuevo;
	}
	
	public static String getVersion() {
		if (tipo == VECTORES) {
			return "VECTORES " + ResolverAFPVectores.VERSION;
		}
		if (tipo == PILA) {
			return "PILA " + ResolverAFPPila.VERSION;
		}
		return "VECTORES " + ResolverAFPVectores.VERSION;
		
	}
}
