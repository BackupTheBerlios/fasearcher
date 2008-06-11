package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * Panel que muestra informacion acerca del estado de la ejecucion
 *
 */
public class PanelInfo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7534236970898154516L;
	/**
	 * Texto que se muestra
	 */
	private JTextArea texto;
	/**
	 * Controla el tiempo que va pasando
	 */
	private Timer timer;
	/**
	 * Constructora que hace las inicializaciones
	 */
	public PanelInfo() {
		texto = new JTextArea();
		texto.setEditable(false);
		add(texto);
		repaint();
	}
	/**
	 * Muestra el mensaje pasado por parametro
	 * @param mensaje
	 */
	public void mostarMensaje(String mensaje) {
		texto.setText(mensaje);
		repaint();
	}
	/**
	 * Pone el texto "Resolviendo..." cuando esta
	 * resolviendo un problema 
	 */
	public void resolviendo()  {
		texto.setText("Resolviendo...");
		repaint();
		TimerResolviendo timerResolviendo = new TimerResolviendo(this);
		timer = new Timer();
		timer.scheduleAtFixedRate(timerResolviendo, 1000, 1000);
	}
	/**
	 * Cuando termina de resolver pone el mensaje pasado
	 * por parametro
	 * @param mensaje
	 */
	public void terminaResolviendo(String mensaje) {
		if (mensaje != null)
			texto.setText(mensaje);
		else
			texto.setText("");
		timer.cancel();
		repaint();
	}
	/**
	 * Clase privada para controlar el tiempo
	 *
	 */
	private class TimerResolviendo extends TimerTask {
		/**
		 * Contador
		 */
		private int cont;
		/**
		 * Panel de informacion
		 */
		private PanelInfo panelInfo;
		/**
		 * Constructora que inicializa
		 * @param panelInfo
		 */
		public TimerResolviendo(PanelInfo panelInfo) {
			cont = 0;
			this.panelInfo = panelInfo;
		}
		
		/**
		 * Pone en marcha la ejecucion, añadiendo un punto "."
		 * cada vez
		 */
		public void run() {
			cont++;
			cont = cont % 4;
			String temp = "Resolviendo";
			for (int i = 0; i < cont; i++)
				temp += ".";
			panelInfo.mostarMensaje(temp);
		}
		
	}
}
