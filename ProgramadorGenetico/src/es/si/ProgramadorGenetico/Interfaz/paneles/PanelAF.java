package es.si.ProgramadorGenetico.Interfaz.paneles;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import es.si.ProgramadorGenetico.Individuo;
/**
 * Panel que contiene el dibujo del automata
 *
 */
public class PanelAF extends JScrollPane {

	private static final long serialVersionUID = 4846862696658836820L;
	/**
	 * Subpanel que contiene el dibujo
	 */
	private SubPanelAF subPanelAF;
	/**
	 * Constructora que construye el subpanel
	 */
	public PanelAF() {
		super();
		subPanelAF = new SubPanelAF();
		setSubPanel(subPanelAF);
	}
	/**
	 * Constructora que dibuja el AFP pasado por parametro
	 * @param afp
	 */
	public PanelAF(Individuo afp) {
		super();
		subPanelAF = new SubPanelAF(afp);
		setSubPanel(subPanelAF);
	}
	/**
	 * Constructora que dibuja el automata dado por los arrays
	 * pasados por parametro
	 * @param transiciones
	 * @param probabilidadFinal
	 * @param estados
	 */
	public PanelAF(float[][][] transiciones, float[] probabilidadFinal,
			int estados) {
		super();
		subPanelAF = new SubPanelAF(transiciones, probabilidadFinal, estados);
		setSubPanel(subPanelAF);
	}
	/**
	 * Actualiza el subpanel
	 * @param subPanelAF
	 */
	private void setSubPanel(SubPanelAF subPanelAF) {
		this.setViewportView(subPanelAF);
		center();
	}
	/**
	 * Centra el scroll pasado por parametro
	 */
	private void centerScroll(JScrollBar bar) {
		if (bar != null) {
			int max = bar.getMaximum();
			int min = bar.getMinimum();
			bar.setValue((max - min)/2 - (max - min)/8);
		}
	}
	/**
	 * Centra todos los scrolls
	 */
	public void center() {
		centerScroll(this.getHorizontalScrollBar());
		centerScroll(this.getVerticalScrollBar());
		centerScroll(this.getHorizontalScrollBar());
	}
	/**
	 * Devuelve el subpanel
	 * @return
	 */
	public SubPanelAF getSubPanelAF () {
		return subPanelAF;
	}
	
}
