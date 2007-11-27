package es.si.ProgramadorGenetico.Interfaz;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import es.si.ProgramadorGenetico.Individuo;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

import java.awt.RenderingHints;


public class Dibujante extends JPanel{

	//Atributos de entrada
	private double[][][] transiciones;
	private double[] probabilidadFinal;
	private int estados;
	
	private int x;
	private int y;
	private int d = 40;
	private double r = 150;
	
	private JLabel[] etiquetasEstados;
	private Point[] puntosEstados;
	private JLabel[][] etiquetasValores;
		
	
	public Dibujante () {		
	}
	
	public Dibujante(Individuo mejor) {
		AFP automataMejor = (AFP) mejor;
		transiciones = automataMejor.getTransiciones();
		estados = automataMejor.getEstados();
		probabilidadFinal = automataMejor.getProbabilidadesFinal();
		
		this.setPreferredSize(new Dimension(600,600));		
		this.setLayout(null);
		
		puntosEstados = new Point[estados];
		
		etiquetasEstados = new JLabel[estados];
		for (int i=0; i<estados; i++) { 
			etiquetasEstados[i]=new JLabel ("Q"+i);			
			add(etiquetasEstados[i]);			
		}							
		
		etiquetasValores =  new JLabel[estados][estados];
		for (int i=0; i<estados; i++) {
			for (int j=0; j<estados; j++) {
				etiquetasValores[i][j]=new JLabel ();			
				add(etiquetasValores[i][j]);		
			}
		}							
		
		//el centro de la circunferencia imaginaria esta en el centro
		int xini= (int) (this.getPreferredSize().getWidth() / 2) ; 
		int yini = (int) (this.getPreferredSize().getHeight() / 2) ;
		x = xini;
		y = yini;
		//calculo el radio, angulo e incremento		
		double alfa = 180;
		double incremento = 360/(double)(estados);
		//Colocamos las etiquetas
		for (int i=0; i< estados; i++) {								
			x+=r*Math.cos(enRadianes(alfa));
			y+=r*Math.sin(enRadianes(alfa));
			etiquetasEstados[i].setBounds(x+10, y+10, 20, 20);					
			alfa+=incremento;
			x=xini;
	        y=yini;
		}
	}
			
	
	public Dibujante (double[][][] transiciones, 
			double[] probabilidadFinal,	int estados) {
				
		this.transiciones = transiciones;
		this.probabilidadFinal = probabilidadFinal;
		this.estados = estados;
		
		this.setPreferredSize(new Dimension(600,600));		
		this.setLayout(null);
		
		puntosEstados = new Point[estados];
		
		etiquetasEstados = new JLabel[estados];
		for (int i=0; i<estados; i++) { 
			etiquetasEstados[i]=new JLabel ("Q"+i);			
			add(etiquetasEstados[i]);			
		}							
		
		etiquetasValores =  new JLabel[estados][estados];
		for (int i=0; i<estados; i++) {
			for (int j=0; j<estados; j++) {
				etiquetasValores[i][j]=new JLabel ();			
				add(etiquetasValores[i][j]);		
			}
		}							
		
		//el centro de la circunferencia imaginaria esta en el centro
		int xini= (int) (this.getPreferredSize().getWidth() / 2) ; 
		int yini = (int) (this.getPreferredSize().getHeight() / 2) ;
		x = xini;
		y = yini;
		//calculo el radio, angulo e incremento		
		double alfa = 180;
		double incremento = 360/(double)(estados);
		//Colocamos las etiquetas
		for (int i=0; i< estados; i++) {								
			x+=r*Math.cos(enRadianes(alfa));
			y+=r*Math.sin(enRadianes(alfa));
			etiquetasEstados[i].setBounds(x+10, y+10, 20, 20);					
			alfa+=incremento;
			x=xini;
	        y=yini;
		}
	}
	
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		pintaEstados(g);
		pintaTransiciones(g,g2);
		//pintaTransicionesPrueba(g,g2);
		pintaEstados(g);
		       
    }
	
	public double enRadianes (double ang) {
		return ang*2*Math.PI/360.0;
	}
	
	public double enGrados (double ang) {
		return ang*360/(2*Math.PI);
	}

	public void pintaEstados (Graphics g) {
		//el centro de la circunferencia imaginaria esta en el centro
		int xini= (int) (this.getPreferredSize().getWidth() / 2) ; 
		int yini = (int) (this.getPreferredSize().getHeight() / 2) ;
		x = xini;
		y = yini;
		//calculo el radio, angulo e incremento		
		double alfa = 180;
		double incremento = 360/(double)(estados);
		//para cada estado pintamos un circulo, que forman los vertices de un poligono
		//regular de un numero de lados igual al numero de estados.
		for (int i=0; i< estados; i++) {
			x+=r*Math.cos(enRadianes(alfa));
			y+=r*Math.sin(enRadianes(alfa));
			puntosEstados[i]=new Point(x,y);
			g.setColor(Color.yellow);
			g.fillOval(x, y, d, d);
	        g.drawOval(x, y, d, d);
	        alfa+=incremento;
	        x=xini;
	        y=yini;
		}
	}
	
	public void pintaTransiciones (Graphics g, Graphics2D g2) {
		for (int i=0; i<estados;i++) {
			for (int j=0; j<estados;j++) { //estados+1
				boolean transicionCon0 = transiciones[i][0][j] > 0.9;
				boolean transicionCon1 = transiciones[i][1][j] > 0.9;
				
				if (transicionCon0 || transicionCon1) {
				
					double punto0XAjustado = puntosEstados[i].getX()+(d/2.0);
					double punto0YAjustado = puntosEstados[i].getY()+(d/2.0);
					double punto1XAjustado = puntosEstados[j].getX()+(d/2.0);
					double punto1YAjustado = puntosEstados[j].getY()+(d/2.0);

					double xMedio = (punto0XAjustado+punto1XAjustado)/2.0;
					double yMedio = (punto0YAjustado+punto1YAjustado)/2.0;	    
					double tgangulo = Math.abs((punto1YAjustado-punto0YAjustado)/
							(punto1XAjustado-punto0XAjustado));		
					double angulo = Math.atan(tgangulo);

					double xpuntoControl = getPuntoControlX(i,j,angulo, xMedio, yMedio);
					double ypuntoControl = getPuntoControlY(i,j,angulo,xMedio,yMedio);
					QuadCurve2D q = new QuadCurve2D.Double();
					q.setCurve(punto0XAjustado, punto0YAjustado,xpuntoControl, ypuntoControl, 
							punto1XAjustado, punto1YAjustado);
					g2.setColor(Color.black);
					g2.draw(q);
					
					//valores
																		
					double xPuntoValor = getPuntoValorX (i,j, angulo, xMedio, yMedio);
					double yPuntoValor = getPuntoValorY (i,j, angulo, xMedio, yMedio);
					
					if (i==0 && j==1) {
					System.out.println("puntoControl:"+xpuntoControl+","+ypuntoControl);
					System.out.println("puntoValor:"+xPuntoValor+","+yPuntoValor);
					}
					
					etiquetasValores[i][j].setBounds((int)xPuntoValor,(int)yPuntoValor,20,20);
					if (transicionCon0  && ! transicionCon1)
						etiquetasValores[i][j].setText("0");
					else if (!transicionCon0 && transicionCon1)
						etiquetasValores[i][j].setText("1");
					else
						etiquetasValores[i][j].setText("0,1");
					
																	
					
				}
			}

		}
	}
	
	public double getPuntoControlX (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteX = 150*Math.cos(enRadianes(90+enGrados(angulo)));
		if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
			puntosEstados[j].getY() < puntosEstados[i].getY())							
			
			return xMedio+ajusteX;
		else if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
				puntosEstados[j].getY() > puntosEstados[i].getY())
			return xMedio-ajusteX;
			else if (puntosEstados[j].getX() < puntosEstados[i].getX() && 
			puntosEstados[j].getY() < puntosEstados[i].getY())
				return xMedio+ajusteX;
			else
				return xMedio-ajusteX;
			
	}
	
	public double getPuntoControlY (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteY = 150*Math.sin(enRadianes(90+enGrados(angulo)));
		if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
			puntosEstados[j].getY() < puntosEstados[i].getY())
			
			return yMedio-ajusteY;
		else if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
					puntosEstados[j].getY() > puntosEstados[i].getY())
			return yMedio-ajusteY;
		else if (puntosEstados[j].getX() < puntosEstados[i].getX() && 
				puntosEstados[j].getY() < puntosEstados[i].getY())
			return yMedio+ajusteY;
		else return yMedio+ajusteY;
				
	}
	
	public double getPuntoValorX (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteX = 100*Math.cos(enRadianes(90+enGrados(angulo)));
		if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
			puntosEstados[j].getY() < puntosEstados[i].getY())							
			
			return xMedio+ajusteX;
		else if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
				puntosEstados[j].getY() > puntosEstados[i].getY())
			return xMedio-ajusteX;
			else if (puntosEstados[j].getX() < puntosEstados[i].getX() && 
			puntosEstados[j].getY() < puntosEstados[i].getY())
				return xMedio+ajusteX;
			else
				return xMedio-ajusteX;
			
	}
	
	public double getPuntoValorY (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteY = 100*Math.sin(enRadianes(90+enGrados(angulo)));
		if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
			puntosEstados[j].getY() < puntosEstados[i].getY())
			
			return yMedio-ajusteY;
		else if (puntosEstados[j].getX() > puntosEstados[i].getX() && 
					puntosEstados[j].getY() > puntosEstados[i].getY())
			return yMedio-ajusteY;
		else if (puntosEstados[j].getX() < puntosEstados[i].getX() && 
				puntosEstados[j].getY() < puntosEstados[i].getY())
			return yMedio+ajusteY;
		else return yMedio+ajusteY;
				
	}
	
	
	
	public void pintaTransicionesPrueba (Graphics g, Graphics2D g2) {
		
		double punto0XAjustado = puntosEstados[0].getX()+(d/2.0);
		double punto0YAjustado = puntosEstados[0].getY()+(d/2.0);
		double punto1XAjustado = puntosEstados[1].getX()+(d/2.0);
		double punto1YAjustado = puntosEstados[1].getY()+(d/2.0);
		
		/*
		double punto0XAjustado = 150;
		double punto0YAjustado = 300;
		double punto1XAjustado = 250;
		double punto1YAjustado = 200;
		*/
		
	    double xMedio = (punto0XAjustado+punto1XAjustado)/2.0;
	    double yMedio = (punto0YAjustado+punto1YAjustado)/2.0;	    
	    double tgangulo = Math.abs((punto1YAjustado-punto0YAjustado)/
	    				(punto1XAjustado-punto0XAjustado));
	    
	    double angulo = Math.atan(tgangulo);
    
	    double ajusteX = 150*Math.cos(enRadianes(90+enGrados(angulo)));
	    double ajusteY = 150*Math.sin(enRadianes(90+enGrados(angulo)));
	    
	    double xpuntoControl = xMedio+ajusteX;
	    double ypuntoControl = yMedio-ajusteY;
	    
	    System.out.println("X0:"+puntosEstados[0].getX()+","+puntosEstados[0].getY());
	    System.out.println("X1:"+puntosEstados[1].getX()+","+puntosEstados[1].getY());
	    System.out.println("Medio:"+xMedio+","+yMedio);
	    System.out.println(angulo);
	    System.out.println(enGrados(angulo));
	    System.out.println("ajusteX"+ajusteX);
	    System.out.println("ajusteY"+ajusteY);
	    	    	         
	    QuadCurve2D q = new QuadCurve2D.Double();
	    q.setCurve(punto0XAjustado, punto0YAjustado,xpuntoControl, ypuntoControl, 
	    		punto1XAjustado, punto1YAjustado);
	    g2.setColor(Color.black);
	    g2.draw(q);
	    
	}
}
