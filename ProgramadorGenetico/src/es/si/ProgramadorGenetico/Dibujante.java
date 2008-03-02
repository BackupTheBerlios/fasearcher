package es.si.ProgramadorGenetico;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import es.si.ProgramadorGenetico.Interfaz.Estado;
import es.si.ProgramadorGenetico.Interfaz.Transicion;

public interface Dibujante{

	
	public void calculoTransiciones(double [][][] transicionesArray);
	public void calculoEstados();
		
}
