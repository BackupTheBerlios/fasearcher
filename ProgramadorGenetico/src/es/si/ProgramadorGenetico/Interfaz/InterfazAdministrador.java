package es.si.ProgramadorGenetico.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorAF;
import es.si.ProgramadorGenetico.GeneradorAutomatico.GeneradorCadenas;
import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameCadenas;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameCargarProblema;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameConfiguraciones;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameEstadisticasAvanzadas;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameResolver;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameResolverCadenas;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameSobreAplicacion;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameSoluciones;
import es.si.ProgramadorGenetico.Interfaz.frames.FrameStats;
import es.si.ProgramadorGenetico.Interfaz.menus.MenuAdministrador;
import es.si.ProgramadorGenetico.Interfaz.paneles.BarraEdicion;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAFPs;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelInfo;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.WS.AddProblemaWS;
import es.si.ProgramadorGenetico.WS.RemoveProblemaWS;

/**
 * Clase principal de la aplicación del administrador
 */
public class InterfazAdministrador extends JFrame implements InterfazGrafica {

	private static final long serialVersionUID = 315808382302135788L;
	/**
	 * Menu principal
	 */
	private JMenuBar menuBar;
	/**
	 * Panel de los AFPs 
	 */
	private PanelAFPs panelAFPs = null;
	/**
	 * Panel de los AF que dibuja el usuario
	 */
	private PanelAF panelAF = null;	
	/**
	 * Panel de informacion sobre el estado de la ejecucion
	 */
	private PanelInfo panelInfo;
	/**
	 * Atributo que se encarga de poner en marcha
	 * la ejecucion de resolver problemas
	 */
	private ResolverProblemas resolverProblemas = null;
	/**
	 * Valor booleano que indica si se debe mostrar el frame
	 */
	private boolean mostrar = true;
	/**
	 * Guarda el problema actual
	 */
	private Problema problema;
	/**
	 * Barra de edicion que contiene los elementos de dibujo
	 */
	private BarraEdicion barraEdicion;
	

	/**
	 * Constructora principal. Construye los menus y los paneles
	 * y hace las inicializaciones necesarias
	 */
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

	/**
	 * Actualiza el valor del atributo mostrar a selected
	 * @param selected
	 */
	public void setMostrar(boolean selected) {
		mostrar = selected;
	}


	/**
	 * Detiene la resolucion de problemas
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
	 * Inicia la resolucion de un problema
	 */
	public void resolverUnProblema() {
		AF af = creadoAutomata();
		if (af != null) {
			if (problema.getCadenas().size() > 0)
				new FrameResolver(af, problema);
			else {
				JOptionPane.showMessageDialog(this, "No hay suficientes cadenas");
			}
		}
	}
	
	/**
	 * Actualiza los paneles necesarios cuando ha terminado de resolver un problema 
	 */
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


	

	/**
	 * Actualiza los paneles necesarios cuando ha terminado de resolver muchos problemas
	 */
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

	/**
	 * Quita paneles
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

	/**
	 * Dibuja un automata
	 */
	public void dibujarAutomata() {
		quitarPaneles();
		problema = new Problema();
		panelAF = new PanelAF();
		panelAF.getSubPanelAF().setEditable(true);
		add(panelAF, BorderLayout.CENTER);
		validate();
	}

	/**
	 * Genera un automata aleatorio
	 */
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

	/**
	 * Genera cadenas aleatorias para el problema
	 */
	public void generarCadenas() {
		AF af = creadoAutomata();
		if (af != null) {
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
		

	}

	/**
	 * Crea un automata
	 * @return automata creado
	 */
	private AF creadoAutomata() {
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
				return af;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Primero debe crear un automata");
		}
		return null;
	}

	/**
	 * Crea un frame para manipular cadenas
	 */	
	public void manipularCadenas() {
		AF af = creadoAutomata();
		if (af != null)
				new FrameCadenas(af, problema);
	}

	/**
	 * Envia el problema a la base de datos
	 */
	public void enviarProblema() {
		AF af = creadoAutomata();
		if (af != null) {
			if (problema.getCadenas().size() > 0) {
				int s = JOptionPane.YES_OPTION;
				if (problema.getConfiguraciones().size() == 0) 
					s = JOptionPane.showOptionDialog(this, "Seguro que no quiere añadir configuraciones?", "Sin configuraciones", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (s == JOptionPane.YES_OPTION) {
					if (problema.getId() != null) {
						s = JOptionPane.showOptionDialog(this, "Desea reemplazar el problema que esta en la base de datos?\n(De lo contrario se añade como uno nuevo)","Problema ya en base de datos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (s == JOptionPane.YES_OPTION) {
							RemoveProblemaWS removeProblemaWS = new RemoveProblemaWS();
							removeProblemaWS.setId(problema.getId());
							removeProblemaWS.ejecutar();
						}
					}
					
					String id = "-1";
					String descripcion = "";
					if (problema.getDescripcion() == null)
						descripcion = JOptionPane.showInputDialog(this, "Escriba la descripción para el problema");
					else
						descripcion = JOptionPane.showInputDialog(this, "Escriba la descripción para el problema", problema.getDescripcion());
					try {
						AddProblemaWS problemaWS = new AddProblemaWS();
						List<String> aceptadas = new ArrayList<String>();
						List<String> rechazadas = new ArrayList<String>();
						for (String cadena : problema.getCadenas()) {
							if (af.acepta(cadena))
								aceptadas.add(cadena);
							else
								rechazadas.add(cadena);
						}
						problemaWS.setAceptadas(aceptadas);
						problemaWS.setRechazadas(rechazadas);
						problemaWS.setAFP(new AFP(af));
						problemaWS.setTipoAutomata("AFP");
						problemaWS.setDescripcion(descripcion);
						for (Configuracion config : problema.getConfiguraciones()) 
							problemaWS.addConfiguracion(config.getMuestras(), config.getPobMax(), config.getEstados(), config.getCalculadorBondad(), config.getCruzador(), config.getMutador(), config.getResolver());
						problemaWS.ejecutar();
						id = problemaWS.getId();
					} catch (Exception e) {
						id = "-1";
					}
					if (id.equals("-1"))
						JOptionPane.showMessageDialog(this, "Error al enviar problema");
					else
						JOptionPane.showMessageDialog(this, "El problema se envio correctamente, id: " + id);
				}
			} else 
				JOptionPane.showMessageDialog(this, "Primero debe añadir cadenas");
		} else 
			JOptionPane.showMessageDialog(this, "Primero debe crear un automata");
	}

	/**
	 * Muestra el frame para modificar configuraciones
	 */
	public void modificarConfiguraciones() {
		if (creadoAutomata() != null)
			new FrameConfiguraciones(problema);
	}

	/**
	 * Muestra el frame de las soluciones de la base de datos
	 */
	public void buscarSoluciones() {
		new FrameSoluciones();
	}

	/**
	 * Muestra el frame para resolver un problema desde cadenas 
	 */
	public void resolverUnProblemaDesdeCadenas() {
		new FrameResolverCadenas();
	}

	/**
	 * Muestra el frame de las estadisticas
	 */
	public void verEstadisticas() {
		new FrameStats();
	}
	
	/**
	 * Devuelve el panel del automata
	 * @return el panel del automata
	 */
	public PanelAF getPanelAF() {
		return panelAF;
	}

	/**
	 * Muestra el frame de cargar problemas
	 */
	public void cargarProblema() {
		new FrameCargarProblema(this);
	}
	
	/**
	 * Dibuja el automata afp y guarda el problema
	 * @param afp
	 * @param problema
	 */
	public void terminoCargar(AFP afp, Problema problema) {
		quitarPaneles();
		this.problema = problema;
		panelAF = new PanelAF(afp);
		panelAF.getSubPanelAF().setEditable(true);
		add(panelAF, BorderLayout.CENTER);
		validate();
	}

	/**
	 * Muestra el frame de estadisticas avanzadas
	 */
	public void verEstadisticasAvanzadas() {
		new FrameEstadisticasAvanzadas();
	}

	/**
	 * Muestra el frame sobre la aplicacion
	 */
	public void sobreAplicacion() {
		new FrameSobreAplicacion();
	}
}
