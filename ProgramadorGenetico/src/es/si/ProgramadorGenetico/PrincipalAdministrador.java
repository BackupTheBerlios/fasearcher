package es.si.ProgramadorGenetico;

import es.si.ProgramadorGenetico.Interfaz.InterfazAdministrador;

/**
 * Clase principal para la ejecución del administrador.<p>
 * 
 * Esta clase contiene el método main que es el que se ejecuta para mostrar
 * la ventana principal de la aplicación.
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
