package es.si.ProgramadorGenetico;

import es.si.ProgramadorGenetico.Interfaz.InterfazCliente;

/**
 * Clase principal para la ejecuci�n del cliente.<p>
 * 
 * Esta clase contiene el m�todo main que es el que se ejecuta para mostrar
 * la ventana principal de la aplicaci�n.
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
