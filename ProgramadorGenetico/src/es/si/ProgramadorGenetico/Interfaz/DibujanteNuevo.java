package es.si.ProgramadorGenetico.Interfaz;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.Individuo;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;



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
	
	/**
	 * Punto inicial de un estado antes de moverlo
	 */
	
	private Point puntoInicialEstado;
	
	/**
	 * Estado que se esta moviendo
	 *
	 */
	
	private Estado estadoMovido;
	
	private class OyenteDibujante extends MouseInputAdapter {
		public OyenteDibujante () {
			
		}
		
		public void mouseDragged (MouseEvent e) {
			estadoMovido.actualizaPosicion(e.getPoint());	
			actualizaDibujo();
		}
		
		public void actualizaDibujo() {
			repaint();
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			for (int i=0; i<estados.size();i++){
				Point puntoClick = e.getPoint();			
				Estado es = ((Estado)estados.get(i));
				if (es.getPulsado(puntoClick)) {
					estadoMovido = es;
					System.out.println("Estado "+i+" pulsado");
				}
			}
			
		}


		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			estadoMovido.actualizaPosicion(e.getPoint());	
			actualizaDibujo();
			*/
		}
		
		
		
	}
	
	
	public DibujanteNuevo () {		
	}
	
	
	/**
	 * Esta constructora recibe el mejor automata seleccionado
	 * Inicializa los arrays y crea las etiquetas 
	 * @param mejor Automata seleccionado
	 */
	
	public DibujanteNuevo(Individuo mejor) {
		super(new GridLayout(0,1));
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
			
		super(new GridLayout(0,1));
		this.probabilidadFinal = probabilidadFinal;
		this.numEstados = estados;		
		inicializacionesPanel();
		calculoEstados();
		calculoTransiciones(transiciones);
				
		
										
	}
	
	
	public void inicializacionesPanel () {        
		this.setPreferredSize(new Dimension(600,600));		
		this.setLayout(null);
		
		
		OyenteDibujante oyente = new OyenteDibujante();
	    addMouseListener(oyente);
	    addMouseMotionListener(oyente);
		
			
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
	}
	


	
	/**
	 * Se calculan las posiciones iniciales de los estados
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
		pintaTransiciones(g2);
		pintaEstados(g2);				      
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

		for (int i = 0; i<estados.size()-1; i++)  
			((Estado)(estados.get(i))).paintComponent(g);
		
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
					trans.paintComponent(g);
				}
			}
		}

	}

	public int getNumEstados() {
		return numEstados;
	}

	





}
