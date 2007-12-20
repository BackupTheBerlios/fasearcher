package es.si.ProgramadorGenetico.Interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Transicion {

	public Estado origen;
	public Estado destino;
	public double probabilidad0;
	public double probabilidad1;
	public JLabel label;

	public Transicion () {
		
	}
	
	public Transicion (Estado origen, Estado destino, double probabilidad0, double probabilidad1) {
		this.origen = origen;
		this.destino = destino;
		this.probabilidad0 = probabilidad0;
		this.probabilidad1 = probabilidad1;
	}
	
	public Estado getOrigen () {
		return origen;
	}
	
	public Estado getDestino () {
		return destino;
	}
	
	public double getProbabilidad0 () {
		return probabilidad0;
	}
	
	public double getProbabilidad1 () {
		return probabilidad1;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setLabel (JLabel l) {
		label = l;
	}
	
	public void pinta (DibujanteNuevo panel) {
		panel.getGraphics().drawOval(0,0,0,0);
		Graphics2D g = (Graphics2D) panel.getGraphics();
		if (origen==destino) {
			int radioArco = origen.getDiametro()/2;
			int diametro = origen.getDiametro()/2;
			double incremento = 360/(double)(panel.getNumEstados());
			double ang = 180-i*incremento;
			Point centro = origen.getCentro();
			int despX = (int)(centro.getX()+(origen.getDiametro()/2)*Math.cos(Math.toRadians(ang)));
			int despY = (int)(centro.getY()-(origen.getDiametro()/2)*Math.sin(Math.toRadians(ang)));						
			Color colorTransicion = getColorArco(i,j);
			g.setColor(colorTransicion);
			g.drawOval(despX-radioArco/2, despY-radioArco/2, radioArco, radioArco);
			//g.drawOval((int)puntosEstados[i].getX()+d/2-d, (int)puntosEstados[i].getY()+d/2-d, radioArco, radioArco);
			System.out.println("ang:"+ang+"despX: "+despX+" despY: "+despY);
			double xPuntoValor = (double)(centro.getX()+(1.5*diametro)*Math.cos(Math.toRadians(ang)));
			double yPuntoValor = (double)(centro.getY()-(1.5*diametro)*Math.sin(Math.toRadians(ang)));
			label.setBounds((int)xPuntoValor,
					(int)yPuntoValor,20,20);
			asignaEtiquetas(i, j, transicionCon0, transicionCon1);

		}
		
	}
	
	public double getAnguloEstados () {
		double xOrigen = origen.getCentro().getX();
		double yOrigen = origen.getCentro().getY();
		double xDestino = destino.getCentro().getX();
		double yDestino = destino.getCentro().getY();
		double catOpuesto = yOrigen - yDestino;
		double catContiguo = xDestino - xOrigen;
		double cosAng = catOpuesto / catContiguo;
		return (Math.toDegrees((Math.acos(cosAng))));
		
	}
	
	/*
	public double distanciaEstados () {
		double xOrigen = origen.getCentro().getX();
		double yOrigen = origen.getCentro().getY();
		double xDestino = destino.getCentro().getX();
		double yDestino = destino.getCentro().getY();
		
	}
	 */

}
