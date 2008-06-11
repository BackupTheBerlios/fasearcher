package es.si.ProgramadorGenetico.Interfaz.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import es.si.ProgramadorGenetico.Interfaz.InterfazCliente;
/**
 * Menu de la interfaz del cliente
 *
 */
public class MenuCliente extends JMenuBar {

	private static final long serialVersionUID = 4082314033249181875L;
	/**
	 * Interfaz del cliente
	 */
	InterfazCliente interfaz;
	/**
	 * Constructora que toma la interfaz y crea
	 * los menus necesarios
	 * @param interfaz
	 */
	public MenuCliente(InterfazCliente interfaz) {
		this.interfaz = interfaz;
		
		JMenu menu;
		JMenuItem menuItem;

		menu = new JMenu("Acciones");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext()
				.setAccessibleDescription("Menu de acciones");
		add(menu);

		menuItem = crearItem("Resolver un problema",KeyEvent.VK_R,"Resulve un problema y permite ver los resultados");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuCliente.this.interfaz.resolverUnProblema();
			}
		});

		menu.addSeparator();
		
		menuItem = crearItem("Empezar a resolver problemas", KeyEvent.VK_E,"Empieza a resolver problemas, hasta que se solicita que pare");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuCliente.this.interfaz.resolverProblemas();
			}
		});

		menuItem = crearItem("Parar de resolver problemas", KeyEvent.VK_P, "Detiene la resolucion de problemas");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuCliente.this.interfaz.detenerResolucion();
			}
		});

		menu = new JMenu("Vista");
		menu.setMnemonic(KeyEvent.VK_V);
		menu.getAccessibleContext().setAccessibleDescription("Menu para modificar lo mostrado");
		add(menu);

		JCheckBoxMenuItem jcbmenuItem = new JCheckBoxMenuItem("Mostrar automatas generados");
		jcbmenuItem.setSelected(true);
		menu.add(jcbmenuItem);
		jcbmenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
				MenuCliente.this.interfaz.setMostrar(jcb.isSelected());
			}
		});
	}
	/**
	 * Crea un item del menu a partir de los parametros 
	 * @param titulo
	 * @param keyEvent
	 * @param explicacion
	 * @return
	 */
	private JMenuItem crearItem(String titulo, int keyEvent, String explicacion) {
		JMenuItem menuItem = new JMenuItem(titulo, keyEvent);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(explicacion);
		return menuItem;
	}

}
