package es.si.ProgramadorGenetico.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import es.si.ProgramadorGenetico.Interfaz.menus.MenuCliente;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAFPs;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelInfo;
import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

/**
 * Clase principal del cliente 
 *
 */
public class InterfazCliente extends JFrame implements InterfazGrafica {

	private static final long serialVersionUID = 315808382302135788L;
	
	/**
	 * Menu principal del frame
	 */
	private JMenuBar menuBar;
	/**
	 * Panel de los AFPs
	 */
	private PanelAFPs panelAFPs = null;
	/**
	 * Panel de los automatas AF dibujados por el usuario
	 */
	private PanelAF panelAF = null;
	/**
	 * Panel de informacion del estado de la ejecucion
	 */
	private PanelInfo panelInfo;
	/**
	 * Atributo que se encarga de poner en marcha la 
	 * resolucion de los problemas
	 */
	private ResolverProblemas resolverProblemas = null;
	/**
	 * Atributo que indica si se debe mostrar el frame
	 */
	private boolean mostrar = true;
	
	/**
	 * Constructora principal que hace las inicializaciones
	 * de paneles necesarias
	 */
	public InterfazCliente() {
		super("FASearcher");
		menuBar = new MenuCliente(this);
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		panelInfo = new PanelInfo();
		add(panelInfo, BorderLayout.NORTH);
		setSize(new Dimension(600,650));
		validate();
		setVisible(true);
	}

	/**
	 * Actualiza el valor de mostrar a selected
	 * @param selected
	 */
	public void setMostrar(boolean selected) {
		mostrar = selected;
	}
	/**
	 * Detiene la ejecucion de la resolucion de problemas
	 */
	public void detenerResolucion() {
		if (resolverProblemas != null)
			resolverProblemas.stop();
		panelInfo.terminaResolviendo("Terminado");
	}
	/**
	 * Inicia la resolucion de problemas 
	 */
	public void resolverProblemas() {
		resolverProblemas = new ResolverProblemas(this);
		Thread thread = new Thread(resolverProblemas);
		thread.start();
		panelInfo.resolviendo();
	}

	/**
	 * Inicia la resolucion de un unico problema
	 */
	public void resolverUnProblema() {
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
	/**
	 * Hace las actualizaciones necesarias de paneles cuando
	 * termina de resolver un problema
	 */
	public void terminoResolver(boolean correcto, List<AFP> mejores) {
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
	/**
	 * Hace las actualizaciones necesarias de paneles cuando
	 * termina de resolver muchos problemas
	 */
	public void terminoResolverMuchos(boolean correcto, AFP mejor) {
		if (correcto) {
			quitarPaneles();
			if (mostrar) {
				panelAF = new PanelAF(mejor);
				panelAF.getSubPanelAF().setEditable(false);
				add(panelAF, BorderLayout.CENTER);
			}
			InterfazCliente.this.validate();
		} else {
			panelInfo.terminaResolviendo("Terminado con errores");
		}
	}
	/**
	 * Se encarga de quitar los paneles necesarios
	 */
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
