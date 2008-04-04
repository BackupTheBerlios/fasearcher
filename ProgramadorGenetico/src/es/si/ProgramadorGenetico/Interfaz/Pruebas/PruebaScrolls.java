package es.si.ProgramadorGenetico.Interfaz.Pruebas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PruebaScrolls {

	private static JScrollPane scrollPane;
	private static JPanel panel;
	private static JFrame frame;
	
	public static void main (String[] args) {		
		iniciar();
	}
	
	public static void iniciar() {
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension (2000,2000));
		panel.add(new JLabel("HOLA"));
		panel.setBackground(Color.blue);
		System.out.println("0");
		scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(400,400));
		System.out.println("1");
		frame = new JFrame();
		frame.add(scrollPane);
		System.out.println("2");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        
	}
}
