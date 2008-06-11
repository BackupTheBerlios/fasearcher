package es.si.ProgramadorGenetico.Interfaz.paneles;

import es.si.ProgramadorGenetico.Interfaz.componentes.AF;
import es.si.ProgramadorGenetico.Interfaz.componentes.Estado;
import es.si.ProgramadorGenetico.Interfaz.componentes.Transicion;
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;
import es.si.ProgramadorGenetico.Individuo;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que posibilita tanto mostrar AF y AFPs en pantalla como editarlos.
 */
public class SubPanelAF extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2948123683167268516L;

	/**
	 * Probabilidad final
	 */
	private float[] probabilidadFinal;

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
	 * Lista de transiciones del AF o AFP representado
	 */
	private List<Transicion> transiciones;

	/**
	 * Lista de estados finales del AF o AFP representado
	 */
	private List<Boolean> estadosFinales;

	/**
	 * Automata
	 */
	private AF automataMejor;
	
	/**
	 * Flag que indica si el AF o AFP representado puede ser editado (añadir estados, transiciones, etc)
	 */
	private boolean editable;

	/**
	 * Modo de edición seleccionado actualmente por el usuario
	 */
	private int modo;
	
	public static final int INSERTAR_ESTADO = 1;
	public static final int INSERTAR_TRANSICION = 2;
	public static final int FINALIZAR_TRANSICION = 3;
	public static final int SET_FINAL = 4;
	public static final int EDICION = 0;

	/**
	 * Constructor por defecto, con un AF vacío
	 */
	public SubPanelAF() {
		super(new GridLayout(0, 1));
		inicializacionesPanel();
		estados = new ArrayList<Estado>();
		transiciones = new ArrayList<Transicion>();
		estadosFinales = new ArrayList<Boolean>();
		numEstados = 0;
		editable = true;
	}

	/**
	 * Esta constructora recibe el mejor automata seleccionado, en forma
	 * de Individuo, por lo que puede ser un AF o un AFP. Inicializa los
	 * arrays y crea las etiquetas
	 * 
	 * @param mejor
	 *            Automata seleccionado
	 */
	public SubPanelAF(Individuo mejor) {
		super(new GridLayout(0, 1));
		editable = true;
		if (mejor instanceof AFP) {
			automataMejor = new AF((AFP)mejor);
			editable = false;
		} else {
			automataMejor = (AF) mejor;
		}
		float[][][] transicionesArray;
		transicionesArray = automataMejor.getTransiciones();
		numEstados = automataMejor.getEstados();
		probabilidadFinal = automataMejor.getFinales();
		inicializacionesPanel();
		calculoEstados();
		calculoTransiciones(transicionesArray);

	}

	/**
	 * Constructora que recibe en 3 parametros los datos necesarios. Estos
	 * datos son las transiciones, las probabilidades de finalizar de un
	 * estado y los estados del autómata.
	 * 
	 * @param transiciones
	 * @param probabilidadFinal
	 * @param estados
	 */
	public SubPanelAF(float[][][] transiciones, float[] probabilidadFinal,
			int estados) {
		super(new GridLayout(0, 1));
		editable = true;
		this.probabilidadFinal = probabilidadFinal;
		this.numEstados = estados;
		inicializacionesPanel();
		calculoEstados();
		calculoTransiciones(transiciones);
	}

	
	/**
	 * Método para inicializar distintas características del panel, utilizado
	 * por los constructores.
	 */
	private void inicializacionesPanel() {
		this.setPreferredSize(new Dimension(2000, 2000));
		this.setLayout(null);
		MIAPanelAF oyente = new MIAPanelAF(this);
		addMouseListener(oyente);
		addMouseMotionListener(oyente);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}

	/**
	 * Se calculan las posiciones iniciales de los estados
	 * 
	 */
	private void calculoEstados() {
		estados = new ArrayList<Estado>();
		// Creamos un estado de mas
		for (int i = 0; i < numEstados; i++) {
			Rectangle ajusteLabel = new Rectangle((int) (diamEst / 2) - 10,
					(int) (diamEst / 2) - 15, 30, 30);
			estados.add(new Estado(diamEst, ajusteLabel));
			estados.get(i).setLabel(new JLabel("Q" + i));
			if (i < numEstados)
				estados.get(i).setProbabilidadFinal(probabilidadFinal[i]);
		}
		// el centro de la circunferencia imaginaria esta en el centro
		int xini = (int) (this.getPreferredSize().getWidth() / 2);
		int yini = (int) (this.getPreferredSize().getHeight() / 2);
		x = xini;
		y = yini;
		// calculo el radio, angulo e incremento
		double alfa = 180;
		double incremento = 360 / (double) (numEstados);
		// Colocamos las etiquetas en los vertices del poligono
		for (Estado estado : estados) {
			x += radioPolig * Math.cos(Math.toRadians(alfa));
			y += radioPolig * Math.sin(Math.toRadians(alfa));
			estado.setPunto(new Point(x, y));
			estado.setBoundsLabel(x, y);
			alfa += incremento;
			x = xini;
			y = yini;
		}

		for (int i = 0; i < numEstados; i++) {
			add(estados.get(i).getLabel());
			estados.get(i).setIndice(i);
		}

	}

	private void calculoTransiciones(float[][][] transicionesArray) {
		transiciones = new ArrayList<Transicion>();
		for (int i = 0; i < transicionesArray.length; i++) {
			for (int k = 0; k < transicionesArray[i][0].length; k++) {
				transiciones.add(new Transicion(estados.get(i),
						estados.get(k), transicionesArray[i][0][k],
						transicionesArray[i][1][k]));
				transiciones.get(transiciones.size() - 1).setLabel0(
						new JLabel());
				transiciones.get(transiciones.size() - 1).setLabel1(
						new JLabel());
				add(transiciones.get(transiciones.size() - 1).getLabel0());
				add(transiciones.get(transiciones.size() - 1).getLabel1());

			}
		}
	}

	/**
	 * Llama a pintar los estados y las transiciones
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		pintaTransiciones(g2);
		pintaEstados(g2);
	}

	/**
	 * Pinta los estados
	 * 
	 * @param g
	 */
	private void pintaEstados(Graphics2D g) {
		for (Estado estado : estados) {
			g.setColor(Color.yellow);
			estado.paintComponent(g);
		}

		g.setColor(Color.black);

		// Pinta triangulo del estado inicial
		if (estados.size() > 0) {
			Estado q0 = estados.get(0);
			int[] xTriangulo = new int[3];
			int[] yTriangulo = new int[3];
			xTriangulo[0] = (int) (q0.getCentro().getX() - q0.getRadio());
			yTriangulo[0] = (int) q0.getCentro().getY();
			xTriangulo[1] = (int) (xTriangulo[0] + Math
					.cos(Math.toRadians(135))
					* q0.getRadio());
			yTriangulo[1] = (int) (yTriangulo[0] + Math
					.sin(Math.toRadians(135))
					* q0.getRadio());
			xTriangulo[2] = (int) (xTriangulo[0] + Math
					.cos(Math.toRadians(225))
					* q0.getRadio());
			yTriangulo[2] = (int) (yTriangulo[0] + Math
					.sin(Math.toRadians(225))
					* q0.getRadio());
			Polygon triangulo = new Polygon(xTriangulo, yTriangulo, 3);
			g.drawPolygon(triangulo);
		}
	}

	private void pintaTransiciones(Graphics2D g) {
		for (Transicion trans : transiciones) {
			boolean transicionCon0 = trans.getProbabilidad0() > 0.1;
			boolean transicionCon1 = trans.getProbabilidad1() > 0.1;
			trans.pinta(transicionCon0, transicionCon1, g);
		}
	}

	public int getNumEstados() {
		return numEstados;
	}

	public AF getAutomata() {
		return automataMejor;
	}

	public void addEstado(Estado e) {
		estados.add(e);
		e.setIndice(numEstados);
		estadosFinales.add(new Boolean(false));
		numEstados++;
	}

	public void addTransicion(Transicion t) {
		transiciones.add(t);
	}

	/**
	 * Añadir una etiqueta al panel.
	 * 
	 * @param label JLabel que se desea añadir
	 */
	public void addLabelEstado(JLabel label) {
		add(label);
	}

	/** 
	 * Quita una etiqueta del panel, para que ya no sea dibujada.
	 * 
	 * @param label JLabel que se desea quitar
	 */
	public void removeLabel(JLabel label) {
		if (label != null)
			remove(label);
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public int getModo() {
		return modo;
	}

	/**
	 * Método que devuelve la transición que empieza en el estado dado
	 * como primer parámetro y termina en el dado como segundo parámetro.
	 * Devuelve null si no existe dicha transición.
	 * 
	 * @param estadoInicio Estado inicial de la transición
	 * @param estadoFin Estado final de la transición
	 * @return Transición que va de estado dado en estado dado. null si no existe
	 */
	public Transicion buscaTransiciones(Estado estadoInicio, Estado estadoFin) {
		for (Transicion trans : transiciones)  {
			if (trans.getOrigen() == estadoInicio
					&& trans.getDestino() == estadoFin)
				return trans;
		}
		return null;
	}

	/**
	 * Método que devuelve la transicion para el valor dado como parámetro
	 * que inicia en el estado dado también como parámetro
	 * 
	 * @param valor Valor de la transicion
	 * @param estadoInicio Estado inicial de la transición
	 * @return Transicion que empieza en estadoInicio para el valor dado
	 */
	public Transicion buscaTransiciones(int valor, Estado estadoInicio) {
		for (Transicion trans : transiciones)  {
			if (trans.getOrigen() == estadoInicio) {
				if (valor == 0) {
					if (trans.getProbabilidad0() == 1)
						return trans;
				} else { // valor==1
					if (trans.getProbabilidad1() == 1)
						return trans;
				}
			}
		}
		return null;
	}

	/**
	 * Método para obtener la lista de estados
	 * 
	 * @return Lista de estados
	 */
	public List<Estado> getEstados() {
		return estados;
	}

	/**
	 * Método para obtener las transiciones como una lista
	 * 
	 * @return Lista de transiciones
	 */
	public List<Transicion> getTransicionesList() {
		return transiciones;
	}
	
	/**
	 * Método para obtener las transiciones como una matriz.
	 * 
	 * @return Matriz de transiciones
	 */
	public Transicion[][] getTransicionesArray() {
		Transicion[][] temp = new Transicion[estados.size()][estados.size()];
		for (Estado init : estados) {
			for (Estado fin : estados) {
				temp[init.getIndice()][fin.getIndice()] = buscaTransiciones(init, fin);
			}
		}
		return temp;
	}
	

	/**
	 * Devuelve el estado que esta en el punto dado como parámetro.
	 * 
	 * @param puntoClick Punto donde se debe buscar el estado
	 * @return El estado en el punto, o null si no hay estado en dicho punto.
	 */
	public Estado buscaEstado(Point puntoClick) {
		for (Estado es : estados) {
			if (es.getPulsado(puntoClick))
				return es;
		}
		return null;
	}

	public float[] getProbabilidadFinal() {
		return probabilidadFinal;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public int getDiamEst() {
		return diamEst;
	}
}
