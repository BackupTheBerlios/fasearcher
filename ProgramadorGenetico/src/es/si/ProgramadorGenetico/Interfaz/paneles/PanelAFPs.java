package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

public class PanelAFPs  extends JPanel {

	private static final long serialVersionUID = 47351154662044707L;

	private List<AFP> afps;
	
	private int actual = 0;
	
	private PanelAF panelAF = null;
	
	JTextField pasos = new JTextField("-");
	
	public PanelAFPs() {
		super();
		setLayout(new BorderLayout());
		crearBotones();
		crearInfo();
	};
	
	private void crearBotones() {
		JPanel panelBotones = new JPanel();
		JButton botonAnterior = new JButton("Anterior");
		botonAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelAFPs.this.anterior();
			}
		});
		panelBotones.add(botonAnterior);
		
		JButton botonSiguiente = new JButton("Siguiente");
		botonSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelAFPs.this.siguiente();
			}
		});
		panelBotones.add(botonSiguiente);
		
		add(panelBotones, BorderLayout.NORTH);
	}
	
	private void crearInfo() {
		JPanel panelInfo = new JPanel();
		panelInfo.add(new JLabel("Paso:"));
		panelInfo.add(pasos);
		add(panelInfo, BorderLayout.SOUTH);
	}
	
	protected void anterior() {
		if (actual > 0) {
			actual--;
			cambiarPanel();
		}
	}
	
	protected void siguiente() {
		if (actual < afps.size() - 1) {
			actual++;
			cambiarPanel();
		}
	}
	
	private void cambiarPanel() {
		if (panelAF != null)
			remove(panelAF);
		panelAF = new PanelAF(afps.get(actual));
		panelAF.getSubPanelAF().setEditable(false);
		add(panelAF, BorderLayout.CENTER);
		panelAF.center();
		pasos.setText("" + (actual + 1));
		this.revalidate();
	}
	
	public void setAFPs(List<AFP> afps) {
		this.afps = afps;
		actual = 0;
		cambiarPanel();
	}
	
}
