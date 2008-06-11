package es.si.ProgramadorGenetico.Interfaz.frames;

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

import es.si.ProgramadorGenetico.Interfaz.paneles.PanelAF;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelConfiguracion;
import es.si.ProgramadorGenetico.ProblemaAFP.ParametrosAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.ProblemaAFP;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CalculadorBondadAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.CruzadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.MutadorAFPFactory;
import es.si.ProgramadorGenetico.ProblemaAFP.Factorias.ResolverAFPFactory;
/**
 * Frame que resuelve el problema desde las cadenas
 *
 */
public class FrameResolverCadenas extends JFrame {

	private static final long serialVersionUID = -6110427997886352665L;
	/**
	 * Panel de configuraciones
	 */
	private PanelConfiguracion panelConfiguracion;
	/**
	 * Panel en el que se dibuja el AF
	 */
	private PanelAF panelAF;
	/**
	 * Panel de informacion de la ejecucion
	 */
	private JPanel panelInfo;
	/**
	 * Lista de cadenas aceptadas
	 */
	List<String> aceptadas;
	/**
	 * Lista de cadenas rechazadas
	 */
	List<String> rechazadas;
	/**
	 * Constructora que incializa las listas y añade los
	 * paneles
	 */
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
	/**
	 * Modifica las cadenas reconstruyendo el frame
	 */
	protected void modificarCadenas() {
		new FrameCadenas(aceptadas, rechazadas);
	}
	/**
	 * Resuelve el problema mediante el algoritmo 
	 * y muestra los datos
	 */
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
