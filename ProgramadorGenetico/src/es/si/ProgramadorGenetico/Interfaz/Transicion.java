package es.si.ProgramadorGenetico.Interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
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
		double radio = origen.getRadio();
		double ang = getAnguloEstados();
		if (origen!=destino) {
			
			Point origenArco = new Point ((int)(origen.getCentro().getX()+Math.cos(Math.toRadians(ang+22.5))*origen.getRadio()), 
											(int)(origen.getCentro().getY()-Math.sin(Math.toRadians(ang+22.5))*origen.getRadio()));	
			Point destinoArco = new Point ((int)(destino.getCentro().getX()+Math.cos(Math.toRadians(180+ang-22.5))*destino.getRadio()),
											(int)(destino.getCentro().getY()-Math.sin(Math.toRadians(ang+180-22.5))*destino.getRadio()));
			Point puntoMedio = getPuntoMedio(origenArco,destinoArco); 			
			Point puntoControl = getPuntoControlCurva3(origenArco, destinoArco, puntoMedio, ang);
			Point centro = origen.getCentro();			
			Color colorTransicion = getColorArco();
			g.setColor(colorTransicion);
			QuadCurve2D q = new QuadCurve2D.Double();			
			q.setCurve(origenArco.getX(), origenArco.getY(),puntoControl.getX(), puntoControl.getY(), 
					destinoArco.getX(), destinoArco.getY());
			((Graphics2D)g).draw(q);			
			Polygon triangulo = calculaFlecha(destinoArco,puntoControl);
			g.drawPolygon(triangulo);
			g.fillPolygon(triangulo);			
			Point puntoValor = getPuntoValorCurva3(origenArco, destinoArco, puntoMedio, ang);
			asignaValorLabel();
			label.setBounds((int)puntoValor.getX(),(int)puntoValor.getY(),20,20);															
		}
		else {
			int despX = (int)(origen.getCentro().getX()+radio*Math.cos(Math.toRadians(90)));
			int despY = (int)(origen.getCentro().getY()-radio*Math.sin(Math.toRadians(90)));
			int radioArco = (int)origen.getRadio();
			Color colorTransicion = getColorArco();
			g.setColor(colorTransicion);						
			g.drawOval(despX-radioArco/2, despY-radioArco/2, radioArco, radioArco);
			asignaValorLabel();
			label.setBounds(despX,(int)(despY-(radioArco*1.5)),20,20);
			
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
		double radio = origen.getRadio();		
		double catContiguo = xDestino - xOrigen;
		double catOpuesto = yOrigen - yDestino;
		double tgAng = catOpuesto / catContiguo;
		int cuadrante = getCuadrante();
		//System.out.println(Math.toDegrees((Math.atan(tgAng))));
		if (cuadrante==1) {			
			return (Math.toDegrees((Math.atan(tgAng))));
		}
		else if (cuadrante == 2) {
			return (180+Math.toDegrees((Math.atan(tgAng))));
		}
		else if (cuadrante == 3) {
			return (180+Math.toDegrees((Math.atan(tgAng))));
		}
		else { //if (cuadrante == 4)
			return (360+Math.toDegrees((Math.atan(tgAng))));
		}
		/*double xOrigen = origen.getCentro().getX();
		double yOrigen = origen.getCentro().getY();
		double xDestino = destino.getCentro().getX();
		double yDestino = destino.getCentro().getY();
		double catOpuesto = yOrigen - yDestino;
		double catContiguo = xDestino - xOrigen;
		double tgAng = catOpuesto / catContiguo;
		return (Math.toDegrees((Math.atan(tgAng))));
		*/
		
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
	
	public Point getPuntoControlCurva2 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {
		
		double xPuntoControl;
		double yPuntoControl;
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		
		if (cuadrante==1)
			ajusteX = 70*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = -70*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = -70*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = 70*Math.cos(Math.toRadians((90+angulo)));
		
		xPuntoControl = puntoMedio.getX()+ajusteX;		
		
		if (cuadrante==1)
			ajusteY = -70*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = 70*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = 70*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -70*Math.sin(Math.toRadians((90+angulo)));
			
		yPuntoControl = puntoMedio.getY()+ajusteY;
				
		return new Point ((int)xPuntoControl, (int)yPuntoControl);	
	
	}
	
	public Point getPuntoControlCurva3 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {
		
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
	
	public Point getPuntoValorCurva2 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {
		
		double xPuntoValor=0.0;
		double yPuntoValor=0.0;
		
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		
		if (cuadrante==1)
			ajusteX = 60*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = -60*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = -60*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = 60*Math.cos(Math.toRadians((90+angulo)));
		
		xPuntoValor = puntoMedio.getX()+ajusteX;		
		
		if (cuadrante==1)
			ajusteY = -60*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = 60*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = 60*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -60*Math.sin(Math.toRadians((90+angulo)));
			
		yPuntoValor = puntoMedio.getY()+ajusteY;
				
		return new Point ((int)xPuntoValor, (int)yPuntoValor);	
				
	}

	public Point getPuntoValorCurva3 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {
	
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
		if (origen.getCentro().getX() <= destino.getCentro().getX() && 
				origen.getCentro().getY() >= destino.getCentro().getY())
			return 1;
		if (origen.getCentro().getX() >= destino.getCentro().getX() &&
				origen.getCentro().getY() >= destino.getCentro().getY()) 
			return 2;
		if (origen.getCentro().getX() > destino.getCentro().getX() && 
				origen.getCentro().getY() <= destino.getCentro().getY())
			return 3;
		else
			return 4;				
	}
	
	public int getCuadrantePuntos (Point pOrigen, Point pDestino) {
		if (pOrigen.getX() <= pDestino.getX() && 
				pOrigen.getY() >= pDestino.getY())
			return 1;
		if (pOrigen.getX() >= pDestino.getX() &&
				pOrigen.getY() >= pDestino.getY()) 
			return 2;
		if (pOrigen.getX() > pDestino.getX() && 
				pOrigen.getY() <= pDestino.getY())
			return 3;
		else
			return 4;				
	}
	
	public double getAnguloPuntos (Point pOrigen, Point pDestino) {
		double xOrigen = pOrigen.getX();
		double yOrigen = pOrigen.getY();
		double xDestino = pDestino.getX();
		double yDestino = pDestino.getY();			
		double catContiguo = xDestino - xOrigen;
		double catOpuesto = yOrigen - yDestino;
		double tgAng = catOpuesto / catContiguo;
		int cuadrante = getCuadrantePuntos(pOrigen, pDestino);
		//System.out.println(Math.toDegrees((Math.atan(tgAng))));
		if (cuadrante==1) {			
			return (Math.toDegrees((Math.atan(tgAng))));
		}
		else if (cuadrante == 2) {
			return (180+Math.toDegrees((Math.atan(tgAng))));
		}
		else if (cuadrante == 3) {
			return (180+Math.toDegrees((Math.atan(tgAng))));
		}
		else { //if (cuadrante == 4)
			return (360+Math.toDegrees((Math.atan(tgAng))));
		}
	}
	public Polygon calculaFlecha (Point destinoArco, Point puntoControl) {
		double largoFlecha = 15;
		double angPunta = 15;
		double angulo = getAnguloPuntos(destino.getCentro(), puntoControl);
		double p1x = destinoArco.getX()+largoFlecha*Math.cos(Math.toRadians(angulo-angPunta));
		double p1y = destinoArco.getY()-largoFlecha*Math.sin(Math.toRadians(angulo-angPunta));
		double p2x = destinoArco.getX()+largoFlecha*Math.cos(Math.toRadians(angulo+angPunta));
		double p2y = destinoArco.getY()-largoFlecha*Math.sin(Math.toRadians(angulo+angPunta));
		int[] xTriangulo = new int[3];
		int[] yTriangulo = new int[3];
		xTriangulo[0] = (int)destinoArco.getX();
		yTriangulo[0] = (int)destinoArco.getY();
		xTriangulo[1] = (int)p1x;
		yTriangulo[1] = (int)p1y;
		xTriangulo[2] = (int)p2x;
		yTriangulo[2] = (int)p2y;
		Polygon triangulo = new Polygon(xTriangulo, yTriangulo, 3);
		return triangulo;
	}
	
	public Polygon calculaFlecha2 (Point destinoArco) {
		double angulo = getAnguloPuntos(destino.getCentro(), destinoArco);
		double p1x = destinoArco.getX()+12*Math.cos(Math.toRadians(angulo-7));
		double p1y = destinoArco.getY()-12*Math.sin(Math.toRadians(angulo-7));
		double p2x = destinoArco.getX()+12*Math.cos(Math.toRadians(angulo+7));
		double p2y = destinoArco.getY()-12*Math.sin(Math.toRadians(angulo+7));
		int[] xTriangulo = new int[3];
		int[] yTriangulo = new int[3];
		xTriangulo[0] = (int)destinoArco.getX();
		yTriangulo[0] = (int)destinoArco.getY();
		xTriangulo[1] = (int)p1x;
		yTriangulo[1] = (int)p1y;
		xTriangulo[2] = (int)p2x;
		yTriangulo[2] = (int)p2y;
		Polygon triangulo = new Polygon(xTriangulo, yTriangulo, 3);
		return triangulo;
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
