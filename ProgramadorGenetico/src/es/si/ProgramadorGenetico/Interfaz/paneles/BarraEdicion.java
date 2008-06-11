package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import es.si.ProgramadorGenetico.Interfaz.InterfazAdministrador;
/**
 * Barra de edicion de dibujo. Se trata de la 
 * barra de herramientas que contiene los elementos
 * para dibujar
 *
 */
public class BarraEdicion extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3025743241417145071L;
	/**
	 * Interfaz del administrador
	 */
	private InterfazAdministrador interfaz;
	/**
	 * Constructora que añade los botones necesarios
	 * @param interfaz
	 */
	public BarraEdicion(InterfazAdministrador interfaz) {
		this.interfaz = interfaz;
		
		addBoton("Mover", "mover.gif", "", 1);
		
		addBoton("Estado", "estado.gif", "", 2);
		
		addBoton("Transicion", "transicion.gif", "", 3);
		
		addBoton("Final", "final.gif", "", 4);
		
		setPreferredSize(new Dimension(30, 120));
	}
	/**
	 * Añade un boton segun los parametros
	 * @param nombre
	 * @param imagen
	 * @param toolTip
	 * @param tipo
	 */
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
	/**
	 * Cambia un estado a final o no final
	 */
	protected void cambiarFinal() {
		if(interfaz.getPanelAF() != null)
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.SET_FINAL);
		
	}
	/**
	 * Inserta una transicion entre dos estados
	 * 
	 */
	protected void insertarTransicion() {
		if (interfaz.getPanelAF() != null) 
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.INSERTAR_TRANSICION);
	}
	/**
	 * Inserta un estado
	 */
	protected void insertarEstado() {
		if (interfaz.getPanelAF() != null) 
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.INSERTAR_ESTADO);
	}
	/**
	 * Entra en el modo en el que se pueden mover estados 
	 */
	protected void mover() {
		if (interfaz.getPanelAF() != null) 
			interfaz.getPanelAF().getSubPanelAF().setModo(SubPanelAF.EDICION);
	}

	
	
}
