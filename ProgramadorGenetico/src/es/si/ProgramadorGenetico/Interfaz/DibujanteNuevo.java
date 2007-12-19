package es.si.ProgramadorGenetico.Interfaz;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.Individuo;

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



import java.awt.RenderingHints;
import java.util.ArrayList;


public class DibujanteNuevo extends JPanel{

		
	/**
	 * Probabilidad final 
	 */
	private double[] probabilidadFinal;
	
	/**
	 * Numero de estados
	 */
	private int numEstados;
	
	/**
	 * Posicion x 
	 */	
	private int x;
	
	/**
	 * Posicion y 
	 */
	private int y;
	
	/**
	 * Diametro del circulo que representa al estado 
	 */
	private int diamEst = 40;
	
	/**
	 * Radio del poligono que forman los estados.
	 */
	private double radioPolig = 150;
	
	/**
	 * Array de JLabel donde se guardan las etiquetas de los
	 * estados
	 */
	private JLabel[] etiquetasEstados;
	
	/**
	 * Array de puntos donde se guardan las coordenadas de los estados
	 */
	private Point[] puntosEstados;
	
	/**
	 * Array de JLabel donde se guardan los valores de cada transicion
	 */
	private JLabel[][] etiquetasValores;
		
	/**
	 * Array que contiene los estados	 
	 */
	private ArrayList<Estado> estados;
	
	/**
	 * Array que contiene las transiciones
	 *
	 */
	private ArrayList<Transicion> transiciones;
	
	public DibujanteNuevo () {		
	}
	
	
	/**
	 * Esta constructora recibe el mejor automata seleccionado
	 * Inicializa los arrays y crea las etiquetas 
	 * @param mejor Automata seleccionado
	 */
	
	public DibujanteNuevo(Individuo mejor) {
		AFP automataMejor = (AFP) mejor;		
		double[][][] transicionesArray;
		transicionesArray = automataMejor.getTransiciones();
		numEstados = automataMejor.getEstados();
		probabilidadFinal = automataMejor.getProbabilidadesFinal();
		
		inicializacionesPanel();
		calculoEstados();
		calculoTransiciones(transicionesArray);
		
		//puntosEstados = new Point[numEstados];
									
		etiquetasValores =  new JLabel[numEstados][numEstados];
		for (int i=0; i<numEstados; i++) {
			for (int j=0; j<numEstados; j++) {
				etiquetasValores[i][j]=new JLabel ();			
				add(etiquetasValores[i][j]);		
			}
		}							
		
		
		
	}
			
	/**
	 * Constructora que recibe en 3 parametros los datos necesarios
	 * @param transiciones
	 * @param probabilidadFinal
	 * @param estados
	 */
	
	public DibujanteNuevo (double[][][] transiciones, 
			double[] probabilidadFinal,	int estados) {
			
		this.probabilidadFinal = probabilidadFinal;
		this.numEstados = estados;
		
		inicializacionesPanel();
		calculoEstados();
		calculoTransiciones(transiciones);
		
		//puntosEstados = new Point[numEstados];						
		
		etiquetasValores =  new JLabel[estados][estados];
		for (int i=0; i<estados; i++) {
			for (int j=0; j<estados; j++) {
				etiquetasValores[i][j]=new JLabel ();			
				add(etiquetasValores[i][j]);		
			}
		}							
	}
	
	
	public void inicializacionesPanel () {
		this.setPreferredSize(new Dimension(600,600));		
		this.setLayout(null);
	}
	
	public void calculoEstados() {
		estados = new ArrayList<Estado>();
		for (int i=0; i<numEstados; i++) { 
			estados.add(new Estado());			
			estados.get(i).setLabel(new JLabel ("Q"+i));
			add(estados.get(i).getLabel());
		}
		//el centro de la circunferencia imaginaria esta en el centro
		int xini= (int) (this.getPreferredSize().getWidth() / 2) ; 
		int yini = (int) (this.getPreferredSize().getHeight() / 2) ;
		x = xini;
		y = yini;
		//calculo el radio, angulo e incremento		
		double alfa = 180;
		double incremento = 360/(double)(numEstados);
		//Colocamos las etiquetas en los vertices del poligono
		for (int i=0; i< numEstados; i++) {								
			x+=radioPolig*Math.cos(enRadianes(alfa));
			y+=radioPolig*Math.sin(enRadianes(alfa));
			estados.get(i).setPunto(new Point(x,y));
			estados.get(i).setDiametro(diamEst);
			estados.get(i).getLabel().setBounds(x+10, y+10, 20, 20);					
			alfa+=incremento;
			x=xini;
			y=yini;
		}
	}
	
	public void calculoTransiciones(double [][][] transicionesArray) {
		
		transiciones = new ArrayList<Transicion>();
		int j=0;
		int k=0;
		for (int i=0; i<transicionesArray.length; i++ ) {
			for (j=0; j<transicionesArray[i].length; j++) {
				for (k=0; k<transicionesArray[i][j].length; k++) {
					Transicion t = new Transicion (estados.get(i),estados.get(k),
							transicionesArray[i][j][k],j);
					transiciones.add(t);
				}
			}
		}
		                                     
	}
	
	/**
	 * Llama a pintar los estados y las transiciones
	 */
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		pintaEstados(g);
		pintaTransiciones(g,g2);
		//pintaTransicionesPrueba(g,g2);
		pintaEstados(g);
		       
    }
	
	/**
	 * Pasa de grados a radianes
	 * @param ang
	 * @return
	 */
	public double enRadianes (double ang) {
		return ang*2*Math.PI/360.0;
	}
	
	/**
	 * Pasa de radianes a grados
	 * @param ang
	 * @return
	 */
	public double enGrados (double ang) {
		return ang*360/(2*Math.PI);
	}

	/**
	 * Pinta los estados
	 * @param g
	 */
	public void pintaEstados (Graphics g) {
		//el centro de la circunferencia imaginaria esta en el centro
		int xini= (int) (this.getPreferredSize().getWidth() / 2) ; 
		int yini = (int) (this.getPreferredSize().getHeight() / 2) ;
		x = xini;
		y = yini;
		//calculo el radio, angulo e incremento		
		double alfa = 180;
		double incremento = 360/(double)(numEstados);
		//para cada estado pintamos un circulo, que forman los vertices de un poligono
		//regular de un numero de lados igual al numero de estados.
		for (int i=0; i< numEstados; i++) {
			x+=radioPolig*Math.cos(enRadianes(alfa));
			y+=radioPolig*Math.sin(enRadianes(alfa));
			estados.get(i).setPunto(new Point(x,y));
			g.setColor(Color.yellow);
			g.fillOval(x, y, estados.get(i).getDiametro(), estados.get(i).getDiametro());
	        g.drawOval(x, y, estados.get(i).getDiametro(), estados.get(i).getDiametro());
	        alfa+=incremento;
	        x=xini;
	        y=yini;
		}
	}
	
	/**
	 * Pinta las transiciones
	 * @param g
	 * @param g2
	 */
	public void pintaTransiciones (Graphics g, Graphics2D g2) {
		for (int i=0; i<transiciones.size(); i++) {
			Transicion trans = (Transicion) transiciones.get(i);
			
				boolean transicionCon0 = transiciones[i][0][j] > 0.1;
				boolean transicionCon1 = transiciones[i][1][j] > 0.1;

				if (transicionCon0 || transicionCon1) {

					if (i==j) {
						int radioArco = diamEst/2;
						double incremento = 360/(double)(numEstados);
						double ang = 180-i*incremento;
						Point centro = getCentroEstado(puntosEstados[i]);
						int despX = (int)(centro.getX()+(diamEst/2)*Math.cos(enRadianes(ang)));
						int despY = (int)(centro.getY()-(diamEst/2)*Math.sin(enRadianes(ang)));						
						Color colorTransicion = getColorArco(i,j);
						g.setColor(colorTransicion);						
						g.drawOval(despX-radioArco/2, despY-radioArco/2, radioArco, radioArco);
						//g.drawOval((int)puntosEstados[i].getX()+d/2-d, (int)puntosEstados[i].getY()+d/2-d, radioArco, radioArco);
						System.out.println("ang:"+ang+"despX: "+despX+" despY: "+despY);
						double xPuntoValor = (double)(centro.getX()+(1.5*diamEst)*Math.cos(enRadianes(ang)));
						double yPuntoValor = (double)(centro.getY()-(1.5*diamEst)*Math.sin(enRadianes(ang)));
						etiquetasValores[i][j].setBounds((int)xPuntoValor,
								(int)yPuntoValor,20,20);
						asignaEtiquetas(i, j, transicionCon0, transicionCon1);
						
						
					}

					else{

						double punto0XAjustado = puntosEstados[i].getX()+(diamEst/2.0);
						double punto0YAjustado = puntosEstados[i].getY()+(diamEst/2.0);
						double punto1XAjustado = puntosEstados[j].getX()+(diamEst/2.0);
						double punto1YAjustado = puntosEstados[j].getY()+(diamEst/2.0);

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
						/*
						if (transiciones[i][0][j] >= 0.9)
							g.setColor(Color.black);
						if (transiciones[i][0][j] < 0.9)
							g.setColor(Color.red);
						*/
						Color colorTransicion = getColorArco(i,j);
						g.setColor(colorTransicion);	
						g2.draw(q);

						//valores

						double xPuntoValor = getPuntoValorX (i,j, angulo, xMedio, yMedio);
						double yPuntoValor = getPuntoValorY (i,j, angulo, xMedio, yMedio);

						if (i==0 && j==1) {
							System.out.println("puntoControl:"+xpuntoControl+","+ypuntoControl);
							System.out.println("puntoValor:"+xPuntoValor+","+yPuntoValor);
						}

						etiquetasValores[i][j].setBounds((int)xPuntoValor,(int)yPuntoValor,20,20);
						asignaEtiquetas(i,j,transicionCon0,transicionCon1);						

					}												

				}
			}

		}
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
	public double getPuntoControlX (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteX = 70*Math.cos(enRadianes(90+enGrados(angulo)));
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
	
	/**
	 * Devuelve la coordenada y del punto de control del arco que representara la transicion
	 * @param i
	 * @param j
	 * @param angulo
	 * @param xMedio
	 * @param yMedio
	 * @return
	 */
	public double getPuntoControlY (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteY = 70*Math.sin(enRadianes(90+enGrados(angulo)));
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
	
	/**
	 * Devuelve la coordenada x del punto donde se dibujara el valor
	 * @param i
	 * @param j
	 * @param angulo
	 * @param xMedio
	 * @param yMedio
	 * @return
	 */
	public double getPuntoValorX (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteX = 60*Math.cos(enRadianes(90+enGrados(angulo)));
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
	/**
	 * Devuelve la coordenada y del punto donde se dibujara el valor
	 * @param i
	 * @param j
	 * @param angulo
	 * @param xMedio
	 * @param yMedio
	 * @return
	 */
	public double getPuntoValorY (int i, int j, double angulo, double xMedio, double yMedio) {
		
		double ajusteY = 60*Math.sin(enRadianes(90+enGrados(angulo)));
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
	
	/**
	 * Devuelve el centro de la circunferencia que representa el estado
	 * @param p
	 * @return
	 */
	
	public Point getCentroEstado (Point p) {

		Point centro = new Point((int)(p.getX()+diamEst/2),(int)(p.getY()+diamEst/2));		
		return centro;
		
	}
	
	/**
	 * Devuelve el color con el que se pintara el arco de la transicion
	 * @param i
	 * @param j
	 * @return
	 */
	public Color getColorArco (int i, int j) {
		
		if (transiciones[i][0][j] >= 0.9 || transiciones[i][1][j] >=0.9)
			return Color.black;
		
		if (transiciones[i][0][j] < 0.1 && transiciones[i][1][j] < 0.9)
			return Color.red;
		
		if (transiciones[i][1][j] < 0.1 && transiciones [i][0][j] < 0.9)
			return Color.red;

		return Color.black;
	}
	
	/**
	 * Dependiendo de los valores de las probabilidades de las transiciones de un estado i a
	 * un estado j, devuelve el texto que se mostrara en la transicion
	 * @param i
	 * @param j
	 * @param transicionCon0
	 * @param transicionCon1
	 */
	public void asignaEtiquetas (int i, int j, boolean transicionCon0, boolean transicionCon1) {
	
		if (transicionCon0  && ! transicionCon1)
			etiquetasValores[i][j].setText("0");
		else if (!transicionCon0 && transicionCon1)
			etiquetasValores[i][j].setText("1");
		else if (transiciones[i][0][j] < 0.9 && transiciones[i][1][j] >= 0.9)
			etiquetasValores[i][j].setText("1");
		else if (transiciones[i][1][j] < 0.9 && transiciones[i][0][j] >= 0.9)
			etiquetasValores[i][j].setText("0");
		else
			etiquetasValores[i][j].setText("0,1");
		
	}
	
	/**
	 * Prueba de dibujo de las transiciones
	 * @param g
	 * @param g2
	 */
	public void pintaTransicionesPrueba (Graphics g, Graphics2D g2) {
		
		double punto0XAjustado = puntosEstados[0].getX()+(diamEst/2.0);
		double punto0YAjustado = puntosEstados[0].getY()+(diamEst/2.0);
		double punto1XAjustado = puntosEstados[1].getX()+(diamEst/2.0);
		double punto1YAjustado = puntosEstados[1].getY()+(diamEst/2.0);
		
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
