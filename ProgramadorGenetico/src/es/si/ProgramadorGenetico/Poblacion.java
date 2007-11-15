package es.si.ProgramadorGenetico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que amacena el conjunto de individuos que componen una población para el algoritmo.
 * 
 * @author Eugenio Jorge Marchiori
 *
 */
public class Poblacion {

	private ArrayList<Individuo> miembros;
	
	public List<Individuo> getMiembros() {
		return miembros;
	}
	
	public void setMiembros(List<Individuo> miembros) {
		this.miembros = new ArrayList<Individuo> (miembros);
	}
	
	public void agregarMiembro(Individuo nuevo) {
		miembros.add(nuevo);
	}
	
	public void quitarMiembro(Individuo miembro) {
		miembros.remove(miembro);
	}
	
	public Iterator<Individuo> getIterator() {
		return miembros.iterator();
	}
}
