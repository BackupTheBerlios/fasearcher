package es.si.ProgramadorGenetico.Interfaz;
import es.si.ProgramadorGenetico.GeneradorAutomatico.AF;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.Dibujante;
import es.si.ProgramadorGenetico.Individuo;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;



import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DibujanteAF extends JPanel implements Dibujante{


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
	private int diamEst = 50;

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
	private List<Estado> estados;

	/**
	 * Array que contiene las transiciones
	 *
	 */
	private Transicion[][] transiciones;
	
	private List<Transicion> transiciones2;
	
	private List<Boolean> estadosFinales;

	/**
	 * Estado que se esta moviendo
	 *
	 */
	private Estado estadoMovido;
	
	/**
	 * Automata
	 */
	private AF automataMejor;
	
	private int modo;	
	protected final int INSERTAR_ESTADO=1;
	protected final int INSERTAR_TRANSICION=2;
	protected final int FINALIZAR_TRANSICION=3;
	protected final int SET_FINAL=4;
	protected final int EDICION=0;
	private Estado estadoInicioTransicion;
	
	private class OrigenDestinos {
		
		private int destino0;
		private int destino1;
				
		public int get0() {
			return destino0;
		}
		public int get1() {
			return destino1;
		}
	}
	
	//private List<OrigenTransiciones> tablaDestinos; 

	private class OyenteDibujante extends MouseInputAdapter {
		public OyenteDibujante () {

		}

		public void mouseDragged (MouseEvent e) {
			if (estadoMovido !=null) {
				estadoMovido.actualizaPosicion(e.getPoint());	
				actualizaDibujo();
			}
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
			switch (modo) {
			case 1: {
				Point puntoClick = e.getPoint();				
				String etiqueta = new String("Q"+getNumEstados());
				JLabel label = new JLabel(etiqueta);
				Rectangle ajusteLabel = new Rectangle((int)(diamEst/2)-10, (int)(diamEst/2)-15, 30, 30);
				Estado nuevoE = new Estado(diamEst,ajusteLabel);								
				nuevoE.setLabel(label);
				nuevoE.setPunto(puntoClick);
				nuevoE.setBoundsLabel(puntoClick.x, puntoClick.y);
				addEstado(nuevoE);
				addLabelEstado(nuevoE.getLabel());
				paintComponent(getGraphics());
				break;
			}
			case 2: {
				boolean estadoPulsado = false;
				for (int i=0; i<estados.size()&&!estadoPulsado;i++){
					Point puntoClick = e.getPoint();			
					Estado es = ((Estado)estados.get(i));
					if (es.getPulsado(puntoClick)) {
						estadoInicioTransicion = es;					
						estadoPulsado = true;
						setModo(FINALIZAR_TRANSICION);
					}
				}
				paintComponent(getGraphics());
				break;
			}

			case 3: {
				boolean estadoPulsado = false;
				for (int i=0; i<estados.size()&&!estadoPulsado;i++){
					Point puntoClick = e.getPoint();			
					Estado estadoFinTransicion = ((Estado)estados.get(i));
					if (estadoFinTransicion.getPulsado(puntoClick)) {
						estadoPulsado=true;
						String s= new String(); 
						do {
							s = JOptionPane.showInputDialog("Introduzca el nuevo valor de la transicion (0 o 1)");
						}
						while (s==null||!((s.equals("0"))||(s.equals("1"))));
						int valor = Integer.valueOf(s);
						
						Transicion t = buscaTransiciones(estadoInicioTransicion,estadoFinTransicion);
						if (t!=null) {						
							Transicion viejaT = buscaTransiciones(valor, estadoInicioTransicion);
							if (viejaT!=null) {
								viejaT.setProbabilidad(valor,0);
								if (viejaT.getProbabilidad0()==0 && viejaT.getProbabilidad1()==0) {
									removeLabel(viejaT.getLabel0());
									removeLabel(viejaT.getLabel1());
									transiciones2.remove(viejaT);
									
								}
							}					
							t.setProbabilidad(valor,1);
							t.setLabel(valor, new JLabel(Integer.toString(valor)));
						}
						else {
							Transicion viejaT = buscaTransiciones(valor, estadoInicioTransicion);
							if (viejaT!=null) {
								viejaT.setProbabilidad(valor,0);
								if (viejaT.getProbabilidad0()==0 && viejaT.getProbabilidad1()==0) {
									removeLabel(viejaT.getLabel0());
									removeLabel(viejaT.getLabel1());
									transiciones2.remove(viejaT);
								}
							}					
							Transicion nuevaT = new Transicion (estadoInicioTransicion,estadoFinTransicion,0,0);
							nuevaT.setProbabilidad(valor,1);
							nuevaT.setLabel(valor,new JLabel(Integer.toString(valor)));
							addTransicion(nuevaT);
							//AÑADIR LA LABEL
							add(transiciones2.get(transiciones2.size()-1).getLabel(valor));
						}
					}
				}
				estadoInicioTransicion=null;
				setModo(2);
				paintComponent(getGraphics());
				break;
			
			}
			
			case 4: {
				boolean estadoPulsado = false;
				for (int i=0; i<estados.size()&&!estadoPulsado;i++){
					Point puntoClick = e.getPoint();			
					Estado es = ((Estado)estados.get(i));					
					if (es.getPulsado(puntoClick)) {
						es.setProbabilidadFinal(1.0-es.getProbabilidadFinal());						
					}
				}
				paintComponent(getGraphics());
				break;
				
				
			}
			case 0: {
				boolean estadoPulsado = false;
				for (int i=0; i<estados.size()&&!estadoPulsado;i++){
					Point puntoClick = e.getPoint();			
					Estado es = ((Estado)estados.get(i));
					if (es.getPulsado(puntoClick)) {
						estadoMovido = es;					
						estadoPulsado = true;
					}
				}
				if (!estadoPulsado)
					estadoMovido = null;
				paintComponent(getGraphics());
				break;
				
			}

			default: {
				boolean estadoPulsado = false;
				for (int i=0; i<estados.size()&&!estadoPulsado;i++){
					Point puntoClick = e.getPoint();			
					Estado es = ((Estado)estados.get(i));
					if (es.getPulsado(puntoClick)) {
						estadoMovido = es;					
						estadoPulsado = true;
					}
				}
				if (!estadoPulsado)
					estadoMovido = null;
				paintComponent(getGraphics());
				break;
			}
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

		public void buscaEstado () {
		
	}

	public DibujanteAF () {
		super(new GridLayout(0,1));		
		inicializacionesPanel();
		estados = new ArrayList<Estado>();
		transiciones2 = new ArrayList<Transicion>();
		estadosFinales = new ArrayList<Boolean> ();
		numEstados = 0;
		//tablaDestinos = new ArrayList<DestinoTransiciones>();
	}


	/**
	 * Esta constructora recibe el mejor automata seleccionado
	 * Inicializa los arrays y crea las etiquetas 
	 * @param mejor Automata seleccionado
	 */

	public DibujanteAF(Individuo mejor) {
		super(new GridLayout(0,1));		
		automataMejor = (AF) mejor;	
		double[][][] transicionesArray;
		transicionesArray = automataMejor.getTransiciones();
		numEstados = automataMejor.getEstados();		
		probabilidadFinal = automataMejor.getFinales();
		inicializacionesPanel();
		calculoEstados();
		calculoTransiciones2(transicionesArray);

	}

	/**
	 * Constructora que recibe en 3 parametros los datos necesarios
	 * @param transiciones
	 * @param probabilidadFinal
	 * @param estados
	 */

	public DibujanteAF (double[][][] transiciones, 
			double[] probabilidadFinal,	int estados) {
		super(new GridLayout(0,1));		
		this.probabilidadFinal = probabilidadFinal;
		this.numEstados = estados;		
		inicializacionesPanel();
		calculoEstados();
		calculoTransiciones(transiciones);
	}


	public void inicializacionesPanel () {        
		this.setPreferredSize(new Dimension(1000,1000));		
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
		for (int i=0; i<numEstados; i++) { 
			Rectangle ajusteLabel = new Rectangle((int)(diamEst/2)-10, (int)(diamEst/2)-15, 30, 30);
			estados.add(new Estado(diamEst,ajusteLabel));
			estados.get(i).setLabel(new JLabel ("Q"+i));
			if (i<numEstados)
				estados.get(i).setProbabilidadFinal(probabilidadFinal[i]);			
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
			estados.get(i).setBoundsLabel(x,y);
			alfa+=incremento;
			x=xini;
			y=yini;
		}

		for (int i=0; i<numEstados; i++) {
			add(estados.get(i).getLabel());
			estados.get(i).setIndice(i);
		}

	}

	public void calculoTransiciones(double [][][] transicionesArray) {

		transiciones = new Transicion[numEstados][numEstados];
		System.out.println("Longitud de la primera dimension."+transicionesArray.length);
		System.out.println("Longitud de la segunda dimension."+transicionesArray[0].length);
		System.out.println("Longitud de la tercera dimension."+transicionesArray[0][0].length);

		for (int i=0; i<transicionesArray.length; i++ ) {
			for (int k=0; k<transicionesArray[i][0].length; k++) {
				transiciones[i][k] = new Transicion (estados.get(i),estados.get(k),
						transicionesArray[i][0][k],transicionesArray[i][1][k]);

				transiciones[i][k].setLabel0(new JLabel());
				transiciones[i][k].setLabel1(new JLabel());
				add(transiciones[i][k].getLabel0());								
				add(transiciones[i][k].getLabel1());

			}	
		}
	}
	
	public void calculoTransiciones2(double [][][] transicionesArray) {

		transiciones2 = new ArrayList<Transicion>();		
		for (int i=0; i<transicionesArray.length; i++ ) {
			for (int k=0; k<transicionesArray[i][0].length; k++) {
				transiciones2.add(new Transicion (estados.get(i),estados.get(k),
						transicionesArray[i][0][k],transicionesArray[i][1][k]));
				transiciones2.get(transiciones2.size()-1).setLabel0(new JLabel());
				transiciones2.get(transiciones2.size()-1).setLabel1(new JLabel());
				add(transiciones2.get(transiciones2.size()-1).getLabel0());								
				add(transiciones2.get(transiciones2.size()-1).getLabel1());

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
		pintaTransiciones2(g2);
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

		for (int i = 0; i<estados.size(); i++)  
			((Estado)(estados.get(i))).paintComponent(g);


		//Pinta triangulo del estado inicial
		if (estados.size()>0) {
			Estado q0 = estados.get(0);					
			int[] xTriangulo = new int[3];
			int[] yTriangulo = new int[3];
			xTriangulo[0] = (int)(q0.getCentro().getX()-q0.getRadio());
			yTriangulo[0] = (int)q0.getCentro().getY();
			xTriangulo[1] = (int)(xTriangulo[0]+Math.cos(Math.toRadians(135))*q0.getRadio());
			yTriangulo[1] = (int)(yTriangulo[0]+Math.sin(Math.toRadians(135))*q0.getRadio());
			xTriangulo[2] = (int)(xTriangulo[0]+Math.cos(Math.toRadians(225))*q0.getRadio());
			yTriangulo[2] = (int)(yTriangulo[0]+Math.sin(Math.toRadians(225))*q0.getRadio());
			Polygon triangulo = new Polygon(xTriangulo, yTriangulo, 3);
			g.drawPolygon(triangulo);
		}


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
				trans.pinta(transicionCon0, transicionCon1,g);

			}
		}

	}
	
	public void pintaTransiciones2 (Graphics2D g) {
		for (int i=0; i<transiciones2.size(); i++) {			
			Transicion trans = transiciones2.get(i);
			boolean transicionCon0 = trans.getProbabilidad0() > 0.1;
			boolean transicionCon1 = trans.getProbabilidad1() > 0.1;
			trans.pinta(transicionCon0, transicionCon1,g);
		}

	}

	public int getNumEstados() {
		return numEstados;
	}
	
	public AF getAutomata() {
		return automataMejor;
	}
	
	public void addEstado (Estado e) {
		estados.add(e);
		e.setIndice(numEstados);
		estadosFinales.add(new Boolean(false));
		numEstados++;
	}
	
	public void addTransicion (Transicion t) {			
		transiciones2.add(t);
		
	}
	
	public void addLabelEstado (JLabel label) {
		add(label);
	}
	
	public void removeLabel (JLabel label) {
		if (label!=null)
			remove(label);
	}
	
	public void setModo(int modo) {
		this.modo = modo;
	}
	
	public int getModo() {
		return modo;
	}
	
	public Transicion buscaTransiciones (Estado estadoInicio, Estado estadoFin) {
		for (int i=0; i<transiciones2.size();i++) {
			if (transiciones2.get(i).getOrigen()==estadoInicio && 
				transiciones2.get(i).getDestino()==estadoFin)
				return transiciones2.get(i);			
		}
		return null;
	}
	
	public Transicion buscaTransiciones(int valor, Estado estadoInicio) {
		for (int i=0; i<transiciones2.size(); i++) {
			if (transiciones2.get(i).getOrigen()==estadoInicio) {
				if (valor==0) {
					if (transiciones2.get(i).getProbabilidad0()==1)
						return transiciones2.get(i);
				}
				else { //valor==1					
					if (transiciones2.get(i).getProbabilidad1()==1)
						return transiciones2.get(i);
				}
			}
		}
		return null;
		
	}
	
	public List<Estado> getEstados () {
		return estados;
	}
	
	public List<Transicion> getTransiciones2 () {
		return transiciones2;
	}

	/*
	public void paint(Graphics g) {
		super.paint(g);	
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);				
		pintaTransiciones(g2);
		pintaEstados(g2);	
	}
	 */
}
