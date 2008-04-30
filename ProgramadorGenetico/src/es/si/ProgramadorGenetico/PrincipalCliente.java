package es.si.ProgramadorGenetico;

import es.si.ProgramadorGenetico.Interfaz.InterfazCliente;

public class PrincipalCliente {
	private static void createAndShowGUI() {
		InterfazCliente f = new InterfazCliente();		
	}
	
	public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
