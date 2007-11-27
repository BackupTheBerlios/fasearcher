package es.si.ProgramadorGenetico.Interfaz;

import javax.swing.JFrame;

import es.si.ProgramadorGenetico.Individuo;

public class Interfaz  {

	Dibujante dibujante;
	Individuo mejor;
	
	public Interfaz (Individuo mejor) {		
		JFrame f = new JFrame("Dibujante automatas");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
	    f.add(new Dibujante(mejor));
	    f.pack();
	    f.setVisible(true);	  
	}
}
