package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.si.ProgramadorGenetico.Interfaz.data.Configuracion;

public class PanelConfiguracion extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3342808888708261661L;
	
	JTextField muestras;
	
	JTextField estados;
	
	JTextField pobMax;
	
	JComboBox calculadorBondad;
	
	JComboBox resolver;
	
	JComboBox cruzador;
	
	JComboBox mutador;
	
	public PanelConfiguracion() {
		setLayout(new GridLayout(2,7));
		
		add(new JLabel("Estados"));
		add(new JLabel("Muestras"));
		add(new JLabel("Pob Max"));
		add(new JLabel("cruzador"));
		add(new JLabel("mutador"));
		add(new JLabel("calculadorBondad"));
		add(new JLabel("resolver"));
				
		estados = new JTextField(10);
		add(estados);

		muestras = new JTextField(10);
		add(muestras);
		
		pobMax = new JTextField(10);
		add(pobMax);

		cruzador = new JComboBox();
		add(cruzador);
		
		mutador = new JComboBox();
		add(mutador);
		
		calculadorBondad = new JComboBox();
		add(calculadorBondad);
		
		resolver = new JComboBox();
		add(resolver);
		
		llenarCombos();
		
	}

	private void llenarCombos() {
		calculadorBondad.addItem("SIMPLE");
		calculadorBondad.addItem("CUADRATICO");
		calculadorBondad.addItem("BALANACEADO");
		calculadorBondad.addItem("PREFERNCIADET");

		resolver.addItem("VECTORES");
		resolver.addItem("PILA");

		cruzador.addItem("NULO");
		cruzador.addItem("TIPO_1");
		cruzador.addItem("TIPO_2");
		cruzador.addItem("TIPO_3");

		mutador.addItem("NULO");
		mutador.addItem("TIPO_1");
		mutador.addItem("TIPO_2");
	}

	public Configuracion getConfiguracion() {
		try {
			Configuracion config = new Configuracion();
			config.setMuestras(Integer.parseInt(muestras.getText()));
			config.setPobMax(Integer.parseInt(pobMax.getText()));
			config.setEstados(Integer.parseInt(estados.getText()));
			config.setCalculadorBondad(calculadorBondad.getSelectedIndex());
			config.setResolver(resolver.getSelectedIndex());
			config.setCruzador(cruzador.getSelectedIndex());
			config.setMutador(mutador.getSelectedIndex());
			
			return config;
		} catch(Exception e) {
			return null;
		}
	}
}
