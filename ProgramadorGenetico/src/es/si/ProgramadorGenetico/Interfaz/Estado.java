package es.si.ProgramadorGenetico.Interfaz;

import java.awt.Point;

import javax.swing.JLabel;

public class Estado {

		private Point punto;
		private int diametro;
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
		
		public void setDiametro (int diam) {
			this.diametro = diam;
		}
		
		public int getDiametro () {
			return diametro;
		}
		
		public Point getCentro() {
			int x = (int)(punto.getX()+diametro/2.0);
			int y = (int)(punto.getY()+diametro/2.0);			
			return new Point(x,y);
		}
}
