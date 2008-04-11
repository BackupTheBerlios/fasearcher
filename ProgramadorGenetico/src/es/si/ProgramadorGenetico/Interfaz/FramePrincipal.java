package es.si.ProgramadorGenetico.Interfaz;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


import javax.swing.*;

import java.awt.Dialog; 
import java.util.List;

//import com.sun.media.sound.Toolkit;

import es.si.ProgramadorGenetico.GeneradorAutomatico.AF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Principal;
import es.si.ProgramadorGenetico.WS.AddProblemaWS;

public class FramePrincipal extends JFrame implements ActionListener {

	//JMenu menuPrincipal;
	static boolean test;
	private static int estados;
	private static double[][][] transiciones;
	private static double[] probabilidadFinal;
	private PanelTablaTransiciones tablaTransiciones;
	private JPanel panelPrincipal;
	private DibujanteAFP dibujanteAFP;
	private DibujanteAF dibujanteAF;
	private PanelDatos tablaDatos;
	private boolean dibujar;
	private int diametroEstado = 40;
	private Estado estadoInicioTransicion;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private AF afGenetico;
	private GeneradorCadenas genCadenas;
	private JScrollPane scrollPaneAF;
	private JScrollPane scrollPaneAFP;

	public FramePrincipal (String s) {
		super(s);		
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//this.setLayout(new GridLayout());
		//this.getContentPane().setLayout(new BorderLayout());
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

	public static AFP creaAFPdeAF (List<String> aceptadas, List<String> rechazadas, int numEstados) {
		//Principal.ejecucionGenetico(aceptadas, rechazadas);		
		Principal.ejecuta2(aceptadas, rechazadas,numEstados);
		return Principal.getMejor();
	}
	/*
	public void actionPerformed (ActionEvent e) {

		JMenuItem m = ((JMenuItem)(e.getSource()));
		if (m.getName()=="Genetico") {
			dibujanteAF=null;
			AFP mejor = creaAFP();
			dibujanteAFP = new DibujanteAFP(mejor);
			panelPrincipal = new JPanel();
			panelPrincipal.setPreferredSize(new Dimension(1000,1000));
			panelPrincipal.add(dibujanteAFP);			
			this.add(panelPrincipal);
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
	*/

	public void crearMenus () {

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

		/*

		menuItem = new JMenuItem("Algoritmo genético",KeyEvent.VK_A);
		menuItem.setName("Genetico");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Simula la configuración");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
					borraDibujos();
					AFP mejor = creaAFP();
					dibujanteAFP = new DibujanteAFP(mejor);			
					agregaPanelAFP(dibujanteAFP);
					activarMenusDibujo(false);
					pintar();				
			}
		});
		
		*/
		
		menuItem = new JMenuItem("Algoritmo genético a partir del autómata finito",KeyEvent.VK_L);
		menuItem.setName("Genetico");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Simula la configuración");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {										
			
					afGenetico = new AF(dibujanteAF.getEstados(),dibujanteAF.getTransiciones2());
					boolean esAFD = afGenetico.comprobarAFD();
					if (!esAFD) {												
						 JOptionPane.showMessageDialog(null,"Falta completar transiciones en el automata", "",
								 JOptionPane.ERROR_MESSAGE);
					}
					else {	
						genCadenas = new GeneradorCadenas(afGenetico);
						AFP mejor = creaAFPdeAF(genCadenas.getCadenasAceptadas(),
								genCadenas.getCadenasRechazadas(),
								afGenetico.getEstados());					
						borraDibujos();
						dibujanteAFP = new DibujanteAFP (mejor);
						agregaPanelAFP(dibujanteAFP);
						activarMenusDibujo(false);
					}
					pintar();
			}
		});
		

		menuItem = new JMenuItem("Obtener cadenas del AF",KeyEvent.VK_O);
		menuItem.setName("Cadenas");		
		menuItem.getAccessibleContext().setAccessibleDescription("Obtiene cadenas");
		menu.add(menuItem);			
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (dibujanteAF!=null) {
					if (dibujanteAF.getAutomata()==null) { 
						afGenetico = new AF(dibujanteAF.getEstados(), dibujanteAF.getTransiciones2());									 

						boolean esAFD = afGenetico.comprobarAFD();
						if (!esAFD) {												
							JOptionPane.showMessageDialog(null,"Falta completar transiciones en el automata", "",
									JOptionPane.ERROR_MESSAGE);
						}
						else {				
							GeneradorCadenas genCad = new GeneradorCadenas(afGenetico);
							String message = genCad.toString();
							mostrarMensaje(message);
							activarMenusDibujo(false);
						}
					}
					else {
						GeneradorCadenas genCad = new GeneradorCadenas(dibujanteAF.getAutomata());
						String message = genCad.toString();
						mostrarMensaje(message);
						activarMenusDibujo(false);
					}
			}
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

		menu = new JMenu("Crear un autómata finito");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext().setAccessibleDescription(
				"Menu de opciones");
		menuBar.add(menu);
		this.setJMenuBar(menuBar);

		menuItem = new JMenuItem("Dibujar autómata",KeyEvent.VK_D);
		menuItem.setName("Dibujar");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
					borraDibujos();
					dibujar=true;					
					dibujanteAF = new DibujanteAF();
					agregaPanelAF(dibujanteAF);
					activarMenusDibujo(true);
					pintar();
					
			}
		});
		
		menuItem = new JMenuItem("Insertar estado", KeyEvent.VK_I);
		menuItem.setName("Insertar estado");
		menuItem.setEnabled(false);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dibujanteAF.setModo(dibujanteAF.INSERTAR_ESTADO);
			}
		});
		
		menuItem = new JMenuItem("Insertar transicion", KeyEvent.VK_I);
		menuItem.setName("Insertar transicion");
		menuItem.setEnabled(false);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dibujanteAF.setModo(dibujanteAF.INSERTAR_TRANSICION);
			}
		});
		
		menuItem = new JMenuItem("Activar/Desactivar estado final", KeyEvent.VK_I);
		menuItem.setName("Set Final");
		menuItem.setEnabled(false);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dibujanteAF.setModo(dibujanteAF.SET_FINAL);
			}
		});
		
		
		menuItem = new JMenuItem("Modo Edicion", KeyEvent.VK_I);
		menuItem.setName("Modo edicion");
		menuItem.setEnabled(false);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dibujanteAF.setModo(dibujanteAF.EDICION);
			}
		});

		menu.addSeparator();
		
		menuItem = new JMenuItem("Generar autómata finito automáticamente",KeyEvent.VK_G);
		menuItem.setName("Automata");		
		menuItem.getAccessibleContext().setAccessibleDescription("Genera un autómata");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borraDibujos();				
				String s = (String)JOptionPane.showInputDialog(
						"Introduzca el número de estados del automáta a generar","4");
				
				int numEstados = Integer.valueOf(s);
				System.out.println("Num estados: "+numEstados);
				GeneradorAF genAF = new GeneradorAF(numEstados);
				dibujanteAF = new DibujanteAF (genAF.getAF());																
				agregaPanelAF(dibujanteAF);
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
		
		menu = new JMenu ("Ver");
		menuBar.add(menu);
		JCheckBoxMenuItem jcbmenuItem = new JCheckBoxMenuItem ("Automata finito original");
		menu.add(jcbmenuItem);
		jcbmenuItem.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if (dibujanteAF != null){
					agregaPanelAF(dibujanteAF);
				}
				else {
					JCheckBoxMenuItem jcb = (JCheckBoxMenuItem)e.getSource();
					jcb.setSelected(false);
				}
			}
		});
		jcbmenuItem = new JCheckBoxMenuItem ("Automata probabilista obtenido");	
		menu.add(jcbmenuItem);
		jcbmenuItem.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if (dibujanteAFP != null){
					agregaPanelAFP(dibujanteAFP);
				}
				else {
					JCheckBoxMenuItem jcb = (JCheckBoxMenuItem)e.getSource();
					jcb.setSelected(false);
				}
			}
		});
		
		menu = new JMenu ("Enviar");
		menuItem = new JMenuItem ("Enviar problema");
		menu.add(menuItem);
		menuBar.add(menu);
		menuItem.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
			
			
				if (dibujanteAF!=null) {
					if (dibujanteAF.getAutomata()==null) 
						afGenetico = new AF(dibujanteAF.getEstados(), dibujanteAF.getTransiciones2());									 
					//GeneradorCadenas genCad = new GeneradorCadenas(dibujanteAF.getAutomata());
					boolean esAFD = afGenetico.comprobarAFD();
					if (!esAFD) {												
						 JOptionPane.showMessageDialog(null,"Falta completar transiciones en el automata", "",
								 JOptionPane.ERROR_MESSAGE);
					}
					else {				
																		
						GeneradorCadenas genCad = new GeneradorCadenas(afGenetico);
						String message = genCad.toString();
						mostrarMensaje(message);
						activarMenusDibujo(false);
						
						AddProblemaWS problemaWS = new AddProblemaWS();
						problemaWS.ejecutar();
						problemaWS.setAceptadas(genCad.getCadenasAceptadas());
						problemaWS.setRechazadas(genCad.getCadenasRechazadas());
						AFP afp = new AFP (afGenetico);
						problemaWS.setAFP(afp);
					}
				}
				else {
					 JOptionPane.showMessageDialog(null,"No hay problema que enviar", "",
							 JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		/*
        	    PanelNuevo panelNuevo = new PanelNuevo();
        	    this.add(panelNuevo);
		 */
	}

	public void pintar() {
		this.paintComponent(this.getGraphics());
		//panelPrincipal.paintComponents(this.getGraphics());
	}

	public void agregaPanelAF(JPanel panel) {
		//this.add(panel);
		JScrollPane scrollPaneAF = new JScrollPane(panel);
		panel.setPreferredSize(new Dimension (2000,2000));		
		scrollPaneAF.setPreferredSize(new Dimension(500,500));
		this.getContentPane().add(scrollPaneAF,BorderLayout.WEST);
		/*
		JScrollBar hvar = scrollPaneAF.getHorizontalScrollBar();
		//scrollPaneAF.getHorizontalScrollBar().setEnabled(true);
		scrollPaneAF.getHorizontalScrollBar().setValue(scrollPaneAF.getHorizontalScrollBar().getMaximum()/2);
		int val = scrollPaneAF.getHorizontalScrollBar().getValue();
		System.out.println(val);
		System.out.println(hvar.getValue());
		System.out.println(hvar.getMaximum());
		System.out.println("hvar.getMinimum()"+hvar.getMinimum());
		hvar.setValue(hvar.getMaximum()/2);
		System.out.println("hvar.setValue(hvar.getMaximum()/2);"+hvar.getMaximum()/2);	
		System.out.println("System.out.println(hvar.getValue());"+hvar.getValue());
		
			*/
		
		/*
		JScrollBar vert = scrollPane.getVerticalScrollBar();
		JScrollBar hor = scrollPane.getHorizontalScrollBar();
		vert.setValue((vert.getMaximum()/2));
		hor.setValue((hor.getMaximum()/2));
		*/
		//JLabel j = new JLabel ("HOLA");j.setSize(200,200);
		//j.setBackground(Color.blue);
		//j.setVisible(true);
		//this.add(j,FlowLayout.LEFT);
		
	}
	
	public void agregaPanelAFP(JPanel panel) {
		//this.add(panel);
		scrollPaneAFP = new JScrollPane(panel);
		panel.setPreferredSize(new Dimension (2000,2000));		
		scrollPaneAFP.setPreferredSize(new Dimension(500,500));
		scrollPaneAFP.setMaximumSize(new Dimension(500,1500));
		this.getContentPane().add(scrollPaneAFP,BorderLayout.CENTER);
				
		/*
		JScrollBar vert = scrollPane.getVerticalScrollBar();
		JScrollBar hor = scrollPane.getHorizontalScrollBar();
		vert.setValue((vert.getMaximum()/2));
		hor.setValue((hor.getMaximum()/2));
		*/
		//JLabel j = new JLabel ("HOLA");j.setSize(200,200);
		//j.setBackground(Color.blue);
		//j.setVisible(true);
		//this.add(j,FlowLayout.LEFT);
		
	}

	public void paintComponent(Graphics g) {		
		super.paintComponents(g);
		//panelPrincipal.paintComponents(this.getGraphics());
		//panelPrincipal.repaint();
	}
	
	public void mostrarMensaje (String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}
	
	public int mostrarMensajeConfirmacion (String mensaje) {
		int i = JOptionPane.showConfirmDialog(this,mensaje);
		return i; 
	}
	
	public void borraDibujos () {	
		if (dibujanteAF!=null) {
			this.remove(dibujanteAF);
			//dibujanteAF.setVisible(false);
		}
		if (dibujanteAFP!=null) {
			this.remove(dibujanteAFP);
			//dibujanteAFP.setVisible(false);
		}

	}
	
	public void activarMenusDibujo(boolean valor) {
		//MenuBar menubar = this.getMenuBar();
		JMenu menu = menuBar.getMenu(1);
		menu.getItem(1).setEnabled(valor);
		menu.getItem(2).setEnabled(valor);
		menu.getItem(3).setEnabled(valor);
		menu.getItem(4).setEnabled(valor);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
