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
	public float probabilidad0;
	public float probabilidad1;
	public JLabel label0;
	public JLabel label1;
	private boolean pintar0;
	private boolean pintar1;

	public Transicion () {

	}

	public Transicion (Estado origen, Estado destino, float probabilidad0, float probabilidad1) {
		this.origen = origen;
		this.destino = destino;
		this.probabilidad0 = probabilidad0;
		this.probabilidad1 = probabilidad1;
		label0 = new JLabel();
		label1 = new JLabel();
		
	}

	public Estado getOrigen () {
		return origen;
	}

	public Estado getDestino () {
		return destino;
	}

	public float getProbabilidad0 () {
		return probabilidad0;
	}

	public float getProbabilidad1 () {
		return probabilidad1;
	}

	public JLabel getLabel0() {
		return label0;
	}

	public JLabel getLabel1() {
		return label1;
	}

	public void setLabel0 (JLabel l) {
		label0 = l;
	}

	public void setLabel1 (JLabel l) {
		label1 = l;
	}

	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		//Graphics2D g = (Graphics2D) panel.getGraphics();	
		double radio = origen.getRadio();
		double ang = getAnguloEstados();
		if (origen!=destino) {			
			Point origenArco = new Point ((int)(origen.getCentro().getX()+Math.cos(Math.toRadians(ang+22.5))*origen.getRadio()), 
					(int)(origen.getCentro().getY()-Math.sin(Math.toRadians(ang+22.5))*origen.getRadio()));	
			Point destinoArco = new Point ((int)(destino.getCentro().getX()+Math.cos(Math.toRadians(180+ang-22.5))*destino.getRadio()),
					(int)(destino.getCentro().getY()-Math.sin(Math.toRadians(ang+180-22.5))*destino.getRadio()));
			Point puntoMedio = getPuntoMedio(origenArco,destinoArco);

			if (pintar0) {
			Point puntoControl0 = getPuntoControlCurva0(origenArco, destinoArco, puntoMedio, ang);
			Point centro = origen.getCentro();			
			Color colorTransicion0 = getColorArco0();
			g.setColor(colorTransicion0);
			QuadCurve2D curva0 = new QuadCurve2D.Double();			
			curva0.setCurve(origenArco.getX(), origenArco.getY(),puntoControl0.getX(), puntoControl0.getY(), 
					destinoArco.getX(), destinoArco.getY());
			((Graphics2D)g).draw(curva0);			
			Polygon triangulo0 = calculaFlecha(destinoArco,puntoControl0);
			g.drawPolygon(triangulo0);
			g.fillPolygon(triangulo0);
			Point puntoValor0 = getPuntoValorCurva0(origenArco, destinoArco, puntoMedio, ang);
			label0.setText("0");
			label0.setBounds((int)puntoValor0.getX()-10,(int)puntoValor0.getY()-10,10,10);
			}
			
			if (pintar1) {
			Point puntoControl1 = getPuntoControlCurva1(origenArco, destinoArco, puntoMedio, ang);
			Color colorTransicion1 = getColorArco1();
			g.setColor(colorTransicion1);
			QuadCurve2D curva1 = new QuadCurve2D.Double();			
			curva1.setCurve(origenArco.getX(), origenArco.getY(),puntoControl1.getX(), puntoControl1.getY(), 
					destinoArco.getX(), destinoArco.getY());
			((Graphics2D)g).draw(curva1);			
			Polygon triangulo1 = calculaFlecha(destinoArco,puntoControl1);
			g.drawPolygon(triangulo1);
			g.fillPolygon(triangulo1);
			Point puntoValor1 = getPuntoValorCurva1(origenArco, destinoArco, puntoMedio, ang);
			label1.setText("1");
			label1.setBounds((int)puntoValor1.getX()-10,(int)puntoValor1.getY()-10,10,10);
			}

		}
		else {
			/*
			int despX = (int)(origen.getCentro().getX()+radio*Math.cos(Math.toRadians(90)));
			int despY = (int)(origen.getCentro().getY()-radio*Math.sin(Math.toRadians(90)));
			int radioArco = (int)origen.getRadio();
			Color colorTransicion0 = getColorArco0();
			g.setColor(colorTransicion0);						
			g.drawOval(despX-radioArco/2, despY-radioArco/2, radioArco, radioArco);
			label0.setText("0");
			label0.setBounds(despX,(int)(despY-(radioArco*1.5)),20,20);
			*/
			if (pintar0) {
				int despX = (int)(origen.getCentro().getX()+radio*Math.cos(Math.toRadians(90)));
				int despY = (int)(origen.getCentro().getY()-radio*Math.sin(Math.toRadians(90)));
				int radioArco = (int)origen.getRadio();
				Color colorTransicion0 = getColorArco0();
				g.setColor(colorTransicion0);						
				g.drawOval(despX-radioArco/2, despY-radioArco/2, radioArco, radioArco);
				label0.setText("0");
				label0.setBounds(despX,(int)(despY-(radioArco*1.5)),20,20);
			}
			if (pintar1) {
				int despX = (int)(origen.getCentro().getX()+2*radio*Math.cos(Math.toRadians(90)));
				int despY = (int)(origen.getCentro().getY()-2*radio*Math.sin(Math.toRadians(90)));
				int radioArco = (int)origen.getRadio()*2;
				Color colorTransicion1 = getColorArco1();
				g.setColor(colorTransicion1);						
				g.drawOval(despX-radioArco/2, despY-radioArco/3+5, radioArco, radioArco);
				label1.setText("1");
				label1.setBounds(despX,(int)(despY-(radioArco)+8),20,20);
			}
			
		}

	}

	/*
	public void asignaValorLabel() {
		if (probabilidad0 >= 0.1 && probabilidad1 >=0.1)
			label.setText("0,1");
		else if (probabilidad0 < 0.1 && probabilidad1 >= 0.1)
			label.setText("1");
		else if (probabilidad0 >= 0.1 && probabilidad1 < 0.1)
			label.setText("0");				
	}
	 */



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


	
	public Point getPuntoControlCurva0 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {

		double xPuntoControl;
		double yPuntoControl;
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 70;

		if (cuadrante==1)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));

		xPuntoControl = puntoMedio.getX()+ajusteX;		

		if (cuadrante==1)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));

		yPuntoControl = puntoMedio.getY()+ajusteY;

		return new Point ((int)xPuntoControl, (int)yPuntoControl);	

	}

	public Point getPuntoControlCurva1 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {

		double xPuntoControl;
		double yPuntoControl;
		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 140;

		if (cuadrante==1)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));

		xPuntoControl = puntoMedio.getX()+ajusteX;		

		if (cuadrante==1)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));

		yPuntoControl = puntoMedio.getY()+ajusteY;

		return new Point ((int)xPuntoControl, (int)yPuntoControl);	

	}

	public Point getPuntoValorCurva0 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {

		double xPuntoValor=0.0;
		double yPuntoValor=0.0;

		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 50; 
		
		
		if (cuadrante==1)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));

		xPuntoValor = puntoMedio.getX()+ajusteX;		

		if (cuadrante==1)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));

		yPuntoValor = puntoMedio.getY()+ajusteY;

		return new Point ((int)xPuntoValor, (int)yPuntoValor);	

	}
	
	public Point getPuntoValorCurva1 (Point origenArco, Point destinoArco, Point puntoMedio, double angulo) {

		double xPuntoValor=0.0;
		double yPuntoValor=0.0;

		double ajusteX;
		double ajusteY;
		int cuadrante = getCuadrante();
		int valorAjuste = 90; 
		
		
		if (cuadrante==1)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));
		else //cuadrante==4
			ajusteX = valorAjuste*Math.cos(Math.toRadians((90+angulo)));

		xPuntoValor = puntoMedio.getX()+ajusteX;		

		if (cuadrante==1)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==2)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else if (cuadrante==3)
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));
		else
			ajusteY = -valorAjuste*Math.sin(Math.toRadians((90+angulo)));

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

	public Color getColorArco0 () {	
		
		int valorGris = (int)((1-probabilidad0)*255);
		Color c = new Color(valorGris, valorGris, valorGris);
		return c;
	}
	
	public Color getColorArco1 () {	
		int valorGris = (int)((1-probabilidad1)*255);
		Color c = new Color(valorGris, valorGris, valorGris);
		return c;
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

	public void pinta (boolean trans0, boolean trans1, Graphics g) {
		pintar0 = trans0;
		pintar1 = trans1;
		paintComponent(g);
		label0.repaint();
		label1.repaint();
	}
	
	public boolean equals (Transicion t) {
		if ((origen==t.origen)&&(destino==t.destino)) {
			if (probabilidad0==t.getProbabilidad0() && probabilidad1==t.getProbabilidad1())
			return true;
		}
		return false;
	}
	/*
	public double distanciaEstados () {
		double xOrigen = origen.getCentro().getX();
		double yOrigen = origen.getCentro().getY();
		double xDestino = destino.getCentro().getX();
		double yDestino = destino.getCentro().getY();

	}
	 */

	public void setProbabilidad0(float probabilidad0) {
		this.probabilidad0 = probabilidad0;
	}

	public void setProbabilidad1(float probabilidad1) {
		this.probabilidad1 = probabilidad1;
	}
	
	public void setProbabilidad(int valor, float probabilidad) {
		if (valor==0) 
			probabilidad0 = probabilidad;
		if (valor==1)
			probabilidad1 = probabilidad;
	}
	
	public void setLabel (int valor, JLabel label) {
		if (valor==0)
			label0 = label;
		if (valor==1)
			label1 = label;
	}
	
	public JLabel getLabel (int valor) {
		if (valor==0)
			return label0;
		if (valor==1)
			return label1;
		return null;
	}

}
