package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelInfo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7534236970898154516L;
	
	private JTextArea texto;
	
	private Timer timer;
	
	public PanelInfo() {
		texto = new JTextArea();
		texto.setEditable(false);
		add(texto);
		repaint();
	}
	
	public void mostarMensaje(String mensaje) {
		texto.setText(mensaje);
		repaint();
	}
	
	public void resolviendo()  {
		texto.setText("Resolviendo...");
		repaint();
		TimerResolviendo timerResolviendo = new TimerResolviendo(this);
		timer = new Timer();
		timer.scheduleAtFixedRate(timerResolviendo, 1000, 1000);
	}
	
	public void terminaResolviendo(String mensaje) {
		if (mensaje != null)
			texto.setText(mensaje);
		else
			texto.setText("");
		timer.cancel();
		repaint();
	}
	
	private class TimerResolviendo extends TimerTask {

		private int cont;
		
		private PanelInfo panelInfo;
		
		public TimerResolviendo(PanelInfo panelInfo) {
			cont = 0;
			this.panelInfo = panelInfo;
		}
		
		@Override
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
