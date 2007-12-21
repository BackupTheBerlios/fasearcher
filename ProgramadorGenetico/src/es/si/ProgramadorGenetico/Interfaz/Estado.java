package es.si.ProgramadorGenetico.Interfaz;

import java.awt.Point;

import javax.swing.JLabel;

public class Estado {

		private Point punto;
		private double diametro;
		private JLabel label;
		
		public Estado () {}
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
}
