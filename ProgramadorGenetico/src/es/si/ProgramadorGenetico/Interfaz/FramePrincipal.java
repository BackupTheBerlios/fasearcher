package es.si.ProgramadorGenetico.Interfaz;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.awt.Dialog; 

//import com.sun.media.sound.Toolkit;

import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Principal;


public class FramePrincipal extends JFrame implements ActionListener {

	//JMenu menuPrincipal;
	static boolean test;
	private static int estados;
	private static double[][][] transiciones;
	private static double[] probabilidadFinal;
	
	private PanelTablaTransiciones tablaTransiciones;
	private JScrollPane scrollPrincipal;
	private JPanel panelScroll;
	private DibujanteAFP dibujanteAFP;
	private DibujanteAF dibujanteAF;

	public FramePrincipal (String s) {
		super(s);		
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);		
		test= false;		   
		crearMenus();    	    	  

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
		transiciones[0][0][0]=0.7;
		transiciones[0][1][0]=0.5;
		transiciones[1][1][0]=0.4;
		transiciones[2][1][0]=1.0;
		transiciones[2][0][0]=0.5;
		transiciones[3][0][0]=0.8;
		transiciones[3][1][0]=0.2;
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

		JMenuItem m = ((JMenuItem)(e.getSource()));
		if (m.getName()=="Genetico") {
			dibujanteAF=null;
			AFP mejor = creaAFP();
			dibujanteAFP = new DibujanteAFP(mejor);
			panelScroll = new JPanel();
			panelScroll.setPreferredSize(new Dimension(1000,1000));
			panelScroll.add(dibujanteAFP);
			scrollPrincipal = new JScrollPane(panelScroll);
			panelScroll.scrollRectToVisible(new Rectangle(0,0,2000,2000));
			this.add(scrollPrincipal);
			pintar();
			//scrollPrincipal = new JScrollPane(dibujante);
			//,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			//dibujante.scrollRectToVisible(new Rectangle(0,0,2000,2000));
			//add(dibujante);
			//add(scrollPrincipal);
			//scrollPrincipal.setBounds(new Rectangle(350, 5, 400, 400));
			//scrollPrincipal.setViewportView(dibujante);
			//add(dibujante);
			//TODO
			//tablaTransiciones = new PanelTablaTransiciones(transiciones, probabilidadFinal, estados);
			//add(tablaTransiciones);
			//this.update(this.getGraphics());
		}

	}

	public void crearMenus () {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		//Crea la barra de menu

		menuBar = new JMenuBar();

		//Primer menu

		menu = new JMenu("Acciones");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"Menu de acciones");
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


		menuItem = new JMenuItem("Algoritmo genético",KeyEvent.VK_A);
		menuItem.setName("Genetico");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Simula la configuración");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("Generar autómata automáticamente",KeyEvent.VK_G);
		menuItem.setName("Automata");		
		menuItem.getAccessibleContext().setAccessibleDescription("Genera un autómata");
		menu.add(menuItem);
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dibujanteAFP = null;
				dibujanteAF = null;				
				String s = (String)JOptionPane.showInputDialog(
						"Introduzca el número de estados del automáta a generar",
				"8");
				int numEstados = Integer.valueOf(s);
				System.out.println("Num estados: "+numEstados);
				GeneradorAF genAF = new GeneradorAF(numEstados);
				dibujanteAF = new DibujanteAF (genAF.getAF());
				
				
				panelScroll = new JPanel();
				panelScroll.setPreferredSize(new Dimension(1000,1000));
				panelScroll.add(dibujanteAF);
				scrollPrincipal = new JScrollPane(panelScroll);
				panelScroll.scrollRectToVisible(new Rectangle(0,0,2000,2000));
				agregaPanel(scrollPrincipal);
				pintar();
				s = "¿Desea ver cadenas posibles de este automata?";
				int respuesta = mostrarMensajeConfirmacion(s);
				if (respuesta == JOptionPane.YES_OPTION) {
					GeneradorCadenas genCad = new GeneradorCadenas(dibujanteAF.getAutomata());
					String message = genCad.toString();
					mostrarMensaje(message);
				}												
			}

		});

		menuItem = new JMenuItem("Obtener cadenas del AF",KeyEvent.VK_O);
		menuItem.setName("Cadenas");		
		menuItem.getAccessibleContext().setAccessibleDescription("Obtiene cadenas");
		menu.add(menuItem);			
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GeneradorCadenas genCad = new GeneradorCadenas(dibujanteAF.getAutomata());
				String message = genCad.toString();
				mostrarMensaje(message);				
			}

		});

		
		menu.addSeparator();
		menuItem = new JMenuItem("Salir", KeyEvent.VK_S);
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		//Segundo menú

		menu = new JMenu("Opciones");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext().setAccessibleDescription(
				"Menu de opciones");
		menuBar.add(menu);
		this.setJMenuBar(menuBar);


		/*
        	    PanelNuevo panelNuevo = new PanelNuevo();
        	    this.add(panelNuevo);
		 */
	}

	public void pintar() {
		this.paintComponent(this.getGraphics());
	}

	public void agregaPanel(JScrollPane panel) {
		this.add(panel);		
	}

	public void paintComponent(Graphics g) {		
		super.paintComponents(g);
	}
	
	public void mostrarMensaje (String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}
	
	public int mostrarMensajeConfirmacion (String mensaje) {
		int i = JOptionPane.showConfirmDialog(this,mensaje);
		return i; 
	}


}
