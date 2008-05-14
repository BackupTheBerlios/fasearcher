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

public class InterfazCliente extends JFrame implements InterfazGrafica {

	private static final long serialVersionUID = 315808382302135788L;
	
	private JMenuBar menuBar;
	
	private PanelAFPs panelAFPs = null;
	
	private PanelAF panelAF = null;
	
	private PanelInfo panelInfo;
	
	private ResolverProblemas resolverProblemas = null;
	
	private boolean mostrar = true;
	
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

	
	public void setMostrar(boolean selected) {
		mostrar = selected;
	}


	public void detenerResolucion() {
		if (resolverProblemas != null)
			resolverProblemas.stop();
		panelInfo.terminaResolviendo("Terminado");
	}

	public void resolverProblemas() {
		resolverProblemas = new ResolverProblemas(this);
		Thread thread = new Thread(resolverProblemas);
		thread.start();
		panelInfo.resolviendo();
	}

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
