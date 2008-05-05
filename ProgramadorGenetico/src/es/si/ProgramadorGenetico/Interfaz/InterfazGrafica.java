package es.si.ProgramadorGenetico.Interfaz;

import java.util.List;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public interface InterfazGrafica {
	
	public void terminoResolver(boolean correcto, List<AFP> mejores);
	
	public void terminoResolverMuchos(boolean correcto, AFP mejor);
	
}
