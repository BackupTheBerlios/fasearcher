package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;

public class FrameResolverCadenas extends JFrame {

	private static final long serialVersionUID = -6110427997886352665L;
	
	private PanelConfiguracion panelConfiguracion;
	
	private PanelAF panelAF;
	
	private JPanel panelInfo;
	
	List<String> aceptadas;
	
	List<String> rechazadas;
	
	public FrameResolverCadenas() {
		panelConfiguracion = new PanelConfiguracion();
		add(panelConfiguracion);
		
		aceptadas = new ArrayList<String>();
		rechazadas = new ArrayList<String>();
		
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.PAGE_AXIS));
		
		JButton boton = new JButton("Modificar cadenas");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameResolverCadenas.this.modificarCadenas();
			}
		});
		temp.add(boton);

		boton = new JButton("Resolver");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameResolverCadenas.this.resolver();
			}
		});
		temp.add(boton);
		
		setLayout(new BorderLayout());
		
		add(panelConfiguracion, BorderLayout.NORTH);
		add(temp, BorderLayout.WEST);
		
		setSize(950,600);
		setVisible(true);
	}

	protected void modificarCadenas() {
		new FrameCadenas(aceptadas, rechazadas);
	}

	protected void resolver() {
		if (aceptadas.size() != 0 && rechazadas.size() != 0) {
			ProblemaAFP problemaAFP = new ProblemaAFP();
			problemaAFP.setLocal(true);
			CruzadorAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getCruzador());
			MutadorAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getMutador());
			ResolverAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getResolver());
			CalculadorBondadAFPFactory.setTipo(panelConfiguracion.getConfiguracion().getCalculadorBondad());
			
			ParametrosAFP.getInstance().setAceptadas(aceptadas);
			ParametrosAFP.getInstance().setRechazadas(rechazadas);
			ParametrosAFP.getInstance().setEstados(panelConfiguracion.getConfiguracion().getEstados());
			ParametrosAFP.getInstance().getMuestras().clear();
			ParametrosAFP.getInstance().getMuestras().add(panelConfiguracion.getConfiguracion().getMuestras());
			ParametrosAFP.getInstance().getPobmax().clear();
			ParametrosAFP.getInstance().getPobmax().add(panelConfiguracion.getConfiguracion().getPobMax());
			
			problemaAFP.ejecutar();
			
			if (panelAF != null)
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
		} else {
			JOptionPane.showMessageDialog(this, "Se debe agregar alguna cadena");
		}
	}
}
