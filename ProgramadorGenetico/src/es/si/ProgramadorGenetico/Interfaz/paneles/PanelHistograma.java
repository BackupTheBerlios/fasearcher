package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JPanel;
/**
 * Panel que muestra un histograma con los datos
 * de las soluciones de los problemas
 *
 */
public class PanelHistograma extends JPanel {
	
	private static final long serialVersionUID = -4630247306064571085L;
	/**
	 * Lista de los valores a representar
	 */
	private List<Double> valores;
	/**
	 * Titulo del grafico
	 */
	private String titulo;
	/**
	 * Constructora que inicializa los atributos
	 * @param valores
	 * @param titulo
	 */
	public PanelHistograma(List<Double> valores, String titulo) {
		this.valores = valores;
		this.titulo = titulo;
	}
	/**
	 * Se encarga de pintar el grafico
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		if (width * height == 0)
			return;
	
		if (valores.size() == 0)
			return;
		
		double escala;
		if (getMax(valores) > 0)
			escala = (double) (height - 25) / getMax(valores) ;
		else
			escala = 1;
		
		double paso = (width - 16) / (valores.size());
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		
		for (int i = 0; i < valores.size(); i++) {
			g2.setColor(Color.PINK);
			g2.fillRect((int)(paso*i + 8), (height -15) - (int) (valores.get(i) * escala), (int)paso, (int) (valores.get(i) * escala));
			g2.setColor(Color.BLACK);
			g2.drawRect((int)(paso*i + 8), (height -15) - (int) (valores.get(i) * escala), (int)paso, (int) (valores.get(i) * escala));
			g2.drawString(nf.format(100 * valores.get(i) / suma(valores)), (int)paso*i + 8, (height -15) - (int) (valores.get(i) * escala));
			g2.drawString(nf.format((1.0f / valores.size())*i),(int)(paso*i),height-2);
		}
		g2.setColor(Color.RED);
		g2.drawString(titulo, 20, 20);
		g2.setColor(Color.BLACK);
		g2.drawString(nf.format(1.0f), (int) (paso*valores.size() - 8), height -2);
	}
	/**
	 * Devuelve el maximo de la lista
	 * @param valores
	 * @return
	 */
	private double getMax(List<Double> valores) {
		double temp = 0;
		for (Double d : valores) {
			if (d > temp)
				temp = d;
		}
		return temp;
	}
	/**
	 * Suma todos los valores de la lista y devuelve el valor
	 * @param valores
	 * @return
	 */
	private double suma(List<Double> valores) {
		double temp = 0;
		for (Double d : valores) {
			temp += d;
		}
		if (temp == 0)
			temp = 1;
		return temp;
	}
}
