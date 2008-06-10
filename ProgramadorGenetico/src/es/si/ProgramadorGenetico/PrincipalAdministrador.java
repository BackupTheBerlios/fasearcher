package es.si.ProgramadorGenetico;

import es.si.ProgramadorGenetico.Interfaz.InterfazAdministrador;

/**
 * Clase principal para la ejecuci�n del administrador.<p>
 * 
 * Esta clase contiene el m�todo main que es el que se ejecuta para mostrar
 * la ventana principal de la aplicaci�n.
 */
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
