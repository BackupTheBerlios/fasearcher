package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import es.si.ProgramadorGenetico.Individuo;

public class PanelAF extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4846862696658836820L;
	
	private SubPanelAF subPanelAF;
	
	public PanelAF() {
		super();
		subPanelAF = new SubPanelAF();
		setSubPanel(subPanelAF);
	}
	
	public PanelAF(Individuo afp) {
		super();
		subPanelAF = new SubPanelAF(afp);
		setSubPanel(subPanelAF);
	}
	
	public PanelAF(float[][][] transiciones, float[] probabilidadFinal,
			int estados) {
		super();
		subPanelAF = new SubPanelAF(transiciones, probabilidadFinal, estados);
		setSubPanel(subPanelAF);
	}
	
	private void setSubPanel(SubPanelAF subPanelAF) {
		this.setViewportView(subPanelAF);
		this.setPreferredSize(new Dimension(500,500));
		center();
	}
	
	private void centerScroll(JScrollBar bar) {
		int max = bar.getMaximum();
		int min = bar.getMinimum();
		bar.setValue((max - min)/2 - (max - min)/8);
	}
	
	public void center() {
		centerScroll(this.getVerticalScrollBar());
		centerScroll(this.getHorizontalScrollBar());
	}
	
	public SubPanelAF getSubPanelAF () {
		return subPanelAF;
	}
}
