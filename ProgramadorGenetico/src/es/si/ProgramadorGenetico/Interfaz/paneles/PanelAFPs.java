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
/**
 * Panel que contiene los mejores AFPs que han sido 
 * a lo largo de los pasos del algoritmo genetico
 */
public class PanelAFPs  extends JPanel {

	private static final long serialVersionUID = 47351154662044707L;
	/**
	 * Lista de los AFPs
	 */
	private List<AFP> afps;
	/**
	 * Indice del AFP actual
	 */
	private int actual = 0;
	/**
	 * Panel del AF actual
	 */
	private PanelAF panelAF = null;
	/**
	 * Numero de pasos
	 */
	JTextField pasos = new JTextField("-");
	/**
	 * Constructora que crea los botones
	 */
	public PanelAFPs() {
		super();
		setLayout(new BorderLayout());
		crearBotones();
		crearInfo();
	};
	/**
	 * Se crean botones de anterior y siguieten
	 * para avanzar o retroceder en los pasos
	 */
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
	/**
	 * Crea un panel con la informacion de paso
	 */
	private void crearInfo() {
		JPanel panelInfo = new JPanel();
		panelInfo.add(new JLabel("Paso:"));
		panelInfo.add(pasos);
		add(panelInfo, BorderLayout.SOUTH);
	}
	/**
	 * Retrocede al anterior AFP
	 */
	protected void anterior() {
		if (actual > 0) {
			actual--;
			cambiarPanel();
		}
	}
	/**
	 * Avanza al siguiente AFP
	 */
	protected void siguiente() {
		if (actual < afps.size() - 1) {
			actual++;
			cambiarPanel();
		}
	}
	/**
	 * Cambia el panel al cambiar de AFP
	 */
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
	/**
	 * Actualiza el valor de afps
	 * @param afps
	 */
	public void setAFPs(List<AFP> afps) {
		this.afps = afps;
		actual = 0;
		cambiarPanel();
	}
	
}
