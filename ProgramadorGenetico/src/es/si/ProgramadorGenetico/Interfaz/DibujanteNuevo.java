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
import java.util.Iterator;


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
	private double diamEst = 40;
	
	/**
	 * Radio del poligono que forman los estados.
	 */
	private double radioPolig = 150;
	
	/**
	 * Objeto para Graphics2D
	 */
	private Graphics2D g2;
		
	/**
	 * Array que contiene los estados	 
	 */
	private ArrayList<Estado> estados;
	
	/**
	 * Array que contiene las transiciones
	 *
	 */
	private Transicion[][] transiciones;
	
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
		
										
	}
	
	
	public void inicializacionesPanel () {
		this.setPreferredSize(new Dimension(600,600));		
		this.setLayout(null);
	}
	
	/**
	 * 
	 */
	/**
	 * 
	 */
	public void calculoEstados() {
		estados = new ArrayList<Estado>();
		//Creamos un estado de mas
		for (int i=0; i<numEstados+1; i++) { 
			estados.add(new Estado());			
			estados.get(i).setLabel(new JLabel ("Q"+i));
			//add(estados.get(i).getLabel());
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
			x+=radioPolig*Math.cos(Math.toRadians(alfa));
			y+=radioPolig*Math.sin(Math.toRadians(alfa));
			estados.get(i).setPunto(new Point(x,y));
			estados.get(i).setDiametro(diamEst);
			estados.get(i).getLabel().setBounds(x+10, y+10, 20, 20);					
			alfa+=incremento;
			x=xini;
			y=yini;
		}
		
		for (int i=0; i<numEstados+1; i++) 
			add(estados.get(i).getLabel());
		
	}
	
	public void calculoTransiciones(double [][][] transicionesArray) {

		transiciones = new Transicion[numEstados][numEstados+1];
		System.out.println("Longitud de la primera dimension."+transicionesArray.length);
		System.out.println("Longitud de la segunda dimension."+transicionesArray[0].length);
		System.out.println("Longitud de la tercera dimension."+transicionesArray[0][0].length);
		
		for (int i=0; i<transicionesArray.length; i++ ) {
			for (int k=0; k<transicionesArray[i][0].length; k++) {
				transiciones[i][k] = new Transicion (estados.get(i),estados.get(k),
						transicionesArray[i][0][k],transicionesArray[i][1][k]);

				transiciones[i][k].setLabel(new JLabel());			
				add(transiciones[i][k].getLabel());		

			}	
		}
	}


	
	/**
	 * Llama a pintar los estados y las transiciones
	 */
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		pintaEstados(g2);
		//pintaTransiciones(g2);
		//pintaTransicionesPrueba(g,g2);
		//pintaEstados(g2);
		       
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
	public void pintaEstados (Graphics2D g) {

		//for (Iterator it=estados.listIterator(); it.hasNext();it.next()) {
		//	Estado es = (Estado)(it.next());	
		
		for (int i = 0; i<estados.size(); i++)  
			((Estado)(estados.get(i))).pinta(this);
		
		/*
		for (int i =0; i<estados.size(); i++) {
			g.setColor(Color.yellow);
			Estado es =((Estado)(estados.get(i)));
			g.fillOval((int)es.getPunto().getX(), (int)es.getPunto().getY(), (int)diamEst, (int)diamEst);
			g.drawOval((int)es.getPunto().getX(), (int)es.getPunto().getY(), (int)diamEst, (int)diamEst);						
		}
		*/
	}
	
	/**
	 * Pinta las transiciones
	 * @param g
	 * @param g2
	 */
	public void pintaTransiciones (Graphics2D g) {
		for (int i=0; i<transiciones.length; i++) {
			for (int j=0; j<transiciones[i].length; j++) {

				Transicion trans = transiciones[i][j];

				boolean transicionCon0 = transiciones[i][j].getProbabilidad0() > 0.1;
				boolean transicionCon1 = transiciones[i][j].getProbabilidad1() > 0.1;

				if (transicionCon0 || transicionCon1) {

					trans.pinta(this);

					//asignaEtiquetas(i,j,transicionCon0,transicionCon1);						

				}												

			}
		}

	}


	
	/**
	 * Prueba de dibujo de las transiciones
	 * @param g
	 * @param g2
	 */
	/*
	public void pintaTransicionesPrueba (Graphics g, Graphics2D g2) {
		
		double punto0XAjustado = puntosEstados[0].getX()+(diamEst/2.0);
		double punto0YAjustado = puntosEstados[0].getY()+(diamEst/2.0);
		double punto1XAjustado = puntosEstados[1].getX()+(diamEst/2.0);
		double punto1YAjustado = puntosEstados[1].getY()+(diamEst/2.0);
		
		//double punto0XAjustado = 150;
		//double punto0YAjustado = 300;
		//double punto1XAjustado = 250;
		//double punto1YAjustado = 200;
		
		
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
	*/

	public int getNumEstados() {
		return numEstados;
	}
}
