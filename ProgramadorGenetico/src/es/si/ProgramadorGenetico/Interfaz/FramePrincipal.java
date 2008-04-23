package es.si.ProgramadorGenetico.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import java.util.List;

import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelTablaTransiciones;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Principal;
import es.si.ProgramadorGenetico.WS.AddProblemaWS;

public class FramePrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 5243835952902877030L;

	static boolean test;
	private PanelTablaTransiciones tablaTransiciones;
	private PanelAF panelAFP;
	private PanelAF dibujanteAF;
	private AF afGenetico;
	private JMenuBar menuBar;
	private GeneradorCadenas genCadenas;
	private JScrollPane scrollPaneAF;
	private JScrollPane scrollPaneAFP;

	public FramePrincipal(String s) {
		super(s);
		test = false;
		crearMenus();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static AFP creaAFPdeAF(List<String> aceptadas,
			List<String> rechazadas, int numEstados) {
		Principal.ejecuta2(aceptadas, rechazadas, numEstados);
		return Principal.getMejor();
	}

	public void crearMenus() {
		JMenu menu;
		JMenuItem menuItem;
		
		// Crea la barra de menu
		menuBar = new JMenuBar();

		// Primer menu
		menu = new JMenu("Acciones");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext()
				.setAccessibleDescription("Menu de acciones");
		menuBar.add(menu);

		// Menu Items
		menuItem = new JMenuItem(
				"Algoritmo genético a partir del autómata finito",
				KeyEvent.VK_L);
		menuItem.setName("Genetico");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Simula la configuración");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afGenetico = new AF(dibujanteAF.getEstados(), dibujanteAF
						.getTransicionesList());
				boolean esAFD = afGenetico.comprobarAFD();
				if (!esAFD) {
					JOptionPane.showMessageDialog(null,
							"Falta completar transiciones en el automata", "",
							JOptionPane.ERROR_MESSAGE);
				} else {
					genCadenas = new GeneradorCadenas(afGenetico);
					AFP mejor = creaAFPdeAF(genCadenas.getCadenasAceptadas(),
							genCadenas.getCadenasRechazadas(), afGenetico
									.getEstados());
					panelAFP = new PanelAF(mejor);
					agregaPanelAFP(panelAFP);
					agregaTabla(tablaTransiciones);
				}
				pintar();
			}
		});

		menuItem = new JMenuItem("Obtener cadenas del AF", KeyEvent.VK_O);
		menuItem.setName("Cadenas");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Obtiene cadenas");
		menu.add(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FramePrincipal.this.obtenerCadenas();
			}
		});

		menu.addSeparator();
		menuItem = new JMenuItem("Salir", KeyEvent.VK_S);
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Segundo menú
		menu = new JMenu("Crear un autómata finito");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext()
				.setAccessibleDescription("Menu de opciones");
		menuBar.add(menu);
		this.setJMenuBar(menuBar);

		menuItem = new JMenuItem("Dibujar autómata", KeyEvent.VK_D);
		menuItem.setName("Dibujar");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borraDibujos();
				dibujanteAF = new PanelAF();
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
				dibujanteAF.setModo(PanelAF.INSERTAR_ESTADO);
			}
		});

		menuItem = new JMenuItem("Insertar transicion", KeyEvent.VK_I);
		menuItem.setName("Insertar transicion");
		menuItem.setEnabled(false);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dibujanteAF.setModo(PanelAF.INSERTAR_TRANSICION);
			}
		});

		menuItem = new JMenuItem("Activar/Desactivar estado final",
				KeyEvent.VK_I);
		menuItem.setName("Set Final");
		menuItem.setEnabled(false);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dibujanteAF.setModo(PanelAF.SET_FINAL);
			}
		});

		menuItem = new JMenuItem("Modo Edicion", KeyEvent.VK_I);
		menuItem.setName("Modo edicion");
		menuItem.setEnabled(false);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dibujanteAF.setModo(PanelAF.EDICION);
			}
		});

		menu.addSeparator();

		menuItem = new JMenuItem("Generar autómata finito automáticamente",
				KeyEvent.VK_G);
		menuItem.setName("Automata");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Genera un autómata");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FramePrincipal.this.generarAutomata();
			}
		});

		menu = new JMenu("Ver");
		menuBar.add(menu);
		JCheckBoxMenuItem jcbmenuItem = new JCheckBoxMenuItem(
				"Automata finito original");
		menu.add(jcbmenuItem);
		jcbmenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dibujanteAF != null) {
					agregaPanelAF(dibujanteAF);
				} else {
					JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
					jcb.setSelected(false);
				}
			}
		});
		jcbmenuItem = new JCheckBoxMenuItem("Automata probabilista obtenido");
		menu.add(jcbmenuItem);
		jcbmenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelAFP != null) {
					agregaPanelAFP(panelAFP);
				} else {
					JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
					jcb.setSelected(false);
				}
			}
		});

		menu = new JMenu("Enviar");
		menuItem = new JMenuItem("Enviar problema");
		menu.add(menuItem);
		menuBar.add(menu);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FramePrincipal.this.enviarProblema();
			}
		});
	}

	public void obtenerCadenas() {
		if (dibujanteAF != null) {
			if (dibujanteAF.getAutomata() == null) {
				afGenetico = new AF(dibujanteAF.getEstados(),
						dibujanteAF.getTransicionesList());

				boolean esAFD = afGenetico.comprobarAFD();
				if (!esAFD) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falta completar transiciones en el automata",
									"", JOptionPane.ERROR_MESSAGE);
				} else {
					GeneradorCadenas genCad = new GeneradorCadenas(
							afGenetico);
					String message = genCad.toString();
					mostrarMensaje(message);
				}
			} else {
				GeneradorCadenas genCad = new GeneradorCadenas(
						dibujanteAF.getAutomata());
				String message = genCad.toString();
				mostrarMensaje(message);
			}
		}
	}
	
	public void generarAutomata() {
		borraDibujos();
		String s = (String) JOptionPane
				.showInputDialog(
						"Introduzca el número de estados del automáta a generar",
						"4");

		int numEstados = Integer.valueOf(s);
		System.out.println("Num estados: " + numEstados);
		GeneradorAF genAF = new GeneradorAF(numEstados);
		dibujanteAF = new PanelAF(genAF.getAF());
		agregaPanelAF(dibujanteAF);
		pintar();
		s = "¿Desea ver cadenas posibles de este automata?";
		int respuesta = mostrarMensajeConfirmacion(s);
		if (respuesta == JOptionPane.YES_OPTION) {
			GeneradorCadenas genCad = new GeneradorCadenas(dibujanteAF
					.getAutomata());
			String message = genCad.toString();
			mostrarMensaje(message);
		}
	}
	
	public void enviarProblema() {
		if (dibujanteAF != null) {
			if (dibujanteAF.getAutomata() == null) {
				afGenetico = new AF(dibujanteAF.getEstados(),
						dibujanteAF.getTransicionesList());

				boolean esAFD = afGenetico.comprobarAFD();
				if (!esAFD) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falta completar transiciones en el automata",
									"", JOptionPane.ERROR_MESSAGE);
				} else {
					GeneradorCadenas genCad = new GeneradorCadenas(
							afGenetico);
					String message = genCad.toString();
					mostrarMensaje(message);
					AddProblemaWS problemaWS = new AddProblemaWS();
					problemaWS.setAceptadas(genCad
							.getCadenasAceptadas());
					problemaWS.setRechazadas(genCad
							.getCadenasRechazadas());
					AFP afp = new AFP(afGenetico);
					problemaWS.setAFP(afp);
					problemaWS.ejecutar();
				}
			} else {
				GeneradorCadenas genCad = new GeneradorCadenas(
						dibujanteAF.getAutomata());
				String message = genCad.toString();
				mostrarMensaje(message);
				AddProblemaWS problemaWS = new AddProblemaWS();
				problemaWS.setAceptadas(genCad.getCadenasAceptadas());
				problemaWS.setRechazadas(genCad.getCadenasRechazadas());
				AFP afp = new AFP(dibujanteAF.getAutomata());
				problemaWS.setAFP(afp);
				problemaWS.ejecutar();
			}
		}

		else {
			JOptionPane.showMessageDialog(null,
					"No hay problema que enviar", "",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void pintar() {
		this.paintComponent(this.getGraphics());
	}

	public void agregaPanelAF(JPanel panel) {
		// this.add(panel);
		if (scrollPaneAF != null) {
			this.remove(scrollPaneAF);
		}
		scrollPaneAF = new JScrollPane(panel);
		panel.setPreferredSize(new Dimension(2000, 2000));
		scrollPaneAF.setPreferredSize(new Dimension(500, 500));
		this.getContentPane().add(scrollPaneAF, BorderLayout.WEST);
	}

	public void agregaPanelAFP(JPanel panel) {
		if (scrollPaneAFP != null)
			this.remove(scrollPaneAFP);
		scrollPaneAFP = new JScrollPane(panel);
		panel.setPreferredSize(new Dimension(2000, 2000));
		scrollPaneAFP.setPreferredSize(new Dimension(500, 500));
		scrollPaneAFP.setMaximumSize(new Dimension(500, 1500));
		this.getContentPane().add(scrollPaneAFP, BorderLayout.CENTER);
	}

	public void agregaTabla(PanelTablaTransiciones tablaTransiciones) {
		tablaTransiciones = new PanelTablaTransiciones(panelAFP);
		this.add(tablaTransiciones);
		this.update(this.getGraphics());
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}

	public int mostrarMensajeConfirmacion(String mensaje) {
		int i = JOptionPane.showConfirmDialog(this, mensaje);
		return i;
	}

	public void borraDibujos() {
		if (dibujanteAF != null) {
			this.remove(scrollPaneAF);
		}
		if (panelAFP != null) {
			this.remove(scrollPaneAFP);
		}

	}

	public void activarMenusDibujo(boolean valor) {
		// MenuBar menubar = this.getMenuBar();
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
