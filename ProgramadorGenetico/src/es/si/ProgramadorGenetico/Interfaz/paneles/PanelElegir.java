package es.si.ProgramadorGenetico.Interfaz.paneles;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelElegir extends JPanel {

	private static final long serialVersionUID = -9053446529236927066L;

	private List<String> lista_string;
	
	private List<Integer> lista_integer;
	
	private List<String> seleccion_string;
	
	private List<Integer> seleccion_integer;
	
	private JTable tabla;
	
	private DefaultTableModel model;
	
	private JComboBox combo;
	
	public PanelElegir(String titulo) {
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		combo = new JComboBox();
		temp.add(combo);
		
		JButton boton = new JButton("Añadir");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelElegir.this.agregar();
			}
		});
		temp.add(boton);
		
		JPanel temp2 = new JPanel();
		temp2.setLayout(new GridLayout(1,2));
		temp2.add(temp);
		
		model = new DefaultTableModel();
		tabla = new JTable(model);
		model.addColumn("");
		JScrollPane pane = new JScrollPane(tabla);
		temp2.add(pane);
		
		setLayout(new BorderLayout());
		add(new JLabel(titulo), BorderLayout.NORTH);
		add(temp2, BorderLayout.CENTER);
		
	}
	
	
	protected void agregar() {
		String[] fila = new String[1];
		boolean poner = true;
		if (combo.getSelectedItem() != null) {
			if (combo.getSelectedItem() instanceof String) {
				if (seleccion_string.contains((String) combo.getSelectedItem()))
					poner = false;
				else
					seleccion_string.add((String) combo.getSelectedItem());
			}
			if (combo.getSelectedItem() instanceof Integer) {
				if (seleccion_integer.contains((Integer) combo.getSelectedItem()))
					poner = false;
				else
					seleccion_integer.add((Integer) combo.getSelectedItem());
			}
			
			if (poner) {
				fila[0] = "" + combo.getSelectedItem();
				model.addRow(fila);
			}
		}
		
		repaint();
		revalidate();
	}


	private void llenarCombo() {
		if (lista_string != null) {
			for (String el : lista_string) 
				combo.addItem(el);
			seleccion_string = new ArrayList<String>();
		}
		if (lista_integer != null) {
			for (Integer el : lista_integer)
				combo.addItem(el);
			seleccion_integer = new ArrayList<Integer>();
		}
	}


	public List<String> getSeleccion_string() {
		return seleccion_string;
	}


	public List<Integer> getSeleccion_integer() {
		return seleccion_integer;
	}


	public void setLista_string(List<String> lista_string) {
		this.lista_string = lista_string;
		llenarCombo();
	}


	public void setLista_integer(List<Integer> lista_integer) {
		this.lista_integer = lista_integer;
		llenarCombo();
	}
}
