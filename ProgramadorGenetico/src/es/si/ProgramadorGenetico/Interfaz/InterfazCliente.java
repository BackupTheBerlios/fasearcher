package es.si.ProgramadorGenetico.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAFPs;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelInfo;
import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class InterfazCliente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 315808382302135788L;
	
	private JMenuBar menuBar;
	
	private PanelAFPs panelAFPs = null;
	
	private PanelAF panelAF = null;
	
	private PanelInfo panelInfo;
	
	private ResolverProblemas resolverProblemas = null;
	
	private boolean mostrar = true;
	
	public InterfazCliente() {
		super("FASearcher");
		crearMenus();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		panelInfo = new PanelInfo();
		add(panelInfo, BorderLayout.NORTH);
		setSize(new Dimension(600,650));
		validate();
		setVisible(true);
	}

	private void crearMenus() {
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
		menuItem = crearItem("Resolver un problema",KeyEvent.VK_R,"Resulve un problema y permite ver los resultados");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfazCliente.this.resolverUnProblema();
			}
		});

		menu.addSeparator();
		
		menuItem = crearItem("Empezar a resolver problemas", KeyEvent.VK_E,"Empieza a resolver problemas, hasta que se solicita que pare");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfazCliente.this.resolverProblemas();
			}
		});

		menuItem = crearItem("Parar de resolver problemas", KeyEvent.VK_P, "Detiene la resolucion de problemas");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfazCliente.this.detenerResolucion();
			}
		});

		// Segundo men�
		menu = new JMenu("Vista");
		menu.setMnemonic(KeyEvent.VK_V);
		menu.getAccessibleContext().setAccessibleDescription("Menu para modificar lo mostrado");
		menuBar.add(menu);
		this.setJMenuBar(menuBar);

		JCheckBoxMenuItem jcbmenuItem = new JCheckBoxMenuItem("Mostrar automatas generados");
		jcbmenuItem.setSelected(true);
		menu.add(jcbmenuItem);
		jcbmenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
				InterfazCliente.this.setMostrar(jcb.isSelected());
			}
		});
	}

	private JMenuItem crearItem(String titulo, int keyEvent, String explicacion) {
		JMenuItem menuItem = new JMenuItem(titulo, keyEvent);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(explicacion);
		return menuItem;
	}
	
	protected void setMostrar(boolean selected) {
		mostrar = selected;
	}


	protected void detenerResolucion() {
		if (resolverProblemas != null)
			resolverProblemas.stop();
		panelInfo.terminaResolviendo("Terminado");
	}

	protected void resolverProblemas() {
		resolverProblemas = new ResolverProblemas(this);
		Thread thread = new Thread(resolverProblemas);
		thread.start();
		panelInfo.resolviendo();
	}

	protected void resolverUnProblema() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try{
					ProblemaAFP problemaAFP = new ProblemaAFP();
					problemaAFP.ejecutar();
					InterfazCliente.this.terminoResolver(true, problemaAFP.getMejores());
				} catch (Exception e) {
					e.printStackTrace();
					InterfazCliente.this.terminoResolver(false, null);
					
				}
			}
		});
		thread.start();
		panelInfo.resolviendo();
	}
	
	protected void terminoResolver(boolean correcto, List<AFP> mejores) {
		if (correcto) {
			quitarPaneles();
			panelAFPs = new PanelAFPs();
			panelAFPs.setAFPs(mejores);
			add(panelAFPs, BorderLayout.CENTER);
			InterfazCliente.this.validate();
			panelInfo.terminaResolviendo("Terminado");
		} else {
			panelInfo.terminaResolviendo("Terminado con errores");
		}
	}
	
	public void terminoResolverMuchos(boolean correcto, AFP mejor) {
		if (correcto) {
			quitarPaneles();
			if (!mostrar) {
				panelAF = new PanelAF(mejor);
				panelAF.getSubPanelAF().setEditable(false);
				add(panelAF, BorderLayout.CENTER);
			}
			InterfazCliente.this.validate();
		} else {
			panelInfo.terminaResolviendo("Terminado con errores");
		}
	}
	
	private void quitarPaneles() {
		if (panelAFPs != null) {
			remove(panelAFPs);
			panelAFPs = null;
		}
		if (panelAF != null) {
			remove(panelAF);
			panelAF = null;
		}
	}
}
