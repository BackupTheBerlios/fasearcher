package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.data.Problema;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAFPs;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelConfiguracion;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;
/**
 * Frame que se muestra cuando se pide resolver el problema
 * actual
 */
public class FrameResolver extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6110427997886352665L;
	/**
	 * Automata que representa el problema
	 */
	private AF af;
	/**
	 * Datos del problema a resolver
	 */
	private Problema problema;
	/**
	 * Panel de las configuraciones
	 */
	private PanelConfiguracion panelConfiguracion;
	/**
	 * Panel del AF
	 */
	private PanelAF panelAF;
	/**
	 * Panel de los AFPs
	 */
	private PanelAFPs panelAFPs = null;
	/**
	 * Panel de informacion del estado de la ejecucion
	 */
	private JPanel panelInfo;
	/**
	 * Constructora que guarda el AF y el problema
	 * y hae las inicializaciones necesarias
	 * @param af
	 * @param problema
	 */
	public FrameResolver(AF af, Problema problema) {
		this.af = af;
		this.problema = problema;
		
		JPanel panel = new JPanel();
		panelConfiguracion = new PanelConfiguracion();
		panel.add(panelConfiguracion);
		
		JButton boton = new JButton("Resolver");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameResolver.this.resolver();
			}
		});
		panel.add(boton);
		
		setLayout(new BorderLayout());
		
		add(panel, BorderLayout.NORTH);
		
		panelAF = new PanelAF(af);
		add(panelAF, BorderLayout.CENTER);
		
		setSize(950,600);
		setVisible(true);
	}
	/**
	 * Metodo que llama al algoritmo para resolver el problema
	 * y muestra la solucion
	 */
	protected void resolver() {
		ProblemaAFP problemaAFP = new ProblemaAFP();
		problemaAFP.setLocal(true);
		CruzadorAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getCruzador());
		MutadorAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getMutador());
		ResolverAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getResolver());
		CalculadorBondadAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getCalculadorBondad());
		
		List<String> listaAceptadas = new ArrayList<String>();
		List<String> listaRechazadas = new ArrayList<String>();
		for (String cadena : problema.getCadenas()) {
			if (af.acepta(cadena))
				listaAceptadas.add(cadena);
			else
				listaRechazadas.add(cadena);
		}
		ParametrosAFP.getInstance().setAceptadas(listaAceptadas);
		ParametrosAFP.getInstance().setRechazadas(listaRechazadas);
		ParametrosAFP.getInstance().setEstados(panelConfiguracion.getConfiguracion().getEstados());
		ParametrosAFP.getInstance().getMuestras().clear();
		ParametrosAFP.getInstance().getMuestras().add(panelConfiguracion.getConfiguracion().getMuestras());
		ParametrosAFP.getInstance().getPobmax().clear();
		ParametrosAFP.getInstance().getPobmax().add(panelConfiguracion.getConfiguracion().getPobMax());
		
		problemaAFP.ejecutar();
		
		if (panelAF != null) {
			remove(panelAF);
			panelAF = null;
		}
		if (panelAFPs != null)  {
			remove(panelAFPs);
		}
		panelAFPs = new PanelAFPs();
		panelAFPs.setAFPs(problemaAFP.getMejores());
		add(panelAFPs, BorderLayout.CENTER);
		
		if (panelInfo != null)
			remove(panelInfo);
		panelInfo = new JPanel();
		
		panelInfo.add(new JLabel("reconocimiento:"));
		
		panelInfo.add(new JLabel(""+problemaAFP.getReconocimiento()));
		
		panelInfo.add(new JLabel("parecido AF:"));
		
		panelInfo.add(new JLabel(""+problemaAFP.getParecidoAF()));
		
		add(panelInfo, BorderLayout.SOUTH);
		
		validate();
	}
}
