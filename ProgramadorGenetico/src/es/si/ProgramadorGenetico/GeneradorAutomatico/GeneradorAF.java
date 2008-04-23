package es.si.ProgramadorGenetico.GeneradorAutomatico;

import java.util.List;
import java.util.ArrayList;

import es.si.ProgramadorGenetico.Interfaz.componentes.AF;

public class GeneradorAF {

	private class Pareja {
		
		int []trans;		
		
		public Pareja(int val0, int val1) {
			trans = new int[2];
			trans[0]=val0;
			trans[1]=val1;
		}
		
		public void setTrans (int valor) { 
			trans[valor]=1;					
		}
		
		public int getTrans (int valor) {
			return trans[valor];
		}
	};
	AF aut;
	List<Pareja> transicionesExistentes;
	
	public GeneradorAF (int numEstados) {
		aut = new AF(numEstados);		
		generarAutomata();
	}
	
	public void generarAutomata () {
		transicionesExistentes = new ArrayList<Pareja>();
		for (int i=0; i<aut.getEstados()-1; i++) {
			int val = (int)(2*Math.random());
			aut.setTransicion(i, val , i+1);
			if (val==0)
				transicionesExistentes.add(new Pareja(1,0));
			else if (val==1)
				transicionesExistentes.add(new Pareja(0,1));
		}
		
		for (int i=0; i<aut.getEstados()-1; i++) {
			if (transicionesExistentes.get(i).getTrans(0)==0) {
				int estadoDestino = (int)(aut.getEstados()*Math.random());				
				transicionesExistentes.get(i).setTrans(0);
				aut.setTransicion(i,0,estadoDestino);		
			}
			if (transicionesExistentes.get(i).getTrans(1)==0) {
				int estadoDestino = (int)(aut.getEstados()*Math.random());
				transicionesExistentes.get(i).setTrans(1);
				aut.setTransicion(i,1,estadoDestino);				
			}									
		}
		
		int estadoDestino = (int)(aut.getEstados()*Math.random());				
		transicionesExistentes.add(new Pareja(1,0));
		aut.setTransicion(aut.getEstados()-1,0,estadoDestino);
		estadoDestino = (int)(aut.getEstados()*Math.random());				
		transicionesExistentes.get(aut.getEstados()-1).setTrans(1);
		aut.setTransicion(aut.getEstados()-1,1,estadoDestino);
		
		//Minimo un estado final
		int estadoFinalSeguro = (int)(((aut.getEstados())*Math.random()));
		aut.setFinal(estadoFinalSeguro);
		//Probabildad de que un estado sea final entre 0% y 20%
		int probabilidadFinal = (int)(Math.random()*20);
		for (int i=0; i<aut.getEstados(); i++) {			
			int prob = (int)(100*(Math.random()));
			if (prob <= probabilidadFinal)
				aut.setFinal(i);							
		}		
	}
	
	public AF getAF () {
		return aut;
	}
}
