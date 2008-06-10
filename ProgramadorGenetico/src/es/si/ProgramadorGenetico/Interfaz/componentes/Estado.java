package es.si.ProgramadorGenetico.Interfaz.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Clase que representa un Estado en la interfaz grafica 
 *
 */
public class Estado extends JComponent {

	private static final long serialVersionUID = -604577784435087257L;
	/**
	 * Punto en el que se dibujara el estado
	 */
	private Point punto;
	/**
	 * Diametro con el que se dibujara el estado
	 */
	private float diametro;
	/**
	 * Label que indica el nombre del estado
	 */
	private JLabel label;
	/**
	 * Probabilidad de ser final del estado
	 */
	private float probabilidadFinal;
	/**
	 * Rectangulo que indica un ajuste de la label del estado
	 */
	private Rectangle ajusteLabel;
	/**
	 * Indice del numero de estados
	 */
	private int indice;

	/**
	 * Constructora sin parametros
	 */
	public Estado() {
		punto = new Point();
		diametro = 50;
		label = new JLabel();
	}

	/**
	 * Constructora que recibe un punto y el diametro
	 * @param punto
	 * @param diametro
	 * @param etiqueta
	 */
	public Estado(Point punto, int diametro, JLabel etiqueta) {
		this.punto = punto;
		this.label = etiqueta;
		this.diametro = diametro;
	}

	/**
	 * Constructora que inicializa el diametro al pasado por parametro
	 * y las coordenadas de la label 
	 * @param diametro
	 * @param coordenadasLabel
	 */
	public Estado(int diametro, Rectangle coordenadasLabel) {
		punto = new Point();
		label = new JLabel();
		this.diametro = diametro;
		ajusteLabel = (Rectangle) coordenadasLabel.clone();
	}

	/**
	 * Actualiza la label a la pasada por parametro
	 * @param label
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}
	/**
	 * Devuelve la label
	 * 
	 */
	public JLabel getLabel() {
		return label;
	}
	/**
	 * Actualiza el punto al pasado por defecto
	 * @param p
	 */
	public void setPunto(Point p) {
		this.punto = p;
	}
	/**
	 * Actualiza el diametro al pasado por parametro
	 * @param diam
	 */
	public void setDiametro(float diam) {
		this.diametro = diam;
	}
	/**
	 * Devuelve el diametro
	 * @return
	 */
	public double getDiametro() {
		return diametro;
	}
	/**
	 * Devuelve el punto que esta en el centro del estado
	 * @return
	 */
	public Point getCentro() {
		int x = (int) (punto.getX() + diametro / 2.0);
		int y = (int) (punto.getY() + diametro / 2.0);
		return new Point(x, y);
	}
	/**
	 * Devuelve el radio
	 * @return
	 */
	public double getRadio() {
		return diametro / 2.0;
	}
	/**
	 * Devuelve el punto
	 * @return
	 */
	public Point getPunto() {
		return punto;
	}
	/**
	 * Metodo paintComponent que dibuja 
	 */
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
	/**
	 * Devuelve si el estado ha sido pulsado al hacer clic en el punto p
	 * @param p
	 * @return
	 */
	public boolean getPulsado(Point p) {
		if ((getCentro().distance(p)) <= getRadio())
			return true;
		return false;

	}
	/**
	 * Actualiza la posicion del estado por el nuevo punto pasado
	 * por parametro
	 * @param nuevoPunto
	 */
	public void actualizaPosicion(Point nuevoPunto) {
		punto.setLocation(nuevoPunto.x - getRadio(), nuevoPunto.y - getRadio());
		label.setBounds((int) (punto.getX() + ajusteLabel.getX()),
						(int) (punto.getY() + ajusteLabel.getY()), 
						label.getWidth(), label.getHeight());
	}
	/**
	 * Actualiza las coordenadas de la label 
	 * @param x
	 * @param y
	 */
	public void setBoundsLabel(int x, int y) {
		label.setBounds((int) (x + ajusteLabel.getX()),
						(int) (y + ajusteLabel.getY()),
						(int) ajusteLabel.getWidth(), 
						(int) ajusteLabel.getHeight());
	}
	/**
	 * Devuelve si el estado es final
	 * @return
	 */
	public boolean esFinal() {
		if (probabilidadFinal > 0.9)
			return true;
		return false;
	}
	/**
	 * Actualiza la probabilidad de que el estado sea final
	 * @param prob
	 */
	public void setProbabilidadFinal(float prob) {
		probabilidadFinal = prob;
	}
	/**
	 * Ajusta el rectangulo que ajusta la label
	 * @param r
	 */
	public void setAjusteLabel(Rectangle r) {
		ajusteLabel = r;
	}
	/**
	 * Devuelve la probabilidad de que el estado sea final
	 * @return
	 */
	public float getProbabilidadFinal() {
		return probabilidadFinal;
	}
	/**
	 * Actualiza el indice del estado
	 * @param ind
	 */
	public void setIndice(int ind) {
		indice = ind;
	}
	/**
	 * Devuelve el indice del estado
	 * @return
	 */
	public int getIndice() {
		return indice;
	}

}
