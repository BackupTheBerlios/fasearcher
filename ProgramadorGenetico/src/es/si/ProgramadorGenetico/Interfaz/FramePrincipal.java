package es.si.ProgramadorGenetico.Interfaz;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

//import com.sun.media.sound.Toolkit;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Principal;


public class FramePrincipal extends JFrame implements ActionListener {

	//JMenu menuPrincipal;
	static boolean test;
	private static int estados;
	private static double[][][] transiciones;
	private static double[] probabilidadFinal;
	
	private DibujanteNuevo dibujante;
	private PanelTablaTransiciones tablaTransiciones;
	
	public FramePrincipal (String s) {
		super(s);		
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		test= false;
	
	    JMenuBar menuBar;
	    JMenu menu;
	    JMenuItem menuItem;
	   
	    //Crea la barra de menu
	    
	    menuBar = new JMenuBar();
	    
	    //Primer menu
	    
	    menu = new JMenu("Archivo");
	    menu.setMnemonic(KeyEvent.VK_A);
	    menu.getAccessibleContext().setAccessibleDescription(
	            "Menu archivo");
	    menuBar.add(menu);
	    
	    //Menu Items
	    
	    /*
	    menuItem = new JMenuItem("Nueva configuración",
	                             KeyEvent.VK_N);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_N, ActionEvent.ALT_MASK));
	    menuItem.getAccessibleContext().setAccessibleDescription(
	            "Crea una nueva configuración");
	    menu.add(menuItem);

	    menuItem = new JMenuItem("Cargar configuración",KeyEvent.VK_C);
	    menuItem.setMnemonic(KeyEvent.VK_C);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(
	            KeyEvent.VK_C, ActionEvent.ALT_MASK));
	    menu.add(menuItem);
	    */
	    
	    menuItem = new JMenuItem("Simular",KeyEvent.VK_I);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
	    menuItem.getAccessibleContext().setAccessibleDescription("Simula la configuración");
	    menu.add(menuItem);
	    
	    menuItem.addActionListener(this);
	    
	    menu.addSeparator();
	    menuItem = new JMenuItem("Salir", KeyEvent.VK_S);
	    menuItem.setMnemonic(KeyEvent.VK_S);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
	    menu.add(menuItem);
	    
	    //Segundo menú
	    
	    menu = new JMenu("Opciones");
	    menu.setMnemonic(KeyEvent.VK_O);
	    menu.getAccessibleContext().setAccessibleDescription(
	            "Menu de opciones");
	    menuBar.add(menu);
	    
	    menuItem = new JMenuItem("Cambiar representación", KeyEvent.VK_C);
	    menuItem.setMnemonic(KeyEvent.VK_C);
	    menuItem.setAccelerator(null);
	    menu.add(menuItem);
	    	    
	    this.setJMenuBar(menuBar);
	    	   
	    	    
	    /*
	    PanelNuevo panelNuevo = new PanelNuevo();
	    this.add(panelNuevo);
	    */
	    
	}
	
	/*
	public static void main (String[] args) {	
				
		FramePrincipal f = new FramePrincipal("Dibujante automatas");
		creaAFP();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AFP mejor = creaAFP();
		DibujanteNuevo dibujante = new DibujanteNuevo(mejor);
		//JScrollPane panelDibujante = new JScrollPane(dibujante,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 
		JScrollPane scrollPrincipal = new JScrollPane(dibujante,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		scrollPrincipal.setViewportView(dibujante);
		scrollPrincipal.setBounds
		(new Rectangle(350, 5, 400, 400)); 
		f.add(dibujante);
		//panelDibujante.setVerticalScrollBarPolicy(JScrollBar.VERTICAL_SCROLLBAR_ALWAYS);
        //f.add(panelDibujante);
        //f.add(new PanelTablaTransiciones(transiciones, probabilidadFinal, estados));
        f.pack();
        f.setVisible(true);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);        
	}
	*/
	
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
		probabilidadFinal = new double[estados];
		probabilidadFinal[0]=0.0;
		probabilidadFinal[1]=0.0;
		probabilidadFinal[2]=1.0;
		probabilidadFinal[3]=1.0;		
		
			
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
						
			Principal.main(null);
			mejor = Principal.getMejor();					
			/*
			mejor = ...
			estados = mejor.getEstados();
			transiciones = mejor.getTransiciones();
			probabilidadFinal = mejor.getProbabilidadesFinal();
			*/
		}
		return mejor;
	}
	
	public void actionPerformed (ActionEvent e) {

		
		AFP mejor = creaAFP();
		dibujante = new DibujanteNuevo(mejor);
		//JScrollPane scrollPrincipal = new JScrollPane(dibujante);
		//,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrollPrincipal.setViewportView(dibujante);
		add(dibujante);
		//add(scrollPrincipal);
		//scrollPrincipal.setBounds(new Rectangle(350, 5, 400, 400)); 
		System.out.println("Dentro");
		
		
		//TODO
		//tablaTransiciones = new PanelTablaTransiciones(transiciones, probabilidadFinal, estados);
	    //add(tablaTransiciones);
		
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		//super.paintComponents(g);			
		dibujante.repaint();
		//tablaTransiciones.repaint();
	}
	/*
	public void paint(Graphics g) {
		super.paint(g);
		dibujante.repaint();
	}
	*/
}
