package es.si.ProgramadorGenetico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que amacena el conjunto de individuos que componen una población.<p>
 * 
 * Esta clase es utilizada por el algoritmo para almacenar los individuos
 * en cada iteración del algoritmo genético.
 * 
 */
public class Poblacion {

	/**
	 * Lista de miembros (individuos) de la poblacion
	 */
	private ArrayList<Individuo> miembros;
	
	/**
	 * Constructor por defecto de la población
	 */
	public Poblacion() {
		miembros = new ArrayList<Individuo>();
	}
	
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
	
	public int getCantidad(){
		return miembros.size();
	}
	
	public Individuo getMiembro(int index) {
		return miembros.get(index);
	}
	
	public Iterator<Individuo> getIterator() {
		return miembros.iterator();
	}
}
