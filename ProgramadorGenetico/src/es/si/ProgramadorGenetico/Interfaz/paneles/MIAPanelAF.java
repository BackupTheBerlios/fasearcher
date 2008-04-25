package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import es.si.ProgramadorGenetico.Interfaz.componentes.Estado;
import es.si.ProgramadorGenetico.Interfaz.componentes.Transicion;

/**
 * MouseInputAdapter para el PanelAF, implementa toda la funcionalidad del rat�n
 * en el panel para la edici�n y manipulaci�n de AF.
 * 
 */
public class MIAPanelAF extends MouseInputAdapter {

	private PanelAF panel;
	
	/**
	 * Estado que se esta moviendo
	 * 
	 */
	private Estado estadoMovido;
	

	private Estado estadoInicioTransicion;
	
	
	public MIAPanelAF(PanelAF panel) {
		this.panel = panel;
		estadoMovido = null;
	}

	public void mouseDragged(MouseEvent e) {
		if (estadoMovido != null) {
			estadoMovido.actualizaPosicion(e.getPoint());
			actualizaDibujo();
		}
	}

	public void actualizaDibujo() {
		panel.repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (panel.isEditable()) {
			switch (panel.getModo()) {
			case PanelAF.INSERTAR_ESTADO: 
				mPInsertarEstado(e);
				break;
			case PanelAF.INSERTAR_TRANSICION:
				mPInsertarTransicion(e);
				break;
			case PanelAF.FINALIZAR_TRANSICION: 
				mPFinalizarTransicion(e);
				break;
			case PanelAF.SET_FINAL:
				mPSetFinal(e);
				break;
			case PanelAF.EDICION: 
				mPEdicion(e);
				break;
			default:
				mPEdicion(e);
			}
		}
		else
			mPEdicion(e);
	}
	
	public void mPInsertarEstado(MouseEvent e) {
		Point puntoClick;
		puntoClick = e.getPoint();
		String etiqueta = new String("Q" + panel.getNumEstados());
		JLabel label = new JLabel(etiqueta);
		Rectangle ajusteLabel = new Rectangle((int) (panel.getDiamEst() / 2) - 10,
				(int) (panel.getDiamEst() / 2) - 15, 30, 30);
		Estado nuevoE = new Estado(panel.getDiamEst(), ajusteLabel);
		nuevoE.setLabel(label);
		nuevoE.setPunto(puntoClick);
		nuevoE.setBoundsLabel(puntoClick.x, puntoClick.y);
		panel.addEstado(nuevoE);
		panel.addLabelEstado(nuevoE.getLabel());
		panel.paintComponent(panel.getGraphics());
	}
	
	
	public void mPInsertarTransicion(MouseEvent e) {
		Estado es = panel.buscaEstado(e.getPoint());
		if (es != null) {
			estadoInicioTransicion = es;
			panel.setModo(PanelAF.FINALIZAR_TRANSICION);
		}
		panel.paintComponent(panel.getGraphics());
	}
	
	public void mPFinalizarTransicion(MouseEvent e) {
		Estado estadoFinTransicion = panel.buscaEstado(e.getPoint());
		if (estadoFinTransicion != null) {
			String s = new String();
			do {
				s = JOptionPane
				.showInputDialog("Introduzca el nuevo valor de la transicion (0 o 1)");
			} while (s == null
					|| !((s.equals("0")) || (s.equals("1"))));
			int valor = Integer.valueOf(s);

			Transicion t = panel.buscaTransiciones(
					estadoInicioTransicion, estadoFinTransicion);
			if (t != null) {
				Transicion viejaT = panel.buscaTransiciones(valor,
						estadoInicioTransicion);
				if (viejaT != null) {
					viejaT.setProbabilidad(valor, 0);
					if (viejaT.getProbabilidad0() == 0
							&& viejaT.getProbabilidad1() == 0) {
						panel.removeLabel(viejaT.getLabel0());
						panel.removeLabel(viejaT.getLabel1());
						panel.getTransicionesList().remove(viejaT);

					}
				}
				t.setProbabilidad(valor, 1);
				t.setLabel(valor, new JLabel(Integer
						.toString(valor)));
			} else {
				Transicion viejaT = panel.buscaTransiciones(valor,
						estadoInicioTransicion);
				if (viejaT != null) {
					viejaT.setProbabilidad(valor, 0);
					if (viejaT.getProbabilidad0() == 0
							&& viejaT.getProbabilidad1() == 0) {
						panel.removeLabel(viejaT.getLabel0());
						panel.removeLabel(viejaT.getLabel1());
						panel.getTransicionesList().remove(viejaT);
					}
				}
				Transicion nuevaT = new Transicion(
						estadoInicioTransicion,
						estadoFinTransicion, 0, 0);
				nuevaT.setProbabilidad(valor, 1);
				nuevaT.setLabel(valor, new JLabel(Integer
						.toString(valor)));
				panel.addTransicion(nuevaT);
				// A�ADIR LA LABEL
				panel.add(panel.getTransicionesList().get(panel.getTransicionesList().size() - 1)
						.getLabel(valor));
			}
		}
		estadoInicioTransicion = null;
		panel.setModo(PanelAF.INSERTAR_TRANSICION);
		panel.paintComponent(panel.getGraphics());
	}
	
	public void mPSetFinal(MouseEvent e) {
		Estado es = panel.buscaEstado(e.getPoint());
		if (es != null) {
			es.setProbabilidadFinal(1.0f - es
						.getProbabilidadFinal());
		}
		panel.paintComponent(panel.getGraphics());
	}
	
	public void mPEdicion(MouseEvent e) {
		estadoMovido = panel.buscaEstado(e.getPoint());
		panel.paintComponent(panel.getGraphics());
	}
}