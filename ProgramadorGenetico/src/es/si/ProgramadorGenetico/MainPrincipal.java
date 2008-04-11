package es.si.ProgramadorGenetico;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
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