package es.si.ProgramadorGenetico;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JFrame;

import es.si.ProgramadorGenetico.Interfaz.FramePrincipal;

public class MainPrincipal {
	
	
	private static void createAndShowGUI() {
		FramePrincipal f = new FramePrincipal("Dibujante automatas");		
		f.setLayout(new GridLayout(0,3));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
        f.pack();
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
        f.setVisible(true);
        
	}
	public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}