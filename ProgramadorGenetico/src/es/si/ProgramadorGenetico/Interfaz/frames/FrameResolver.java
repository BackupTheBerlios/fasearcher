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
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelConfiguracion;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;

public class FrameResolver extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6110427997886352665L;

	private AF af;
	
	private Problema problema;
	
	private PanelConfiguracion panelConfiguracion;
	
	private PanelAF panelAF;
	
	private JPanel panelInfo;
	
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
		
		remove(panelAF);
		panelAF = new PanelAF(problemaAFP.getMejor());
		add(panelAF, BorderLayout.CENTER);
		
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
