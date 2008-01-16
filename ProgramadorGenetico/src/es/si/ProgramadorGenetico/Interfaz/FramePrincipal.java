package es.si.ProgramadorGenetico.Interfaz;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;


public class FramePrincipal extends JFrame {

	//JMenu menuPrincipal;
	static boolean test;
	private static int estados;
	private static double[][][] transiciones;
	private static double[] probabilidadFinal;
	
	public FramePrincipal (String s) {
		super(s);
		//menuPrincipal = new JMenu();
	    //Where the GUI is created:
		
		test= true;
		
	    JMenuBar menuBar;
	    JMenu menu, submenu;
	    JMenuItem menuItem;
	    JRadioButtonMenuItem rbMenuItem;
	    JCheckBoxMenuItem cbMenuItem;

	    //Create the menu bar.
	    menuBar = new JMenuBar();

	    //Build the first menu.
	    menu = new JMenu("Archivo");
	    menu.setMnemonic(KeyEvent.VK_A);
	    menu.getAccessibleContext().setAccessibleDescription(
	            "Menu archivo");
	    menuBar.add(menu);

	    //Menu Items
	    menuItem = new JMenuItem("Nueva configuración",
	                             KeyEvent.VK_N);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_N, ActionEvent.ALT_MASK));
	    menuItem.getAccessibleContext().setAccessibleDescription(
	            "Crea una nueva configuración");
	    menu.add(menuItem);

	    JMenuItem menuItem2 = new JMenuItem("Cargar configuración",KeyEvent.VK_C);
	    menuItem2.setMnemonic(KeyEvent.VK_C);
	    menuItem2.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_C, ActionEvent.ALT_MASK));
	    menu.add(menuItem2);
	    
	    menu.addSeparator();
	    JMenuItem menuItem3 = new JMenuItem("Salir", KeyEvent.VK_S);
	    menuItem3.setMnemonic(KeyEvent.VK_S);
	    menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
	    menu.add(menuItem3);
    
	    menu = new JMenu("Opciones");
	    menu.setMnemonic(KeyEvent.VK_O);
	    menu.getAccessibleContext().setAccessibleDescription(
	            "Menu de opciones");
	    menuBar.add(menu);
	    
	    this.setJMenuBar(menuBar);
	    
	    PanelNuevo panelNuevo = new PanelNuevo();
	    this.add(panelNuevo);
	}
	public static void main (String[] args) {	
				
		FramePrincipal f = new FramePrincipal("Dibujante automatas");
		creaAFP();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AFP mejor = creaAFP();
        f.add(new DibujanteNuevo(mejor));
        f.add(new PanelTablaTransiciones(transiciones, probabilidadFinal, estados));
        f.pack();
        f.setVisible(true);	   	   	   	
	}
	
	public static void setValoresEntrada() {		
		estados = 4;
		transiciones= new double[estados][2][estados+1];
		transiciones[0][1][0]=1.0;
		transiciones[1][1][0]=0.4;
		transiciones[2][1][0]=1.0;
		transiciones[2][0][0]=1.0;
		transiciones[3][1][0]=1.0;
		transiciones[1][0][2]=0.9;
		transiciones[2][1][2]=0.3;		
			
	}
	
	public static AFP creaAFP () {
		AFP mejor;
		if (test) {
			setValoresEntrada();
			mejor = new AFP (estados);
			mejor.setTransiciones(transiciones);
			mejor.setProbabilidadFinal(probabilidadFinal);
		}
		else {
			// TODO
			return null;
			
			/*
			mejor = ...
			estados = mejor.getEstados();
			transiciones = mejor.getTransiciones();
			probabilidadFinal = mejor.getProbabilidadesFinal();
			*/
		}
		return mejor;
	}
}
