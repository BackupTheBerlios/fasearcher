package es.si.ProgramadorGenetico;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import es.si.ProgramadorGenetico.Interfaz.DibujanteAFP;
import es.si.ProgramadorGenetico.Interfaz.FramePrincipal;
import es.si.ProgramadorGenetico.Interfaz.PanelTablaTransiciones;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class MainPrincipal {
	
	
	private static void createAndShowGUI() {
		FramePrincipal f = new FramePrincipal("Dibujante automatas");		
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
        f.pack();
        f.setVisible(true);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}