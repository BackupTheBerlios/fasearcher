package es.si.ProgramadorGenetico.Interfaz.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Estado extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -604577784435087257L;

	private Point punto;
	private float diametro;
	private JLabel label;
	private float probabilidadFinal;
	private Rectangle ajusteLabel;
	private int indice;

	public Estado() {
		punto = new Point();
		diametro = 50;
		label = new JLabel();
	}

	public Estado(Point punto, int diametro, JLabel etiqueta) {
		this.punto = punto;
		this.label = etiqueta;
		this.diametro = diametro;
	}

	public Estado(int diametro, Rectangle coordenadasLabel) {
		punto = new Point();
		label = new JLabel();
		this.diametro = diametro;
		ajusteLabel = (Rectangle) coordenadasLabel.clone();
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setPunto(Point p) {
		this.punto = p;
	}

	public void setDiametro(float diam) {
		this.diametro = diam;
	}

	public double getDiametro() {
		return diametro;
	}

	public Point getCentro() {
		int x = (int) (punto.getX() + diametro / 2.0);
		int y = (int) (punto.getY() + diametro / 2.0);
		return new Point(x, y);
	}

	public double getRadio() {
		return diametro / 2.0;
	}

	public Point getPunto() {
		return punto;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillOval((int) punto.getX(), (int) punto.getY(),
				   (int) diametro, (int) diametro);
		g.drawOval((int) punto.getX(), (int) punto.getY(),
				   (int) diametro, (int) diametro);
		g.setColor(Color.black);
		g.drawOval((int) punto.getX(), (int) punto.getY(),
				   (int) diametro, (int) diametro);
		if (esFinal()) {
			g.drawOval((int) punto.getX() + 4, (int) punto.getY() + 4,
					   (int) (diametro - 8), (int) (diametro - 8));
		}
		label.repaint();
	}

	public boolean getPulsado(Point p) {
		if ((getCentro().distance(p)) <= getRadio())
			return true;
		return false;

	}

	public void actualizaPosicion(Point nuevoPunto) {
		punto.setLocation(nuevoPunto.x - getRadio(), nuevoPunto.y - getRadio());
		label.setBounds((int) (punto.getX() + ajusteLabel.getX()),
						(int) (punto.getY() + ajusteLabel.getY()), 
						label.getWidth(), label.getHeight());
	}

	public void setBoundsLabel(int x, int y) {
		label.setBounds((int) (x + ajusteLabel.getX()),
						(int) (y + ajusteLabel.getY()),
						(int) ajusteLabel.getWidth(), 
						(int) ajusteLabel.getHeight());
	}

	public boolean esFinal() {
		if (probabilidadFinal > 0.9)
			return true;
		return false;
	}

	public void setProbabilidadFinal(float prob) {
		probabilidadFinal = prob;
	}

	public void setAjusteLabel(Rectangle r) {
		ajusteLabel = r;
	}

	public float getProbabilidadFinal() {
		return probabilidadFinal;
	}

	public void setIndice(int ind) {
		indice = ind;
	}

	public int getIndice() {
		return indice;
	}

}
