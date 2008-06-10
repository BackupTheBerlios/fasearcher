package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class FrameSobreAplicacion extends JFrame {

	private static final long serialVersionUID = -5085114999247625745L;
	
	public FrameSobreAplicacion() {
		super("Sobre la aplicaci�n");

		setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Sobre el Administrador...");
		
		add(label, BorderLayout.NORTH);

		JTextPane text = new JTextPane();
		
		String descripcion = "";
		
		descripcion += "Este es el administrador del \"Programador Gen�tico\"." + System.getProperty("line.separator");
		descripcion += "Por medio de este programa se puede adminstrar los problemas y " +
						"soluciones del sistema. Adem�s, presenta utilidades para ver y " +
						"analizar estad�sticas." + System.getProperty("line.separator") +
						"Entre otras funciones, se pueden crear automatas aleatorios, " +
						"buscar soluciones de forma local, y otras numerosas funciones.";
		
		text.setText(descripcion);
		text.setEditable(false);
		add(text, BorderLayout.CENTER);
		
		setSize(400,200);
			
		setVisible(true);
	}

}
