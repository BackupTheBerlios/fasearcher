package es.si.ProgramadorGenetico.ProblemaAFP;

public interface ResolverAFP extends Runnable {
	
	public void setAFP(AFP afp);
	
	public void setCadena(String cadena);
	
	public boolean getReconoce();
	
	public double getProbabilidadAceptar();
	
	public boolean getProcesando();
	
	public boolean getTermino();
	
}
