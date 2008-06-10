package es.si.ProgramadorGenetico.Interfaz.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import es.si.ProgramadorGenetico.Interfaz.InterfazAdministrador;

public class MenuAdministrador extends JMenuBar {

	private static final long serialVersionUID = -5755409743938361853L;
	
	private InterfazAdministrador interfaz;
	
	public MenuAdministrador(InterfazAdministrador interfaz) {
		this.interfaz = interfaz;
		
		crearMenuProblema();
		
		crearMenuSoluciones();
		
		crearMenuAcciones();
		
		crearMenuEstadisticas();
		
		crearMenuAyuda();
	}

	private void crearMenuAyuda() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Ayuda");
		add(menu);
		
		menuItem = crearItem("Sobre la aplicación",KeyEvent.VK_S,"Muestra información sobre la aplicación");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.sobreAplicacion();
			}
		});
	}

	private void crearMenuEstadisticas() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Estadisticas");
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext()
				.setAccessibleDescription("Menu de estadisticas");
		add(menu);

		menuItem = crearItem("Ver estadisticas basicas",KeyEvent.VK_V,"Muestra estadísticas desde el servidor");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.verEstadisticas();
			}
		});

		menuItem = crearItem("Ver estadisticas avanzadas",KeyEvent.VK_V,"Muestra estadísticas desde el servidor");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.verEstadisticasAvanzadas();
			}
		});

	}

	private void crearMenuAcciones() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Acciones");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext()
				.setAccessibleDescription("Menu de acciones");
		add(menu);

		menuItem = crearItem("Resolver problema actual",KeyEvent.VK_R,"Resulve un problema y permite ver los resultados");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.resolverUnProblema();
			}
		});

		menuItem = crearItem("Resolver problema desde cadenas",KeyEvent.VK_C,"Resulve un problema dado por cadenas");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.resolverUnProblemaDesdeCadenas();
			}
		});		
	}

	private void crearMenuSoluciones() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Soluciones");
		menu.setMnemonic(KeyEvent.VK_S);
		menu.getAccessibleContext()
				.setAccessibleDescription("Menu para analisis de soluciones");
		add(menu);

		menuItem = crearItem("Explorar soluciones",KeyEvent.VK_B,"Buscar soluciones encontradas para un problema determinado");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.buscarSoluciones();
			}
		});
	}

	private void crearMenuProblema() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Problema");
		menu.setMnemonic(KeyEvent.VK_P);
		menu.getAccessibleContext()
				.setAccessibleDescription("Menu para la creación de problemas");
		add(menu);

		
		menuItem = crearItem("Gestionar Problemas",KeyEvent.VK_D,"Permite recuperar o eliminar un problema de la base de datos");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.cargarProblema();
			}
		});
		
		menu.addSeparator();
		
		menuItem = crearItem("Dibujar autómata",KeyEvent.VK_D,"Permite la creación de un autómata");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.dibujarAutomata();
			}
		});
		
		menuItem = crearItem("Generar autómata aleatorio",KeyEvent.VK_A,"Crea un automata de forma aleatoria");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.generarAleatorio();
			}
		});

		menu.addSeparator();		
		
		menuItem = crearItem("Generar cadenas aleatorias",KeyEvent.VK_C,"Crea cadenas aceptadas y rechazadas para el automata actual");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.generarCadenas();
			}
		});
		
		menuItem = crearItem("Manipular cadenas",KeyEvent.VK_M,"Añadir y quitar cadenas aceptadas y rechazadas");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.manipularCadenas();
			}
		});

		menuItem = crearItem("Modificar configuraciones",KeyEvent.VK_F,"Modificar configuraciones para la resolución");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.modificarConfiguraciones();
			}
		});
		
		menu.addSeparator();
		
		menuItem = crearItem("Enviar problema",KeyEvent.VK_E,"Enviar problema al servidor para la resolución");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrador.this.interfaz.enviarProblema();
			}
		});
	}

	private JMenuItem crearItem(String titulo, int keyEvent, String explicacion) {
		JMenuItem menuItem = new JMenuItem(titulo, keyEvent);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(explicacion);
		return menuItem;
	}

}
