package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import es.si.ProgramadorGenetico.Interfaz.InterfazAdministrador;

public class BarraEdicion extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3025743241417145071L;
	
	private InterfazAdministrador interfaz;
	
	public BarraEdicion(InterfazAdministrador interfaz) {
		this.interfaz = interfaz;
		
		addBoton("Mover", "mover.gif", "", 1);
		
		addBoton("Estado", "estado.gif", "", 2);
		
		addBoton("Transicion", "transicion.gif", "", 3);
		
		addBoton("Final", "final.gif", "", 4);
		
		setPreferredSize(new Dimension(30, 120));
	}
	
	private void addBoton(String nombre, String imagen, String toolTip, final int tipo) {
		JButton boton;
		
		URL imagenURL = ClassLoader.getSystemResource("imagenes/" + imagen);
		
		boton = new JButton();
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (tipo) {
				case 1:
					BarraEdicion.this.mover();
					break;
				case 2:
					BarraEdicion.this.insertarEstado();
					break;
				case 3:
					BarraEdicion.this.insertarTransicion();
					break;
				case 4:
					BarraEdicion.this.cambiarFinal();
					break;
				}
			}
		});
		
		
		boton.setToolTipText(toolTip);
		if (imagenURL != null) {
			boton.setIcon(new ImageIcon(imagenURL, nombre));
		} else {
			boton.setText(nombre);
		}
		
		
		add(boton);
	}

	protected void cambiarFinal() {
		if(interfaz.getPanelAF() != null)
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.SET_FINAL);
		
	}

	protected void insertarTransicion() {
		if (interfaz.getPanelAF() != null) 
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.INSERTAR_TRANSICION);
	}

	protected void insertarEstado() {
		if (interfaz.getPanelAF() != null) 
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.INSERTAR_ESTADO);
	}

	protected void mover() {
		if (interfaz.getPanelAF() != null) 
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.EDICION);
	}

	
	
}
