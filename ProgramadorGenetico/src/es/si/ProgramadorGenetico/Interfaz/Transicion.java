package es.si.ProgramadorGenetico.Interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.QuadCurve2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Transicion extends JComponent {

	public Estado origen;
	public Estado destino;
	public double probabilidad0;
	public double probabilidad1;
	public JLabel label;

	public Transicion () {
		
	}
	
	public Transicion (Estado origen, Estado destino, double probabilidad0, double probabilidad1) {
		this.origen = origen;
		this.destino = destino;
		this.probabilidad0 = probabilidad0;
		this.probabilidad1 = probabilidad1;
	}
	
	public Estado getOrigen () {
		return origen;
	}
	
	public Estado getDestino () {
		return destino;
	}
	
	public double getProbabilidad0 () {
		return probabilidad0;
	}
	
	public double getProbabilidad1 () {
		return probabilidad1;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setLabel (JLabel l) {
		label = l;
	}
	
	public void paintComponent (Graphics g) {		
		//Graphics2D g = (Graphics2D) panel.getGraphics();
		if (origen!=destino) {
			int radioArco = (int)origen.getRadio();
			double radio = origen.getRadio();
			//double incremento = 360/(double)(panel.getNumEstados());
			
			double ang = getAnguloEstados();
			//Point origenArco = new Point ((int)(origen.getCentro().getX()+Math.cos(ang)*origen.getRadio()), 
			//								(int)(origen.getCentro().getY()+Math.sin(ang)*origen.getRadio()));
			//Point destinoArco = new Point ((int)(destino.getCentro().getX()+Math.cos(ang)*destino.getRadio()),
			//								(int)(destino.getCentro().getY()+Math.sin(ang)*destino.getRadio()));
			
			Point origenArco = origen.getCentro();
			Point destinoArco = destino.getCentro();
			
			Point puntoMedio = getPuntoMedio(origenArco,destinoArco); 			
			Point puntoControl = getPuntoControlCurva(origenArco, destinoArco, puntoMedio, ang);
			Point centro = origen.getCentro();
			
			//utiles en el futurio
			int despX = (int)(centro.getX()+radio*Math.cos(Math.toRadians(ang)));
			int despY = (int)(centro.getY()-radio*Math.sin(Math.toRadians(ang)));
			
			
			Color colorTransicion = getColorArco();
			g.setColor(colorTransicion);
			QuadCurve2D q = new QuadCurve2D.Double();			
			q.setCurve(origen.getCentro().getX(), origen.getCentro().getY(),puntoControl.getX(), puntoControl.getY(), 
					destino.getCentro().getX(), destino.getCentro().getY());
			((Graphics2D)g).draw(q);			
			
			Point puntoValor = getPuntoValorCurva(origenArco, destinoArco, puntoMedio, ang);
			asignaValorLabel();
			label.setBounds((int)puntoValor.getX(),(int)puntoValor.getY(),20,20);
			
			/*
			double xPuntoValor = (double)(centro.getX()+(1.5*(radio*2))*Math.cos(Math.toRadians(ang)));
			double yPuntoValor = (double)(centro.getY()-(1.5*diametro)*Math.sin(Math.toRadians(ang)));
			*/							
		}
		else {
			
		}
		
	}
	
	public void asignaValorLabel() {
		if (probabilidad0 >= 0.1 && probabilidad1 >=0.1)
			label.setText("0,1");
		else if (probabilidad0 < 0.1 && probabilidad1 >= 0.1)
			label.setText("1");
		else if (probabilidad0 >= 0.1 && probabilidad1 < 0.1)
			label.setText("0");		
		
	}
	
	public double getAnguloEstados () {
		double xOrigen = origen.getCentro().getX();
		double yOrigen = origen.getCentro().getY();
		double xDestino = destino.getCentro().getX();
		double yDestino = destino.getCentro().getY();
		double catOpuesto = yOrigen - yDestino;
		double catContiguo = xDestino - xOrigen;
		double tgAng = catOpuesto / catContiguo;
		return (Math.toDegrees((Math.atan(tgAng))));
		
	}
	
	public Point getPuntoMedio (Point p1, Point p2) {
		int xMedio = (int) (p1.getX() + p2.getX())/2;
		int yMedio = (int)(p1.getY() + p2.getY())/2;
		return new Point (xMedio,yMedio);
	}
	
	/**
	 * Devuelve la coordenada x del punto de control del arco que representara la transicion
	 * @param i
	 * @param j
	 * @param angulo
	 * @param xMedio
	 * @param yMedio
	 * @return
	 */
	public Point getPuntoControlCurva (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {
		
		double xPuntoControl;
		double yPuntoControl;
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		
		if (cuadrante==1)
			ajusteX = 70*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = 70*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = 70*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = 70*Math.cos(Math.toRadians((90+angulo)));
		
		xPuntoControl = puntoMedio.getX()+ajusteX;		
		
		if (cuadrante==1)
			ajusteY = -70*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = -70*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = -70*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -70*Math.sin(Math.toRadians((90+angulo)));
			
		yPuntoControl = puntoMedio.getY()+ajusteY;
				
		return new Point ((int)xPuntoControl, (int)yPuntoControl);	
	
	}
	
	public Point getPuntoValorCurva (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {
		
		double xPuntoValor=0.0;
		double yPuntoValor=0.0;
		
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		
		if (cuadrante==1)
			ajusteX = 60*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = 60*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = 60*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = 60*Math.cos(Math.toRadians((90+angulo)));
		
		xPuntoValor = puntoMedio.getX()+ajusteX;		
		
		if (cuadrante==1)
			ajusteY = -60*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = -60*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = -60*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -60*Math.sin(Math.toRadians((90+angulo)));
			
		yPuntoValor = puntoMedio.getY()+ajusteY;
				
		return new Point ((int)xPuntoValor, (int)yPuntoValor);	
				
	}
	
	public Color getColorArco () {
		
		if (probabilidad0 >= 0.9 || probabilidad1 >=0.9)
			return Color.black;
		
		if (probabilidad0 < 0.1 && probabilidad1 < 0.9)
			return Color.red;
		
		if (probabilidad0 < 0.9 && probabilidad1 < 0.1)
			return Color.red;

		return Color.black;
	}
	
	public int getCuadrante () {
		if (origen.getCentro().getX() < destino.getCentro().getX() && 
				origen.getCentro().getY() >= destino.getCentro().getY())
			return 1;
		if (origen.getCentro().getX() >= destino.getCentro().getX() &&
				origen.getCentro().getY() > destino.getCentro().getY()) 
			return 2;
		if (origen.getCentro().getX() > destino.getCentro().getX() && 
				origen.getCentro().getY() <= destino.getCentro().getY())
			return 3;
		else
			return 4;
			
			
	}
	/*
	public double distanciaEstados () {
		double xOrigen = origen.getCentro().getX();
		double yOrigen = origen.getCentro().getY();
		double xDestino = destino.getCentro().getX();
		double yDestino = destino.getCentro().getY();
		
	}
	 */

}
