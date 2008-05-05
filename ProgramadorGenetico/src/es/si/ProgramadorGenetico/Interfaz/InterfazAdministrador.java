package es.si.ProgramadorGenetico.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.menus.MenuAdministrador;
import es.si.ProgramadorGenetico.Interfaz.paneles.BarraEdicion;
import es.si.ProgramadorGenetico.Interfaz.paneles.FrameCadenas;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAFPs;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelInfo;
import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class InterfazAdministrador extends JFrame implements InterfazGrafica {

	private static final long serialVersionUID = 315808382302135788L;
	
	private JMenuBar menuBar;
	
	private PanelAFPs panelAFPs = null;
	
	private PanelAF panelAF = null;
	
	private PanelInfo panelInfo;
	
	private ResolverProblemas resolverProblemas = null;
	
	private boolean mostrar = true;
	
	private Problema problema;
	
	private BarraEdicion barraEdicion;
	
	public InterfazAdministrador() {
		super("FASearcher");
		menuBar = new MenuAdministrador(this);
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		panelInfo = new PanelInfo();
		add(panelInfo, BorderLayout.NORTH);
		barraEdicion = new BarraEdicion(this);
		barraEdicion.setOrientation(JToolBar.VERTICAL);
		add(barraEdicion, BorderLayout.WEST);
		
		setSize(new Dimension(650,650));
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
					InterfazAdministrador.this.terminoResolver(true, problemaAFP.getMejores());
				} catch (Exception e) {
					e.printStackTrace();
					InterfazAdministrador.this.terminoResolver(false, null);
					
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
			InterfazAdministrador.this.validate();
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
			InterfazAdministrador.this.validate();
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

	public void dibujarAutomata() {
		quitarPaneles();
		problema = new Problema();
		panelAF = new PanelAF();
		panelAF.getSubPanelAF().setEditable(true);
		add(panelAF, BorderLayout.CENTER);
		validate();
	}

	public void generarAleatorio() {
		String s = (String) JOptionPane.showInputDialog(
				"Introduzca el número de estados del automáta a generar",
				"4");
		try {
			int numEstados = Integer.parseInt(s);
			quitarPaneles();
			problema = new Problema();
			GeneradorAF genAF = new GeneradorAF(numEstados);
			panelAF = new PanelAF(genAF.getAF());
			add(panelAF, BorderLayout.CENTER);
			validate();
		} catch(Exception e) {}
	}

	public void generarCadenas() {
		if (problema != null && panelAF != null) {
			AF af = new AF(panelAF.getSubPanelAF().getEstados(),
					panelAF.getSubPanelAF().getTransicionesList());

			boolean esAFD = af.comprobarAFD();
			if (!esAFD) {
				JOptionPane
						.showMessageDialog(
								this,
								"Falta completar transiciones en el automata",
								"", JOptionPane.ERROR_MESSAGE);
			} else {
				GeneradorCadenas genCad = new GeneradorCadenas(af);
				String message = genCad.toString();
				JOptionPane.showMessageDialog(this, message);
				if (problema.getCadenas().size() != 0) {
					int option = JOptionPane.showOptionDialog(this, "Desea unir las nuevas cadenas a las ya existentes?", "Generacion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (option == JOptionPane.YES_OPTION) {
						problema.getCadenas().addAll(genCad.getCadenasAceptadas());
						problema.getCadenas().addAll(genCad.getCadenasRechazadas());
					} else if (option == JOptionPane.NO_OPTION) {
						problema.setCadenas(genCad.getCadenasAceptadas());
						problema.getCadenas().addAll(genCad.getCadenasRechazadas());						
					}
				} else {
					problema.setCadenas(genCad.getCadenasAceptadas());
					problema.getCadenas().addAll(genCad.getCadenasRechazadas());
				}
			}
			
		} else {
			JOptionPane.showMessageDialog(this, "Primero debe crear un automata");
		}
	}

	public void manipularCadenas() {
		if (problema != null && panelAF != null) {
			AF af = new AF(panelAF.getSubPanelAF().getEstados(),
					panelAF.getSubPanelAF().getTransicionesList());

			boolean esAFD = af.comprobarAFD();
			if (!esAFD) {
				JOptionPane
						.showMessageDialog(
								this,
								"Falta completar transiciones en el automata",
								"", JOptionPane.ERROR_MESSAGE);
			} else {
				new FrameCadenas(af, problema);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Primero debe crear un automata");
		}
	}

	public void enviarProblema() {
		// TODO Auto-generated method stub
		
	}

	public void modificarConfiguraciones() {
		// TODO Auto-generated method stub
		
	}

	public void buscarSoluciones() {
		// TODO Auto-generated method stub
		
	}

	public void resolverUnProblemaDesdeCadenas() {
		// TODO Auto-generated method stub
		
	}

	public void verEstadisticas() {
		// TODO Auto-generated method stub
		
	}
	
	public PanelAF getPanelAF() {
		return panelAF;
	}
}
