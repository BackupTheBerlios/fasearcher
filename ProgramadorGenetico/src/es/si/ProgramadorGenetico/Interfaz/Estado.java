package es.si.ProgramadorGenetico.Interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Estado extends JComponent{

		private Point punto;
		private double diametro;
		private JLabel label;
		private double probabilidadFinal;
		
		public Estado () {
			punto = new Point();
			diametro = 40;
			label = new JLabel();
		}
		public Estado (Point punto, int diametro, JLabel etiqueta) {
			this.punto = punto;
			this.label = etiqueta;
			this.diametro = diametro;
		}
		
		public void setLabel (JLabel label) {
			this.label = label;
		}
		
		public JLabel getLabel () {
			return label;
		}
		
		public void setPunto (Point p) {
			this.punto = p;
		}
		
		public void setDiametro (double diam) {
			this.diametro = diam;
		}
		
		public double getDiametro () {
			return diametro;
		}
		
		public Point getCentro() {
			int x = (int)(punto.getX()+diametro/2.0);
			int y = (int)(punto.getY()+diametro/2.0);			
			return new Point(x,y);
		}
		
		public double getRadio() {
			return diametro/2.0;
		}
		
		public Point getPunto() {
			return punto;
		}
		
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.yellow);			
			g.fillOval((int)punto.getX(), (int)punto.getY(), (int)diametro, (int)diametro);
	        g.drawOval((int)punto.getX(), (int)punto.getY(), (int)diametro, (int)diametro);
	        g.setColor(Color.black);
	        g.drawOval((int)punto.getX(), (int)punto.getY(), (int)diametro, (int)diametro);
	        if (esFinal()) {	        	
	        	g.drawOval((int)punto.getX()+4, (int)punto.getY()+4, (int)(diametro-8), (int)(diametro-8));
	        }
		}
		
		public boolean getPulsado (Point p) {
			if ( (getCentro().distance(p)) <= getRadio() )
				return true;
			return false;
						
		}
		
		public void actualizaPosicion(Point nuevoPunto) {
			punto.setLocation(nuevoPunto.x-getRadio(), nuevoPunto.y-getRadio());			
			label.setBounds((int)punto.getX()+10, (int)punto.getY()+10, label.getWidth(), label.getHeight());
		}
		
		public boolean esFinal() {
			if (probabilidadFinal > 0.9)
				return true;
			return false;
		}
		
		public void setProbabilidadFinal (double prob) {
			probabilidadFinal = prob;
		}

}
