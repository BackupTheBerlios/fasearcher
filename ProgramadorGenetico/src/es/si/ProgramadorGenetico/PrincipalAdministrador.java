package es.si.ProgramadorGenetico;

import es.si.ProgramadorGenetico.Interfaz.InterfazAdministrador;

public class PrincipalAdministrador {
	private static void createAndShowGUI() {
		new InterfazAdministrador();		
	}
	
	public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
