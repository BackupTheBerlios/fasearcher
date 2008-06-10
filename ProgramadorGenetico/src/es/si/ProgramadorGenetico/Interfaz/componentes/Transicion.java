package es.si.ProgramadorGenetico.Interfaz.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.QuadCurve2D;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Clase que representa graficamente una transicion de un automata
 *
 */
public class Transicion extends JComponent {

	private static final long serialVersionUID = -3482013995586640495L;
	/**
	 * El estado orgien de la transicion
	 */
	public Estado origen;
	/**
	 * El estado destino de la transicion
	 */
	public Estado destino;
	/**
	 * Probabilidad de que el 0 vaya de origen a destino
	 */
	public float probabilidad0;
	/**
	 * Probabilidad de que el 1 vaya de origen a destino
	 */
	public float probabilidad1;
	/**
	 * Label que pinta el 0
	 */
	public JLabel label0;
	/**
	 * Label que pinta el 1
	 */
	public JLabel label1;
	/**
	 * Valor que indica si se pinta el 0
	 */
	private boolean pintar0;
	/**
	 * Valor que indica si se pinta el 1
	 */
	private boolean pintar1;

	/**
	 * Constructora por defecto
	 */
	public Transicion() {

	}
	/**
	 * Constructora que actualiza los valores a los pasados por parametro
	 * @param origen
	 * @param destino
	 * @param probabilidad0
	 * @param probabilidad1
	 */
	public Transicion(Estado origen, Estado destino, float probabilidad0,
			float probabilidad1) {
		this.origen = origen;
		this.destino = destino;
		this.probabilidad0 = probabilidad0;
		this.probabilidad1 = probabilidad1;
		label0 = new JLabel();
		label1 = new JLabel();
	}
	/**
	 * Devuelve el estado origen de la transicion
	 * @return
	 */
	public Estado getOrigen() {
		return origen;
	}
	/**
	 * Devuelve el estado destino de la transicion
	 * @return
	 */
	public Estado getDestino() {
		return destino;
	}
	/**
	 * Devuelve la probabilidad de que este la transicion del 0
	 * @return
	 */
	public float getProbabilidad0() {
		return probabilidad0;
	}
	/**
	 * Devuelve la probabilidad de que este la transicion del 1
	 * @return
	 */
	public float getProbabilidad1() {
		return probabilidad1;
	}
	/**
	 * Devuelve la label del 0 
	 * @return
	 */
	public JLabel getLabel0() {
		return label0;
	}
	/**
	 * Devuelve la label del 1
	 * @return
	 */
	public JLabel getLabel1() {
		return label1;
	}
	/**
	 * Actualiza el valor de la label0
	 * @param l
	 */
	public void setLabel0(JLabel l) {
		label0 = l;
	}
	/**
	 * Actualiza el valor de la label1
	 * @param l
	 */
	public void setLabel1(JLabel l) {
		label1 = l;
	}
	/**
	 * Metodo paintComponent que pinta la transicion
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double radio = origen.getRadio();
		double ang = getAnguloEstados();
		if (origen != destino) {
			pintarOringenDistintoDestino(g, radio, ang);
		} else {
			pintarOrigenIgualDestino(g, radio, ang);
		}
	}
	/**
	 * Pinta las transiciones cuando el origen es distinto del destino
	 * @param g
	 * @param radio
	 * @param ang
	 */
	private void pintarOringenDistintoDestino (Graphics g, double radio, double ang) {
		Point origenArco = new Point(
				(int) (origen.getCentro().getX() + Math.cos(Math
						.toRadians(ang + 22.5))
						* origen.getRadio()), (int) (origen.getCentro()
						.getY() - Math.sin(Math.toRadians(ang + 22.5))
						* origen.getRadio()));
		Point destinoArco = new Point(
				(int) (destino.getCentro().getX() + Math.cos(Math
						.toRadians(180 + ang - 22.5))
						* destino.getRadio()), (int) (destino.getCentro()
						.getY() - Math
						.sin(Math.toRadians(ang + 180 - 22.5))
						* destino.getRadio()));
		Point puntoMedio = getPuntoMedio(origenArco, destinoArco);

		if (pintar0) {
			Point puntoControl0 = getPuntoControlCurva0(origenArco,
					destinoArco, puntoMedio, ang);
			Color colorTransicion0 = getColorArco0();
			g.setColor(colorTransicion0);
			QuadCurve2D curva0 = new QuadCurve2D.Double();
			curva0.setCurve(origenArco.getX(), origenArco.getY(),
					puntoControl0.getX(), puntoControl0.getY(), destinoArco
							.getX(), destinoArco.getY());
			((Graphics2D) g).draw(curva0);
			Polygon triangulo0 = calculaFlecha(destinoArco, puntoControl0);
			g.drawPolygon(triangulo0);
			g.fillPolygon(triangulo0);
			Point puntoValor0 = getPuntoValorCurva0(origenArco,
					destinoArco, puntoMedio, ang);
			label0.setText("0");
			label0.setBounds((int) puntoValor0.getX() - 10,
					(int) puntoValor0.getY() - 10, 10, 10);
		}
		if (pintar1) {
			Point puntoControl1 = getPuntoControlCurva1(origenArco,
					destinoArco, puntoMedio, ang);
			Color colorTransicion1 = getColorArco1();
			g.setColor(colorTransicion1);
			QuadCurve2D curva1 = new QuadCurve2D.Double();
			curva1.setCurve(origenArco.getX(), origenArco.getY(),
					puntoControl1.getX(), puntoControl1.getY(), destinoArco
							.getX(), destinoArco.getY());
			((Graphics2D) g).draw(curva1);
			Polygon triangulo1 = calculaFlecha(destinoArco, puntoControl1);
			g.drawPolygon(triangulo1);
			g.fillPolygon(triangulo1);
			Point puntoValor1 = getPuntoValorCurva1(origenArco,
					destinoArco, puntoMedio, ang);
			label1.setText("1");
			label1.setBounds((int) puntoValor1.getX() - 10,
					(int) puntoValor1.getY() - 10, 10, 10);
		}
	}

	/**
	 * Pinta las transiciones cuando el origen es igual al destino
	 * @param g
	 * @param radio
	 * @param ang
	 */
	private void pintarOrigenIgualDestino(Graphics g, double radio, double ang) {
		double recto = Math.toRadians(90);
		if (pintar0) {
			int despX = (int) (origen.getCentro().getX() + radio
					* Math.cos(recto));
			int despY = (int) (origen.getCentro().getY() - radio
					* Math.sin(recto));
			int radioArco = (int) origen.getRadio();
			Color colorTransicion0 = getColorArco0();
			g.setColor(colorTransicion0);
			g.drawOval(despX - radioArco / 2, despY - radioArco / 2,
					radioArco, radioArco);
			label0.setText("0");
			label0.setBounds(despX, (int) (despY - (radioArco * 1.5)), 20,
					20);
		}
		if (pintar1) {
			int despX = (int) (origen.getCentro().getX() + 2 * radio
					* Math.cos(recto));
			int despY = (int) (origen.getCentro().getY() - 2 * radio
					* Math.sin(recto));
			int radioArco = (int) origen.getRadio() * 2;
			Color colorTransicion1 = getColorArco1();
			g.setColor(colorTransicion1);
			g.drawOval(despX - radioArco / 2, despY - radioArco / 3 + 5,
					radioArco, radioArco);
			label1.setText("1");
			label1
					.setBounds(despX, (int) (despY - (radioArco) + 8), 20,
							20);
		}
	}
	/**
	 * Devuelve el angulo que forman los estados
	 * @return
	 */
	private double getAnguloEstados() {
		double xOrigen = origen.getCentro().getX();
		double yOrigen = origen.getCentro().getY();
		double xDestino = destino.getCentro().getX();
		double yDestino = destino.getCentro().getY();
		double catContiguo = xDestino - xOrigen;
		double catOpuesto = yOrigen - yDestino;
		double tgAng = catOpuesto / catContiguo;
		switch(getCuadrante()) {
		case 1:
			return (Math.toDegrees((Math.atan(tgAng))));
		case 2:
			return (180 + Math.toDegrees((Math.atan(tgAng))));
		case 3:
			return (180 + Math.toDegrees((Math.atan(tgAng))));
		default:
			return (360 + Math.toDegrees((Math.atan(tgAng))));
		}		
	}
	/**
	 * Devuelve el punto medio de los puntos pasados por parametro
	 * @param p1
	 * @param p2
	 * @return
	 */
	private Point getPuntoMedio(Point p1, Point p2) {
		int xMedio = (int) (p1.getX() + p2.getX()) / 2;
		int yMedio = (int) (p1.getY() + p2.getY()) / 2;
		return new Point(xMedio, yMedio);
	}
	/**
	 * Devuelve un punto para trazar el arco que representa la transicion del 0 del 
	 * estado origen al destino, dado por los parametros 
	 * @param origenArco
	 * @param destinoArco
	 * @param puntoMedio
	 * @param angulo
	 * @return
	 */
	private Point getPuntoControlCurva0(Point origenArco, Point destinoArco,
			Point puntoMedio, double angulo) {
		double xPuntoControl;
		double yPuntoControl;
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 70;

		switch(cuadrante) {
		case 1:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 2:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 3:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		default:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
		}
		
		xPuntoControl = puntoMedio.getX() + ajusteX;
		yPuntoControl = puntoMedio.getY() + ajusteY;
		return new Point((int) xPuntoControl, (int) yPuntoControl);
	}
	/**
	 * Devuelve un punto para trazar el arco que representa la transicion del 1 del 
	 * estado origen al destino, dado por los parametros 
	 * @param origenArco
	 * @param destinoArco
	 * @param puntoMedio
	 * @param angulo
	 * @return
	 */
	public Point getPuntoControlCurva1(Point origenArco, Point destinoArco,
			Point puntoMedio, double angulo) {
		double xPuntoControl;
		double yPuntoControl;
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 140;

		switch(cuadrante) {
		case 1:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 2:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 3:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		default:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));			
		}
		
		xPuntoControl = puntoMedio.getX() + ajusteX;
		yPuntoControl = puntoMedio.getY() + ajusteY;

		return new Point((int) xPuntoControl, (int) yPuntoControl);
	}
	/**
	 * Devuelve el punto donde se representa el valor para la transicion del 0,
	 * dado por los parametros
	 * @param origenArco
	 * @param destinoArco
	 * @param puntoMedio
	 * @param angulo
	 * @return
	 */
	public Point getPuntoValorCurva0(Point origenArco, Point destinoArco,
			Point puntoMedio, double angulo) {

		double xPuntoValor = 0.0;
		double yPuntoValor = 0.0;

		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 50;

		switch(cuadrante) {
		case 1:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 2:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 3:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		default:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));		
		}
		
		xPuntoValor = puntoMedio.getX() + ajusteX;
		yPuntoValor = puntoMedio.getY() + ajusteY;

		return new Point((int) xPuntoValor, (int) yPuntoValor);

	}
	/**
	 * Devuelve el punto donde se representa el valor para la transicion del 1,
	 * dado por los parametros
	 * @param origenArco
	 * @param destinoArco
	 * @param puntoMedio
	 * @param angulo
	 * @return
	 */
	public Point getPuntoValorCurva1(Point origenArco, Point destinoArco,
			Point puntoMedio, double angulo) {

		double xPuntoValor = 0.0;
		double yPuntoValor = 0.0;

		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 90;

		switch(cuadrante) {
		case 1:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 2:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		case 3:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
			break;
		default:
			ajusteX = valorAjuste * Math.cos(Math.toRadians((90 + angulo)));
			ajusteY = -valorAjuste * Math.sin(Math.toRadians((90 + angulo)));
		}

		xPuntoValor = puntoMedio.getX() + ajusteX;
		yPuntoValor = puntoMedio.getY() + ajusteY;

		return new Point((int) xPuntoValor, (int) yPuntoValor);

	}
	/**
	 * Devuelve el color del arco que representa al 0
	 * 
	 */
	public Color getColorArco0() {
		int valorGris = (int) ((1 - probabilidad0) * 255);
		Color c = new Color(valorGris, valorGris, valorGris);
		return c;
	}
	/**
	 * Devuelve el color del arco que representa al 1
	 * @return
	 */
	public Color getColorArco1() {
		int valorGris = (int) ((1 - probabilidad1) * 255);
		Color c = new Color(valorGris, valorGris, valorGris);
		return c;
	}
	/**
	 * Devuelve el cuadrante en el que se encuentra el segundo estado
	 * Esto facilita las opciones de dibujo
	 * @return
	 */
	public int getCuadrante() {
		if (origen.getCentro().getX() <= destino.getCentro().getX()
				&& origen.getCentro().getY() >= destino.getCentro().getY())
			return 1;
		if (origen.getCentro().getX() >= destino.getCentro().getX()
				&& origen.getCentro().getY() >= destino.getCentro().getY())
			return 2;
		if (origen.getCentro().getX() > destino.getCentro().getX()
				&& origen.getCentro().getY() <= destino.getCentro().getY())
			return 3;
		else
			return 4;
	}
	/**
	 * Devuelve el cuadrante en el que esta el segundo punto pasado por parametro
	 * con respecto al primero pasado por parametro
	 * @param pOrigen
	 * @param pDestino
	 * @return
	 */
	public int getCuadrantePuntos(Point pOrigen, Point pDestino) {
		if (pOrigen.getX() <= pDestino.getX()
				&& pOrigen.getY() >= pDestino.getY())
			return 1;
		if (pOrigen.getX() >= pDestino.getX()
				&& pOrigen.getY() >= pDestino.getY())
			return 2;
		if (pOrigen.getX() > pDestino.getX()
				&& pOrigen.getY() <= pDestino.getY())
			return 3;
		else
			return 4;
	}
	/**
	 * Devuelve el angulo que forman los puntos pasados por parametro
	 * @param pOrigen
	 * @param pDestino
	 * @return
	 */
	public double getAnguloPuntos(Point pOrigen, Point pDestino) {
		double xOrigen = pOrigen.getX();
		double yOrigen = pOrigen.getY();
		double xDestino = pDestino.getX();
		double yDestino = pDestino.getY();
		double catContiguo = xDestino - xOrigen;
		double catOpuesto = yOrigen - yDestino;
		double tgAng = catOpuesto / catContiguo;
		int cuadrante = getCuadrantePuntos(pOrigen, pDestino);
		// System.out.println(Math.toDegrees((Math.atan(tgAng))));
		if (cuadrante == 1) {
			return (Math.toDegrees((Math.atan(tgAng))));
		} else if (cuadrante == 2) {
			return (180 + Math.toDegrees((Math.atan(tgAng))));
		} else if (cuadrante == 3) {
			return (180 + Math.toDegrees((Math.atan(tgAng))));
		} else { // if (cuadrante == 4)
			return (360 + Math.toDegrees((Math.atan(tgAng))));
		}
	}
	/**
	 * Metodo que crea el triangulo de la flecha de la transicion
	 * @param destinoArco
	 * @param puntoControl
	 * @return
	 */
	public Polygon calculaFlecha(Point destinoArco, Point puntoControl) {
		double largoFlecha = 15;
		double angPunta = 15;
		double angulo = getAnguloPuntos(destino.getCentro(), puntoControl);
		double p1x = destinoArco.getX() + largoFlecha
				* Math.cos(Math.toRadians(angulo - angPunta));
		double p1y = destinoArco.getY() - largoFlecha
				* Math.sin(Math.toRadians(angulo - angPunta));
		double p2x = destinoArco.getX() + largoFlecha
				* Math.cos(Math.toRadians(angulo + angPunta));
		double p2y = destinoArco.getY() - largoFlecha
				* Math.sin(Math.toRadians(angulo + angPunta));
		int[] xTriangulo = new int[3];
		int[] yTriangulo = new int[3];
		xTriangulo[0] = (int) destinoArco.getX();
		yTriangulo[0] = (int) destinoArco.getY();
		xTriangulo[1] = (int) p1x;
		yTriangulo[1] = (int) p1y;
		xTriangulo[2] = (int) p2x;
		yTriangulo[2] = (int) p2y;
		Polygon triangulo = new Polygon(xTriangulo, yTriangulo, 3);
		return triangulo;
	}
	/**
	 * Otro metodo que calcula la flecha de la transicion
	 * @param destinoArco
	 * @return
	 */
	public Polygon calculaFlecha2(Point destinoArco) {
		double angulo = getAnguloPuntos(destino.getCentro(), destinoArco);
		double p1x = destinoArco.getX() + 12
				* Math.cos(Math.toRadians(angulo - 7));
		double p1y = destinoArco.getY() - 12
				* Math.sin(Math.toRadians(angulo - 7));
		double p2x = destinoArco.getX() + 12
				* Math.cos(Math.toRadians(angulo + 7));
		double p2y = destinoArco.getY() - 12
				* Math.sin(Math.toRadians(angulo + 7));
		int[] xTriangulo = new int[3];
		int[] yTriangulo = new int[3];
		xTriangulo[0] = (int) destinoArco.getX();
		yTriangulo[0] = (int) destinoArco.getY();
		xTriangulo[1] = (int) p1x;
		yTriangulo[1] = (int) p1y;
		xTriangulo[2] = (int) p2x;
		yTriangulo[2] = (int) p2y;
		Polygon triangulo = new Polygon(xTriangulo, yTriangulo, 3);
		return triangulo;
	}
	/**
	 * Metodo que se encarga de llamar a los metodos de pintar correspondientes
	 * @param trans0
	 * @param trans1
	 * @param g
	 */
	public void pinta(boolean trans0, boolean trans1, Graphics g) {
		pintar0 = trans0;
		pintar1 = trans1;
		paintComponent(g);
		label0.repaint();
		label1.repaint();
	}
	/**
	 * Metodo equals que comprueba si dos transiciones son iguales
	 * @param t
	 * @return
	 */
	public boolean equals(Transicion t) {
		if ((origen == t.origen) && (destino == t.destino)) {
			if (probabilidad0 == t.getProbabilidad0()
					&& probabilidad1 == t.getProbabilidad1())
				return true;
		}
		return false;
	}
	/**
	 * Actualiza la probabilidad de que se de la transicion 0
	 * @param probabilidad0
	 */
	public void setProbabilidad0(float probabilidad0) {
		this.probabilidad0 = probabilidad0;
	}
	/**
	 * Actualiza la probabilidad de que se de la transicion 1
	 * @param probabilidad1
	 */
	public void setProbabilidad1(float probabilidad1) {
		this.probabilidad1 = probabilidad1;
	}
	/**
	 * Actualiza la probabilidad del valor dado por parametro a la
	 * probabilidad que tambien viene por parametro
	 * @param valor
	 * @param probabilidad
	 */
	public void setProbabilidad(int valor, float probabilidad) {
		if (valor == 0)
			probabilidad0 = probabilidad;
		if (valor == 1)
			probabilidad1 = probabilidad;
	}
	/**
	 * Actualiza la label al valor indicado
	 * @param valor
	 * @param label
	 */
	public void setLabel(int valor, JLabel label) {
		if (valor == 0)
			label0 = label;
		if (valor == 1)
			label1 = label;
	}
	/**
	 * Devuelve la label pedida
	 * @param valor
	 * @return
	 */
	public JLabel getLabel(int valor) {
		return (valor == 0 ? label0 : label1);
	}

}
