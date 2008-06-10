package es.si.ProgramadorGenetico;

import es.si.ProgramadorGenetico.Interfaz.InterfazCliente;

/**
 * Clase principal para la ejecución del cliente.<p>
 * 
 * Esta clase contiene el método main que es el que se ejecuta para mostrar
 * la ventana principal de la aplicación.
 */
public class PrincipalCliente {
	private static void createAndShowGUI() {
		new InterfazCliente();		
	}
	
	public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
