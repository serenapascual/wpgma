package wpgma;

import java.util.*;

import javax.swing.event.*;

/**
 * @author Serena Pascual and Erin Yang
 * Model for the distance matrix
 */

public class DataModel {
	private HashMap<String, HashMap<String, Double>> distances;
	private ArrayList<ChangeListener> listeners;
	private int size;
	
	public DataModel() {
		distances = new HashMap<String, HashMap<String, Double>>();
		listeners = new ArrayList<ChangeListener>();
		size = 0;
	}
	
	
	/**
	 * Returns the distance matrix in hash table format
	 * @return the distance matrix in hash table format
	 */
	public HashMap<String, HashMap<String, Double>> getDistances() {
		return distances;
	}
	
	/**
	 * Returns the intended number of elements in the matrix
	 * @return the intended number of elements in the matrix
	 */
	public int getSize() {
		return size;
	}
	
	public void setSize(int i) {
		size = i;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	public void attach(ChangeListener c) {
		listeners.add(c);
	}
	
	public void addElements(int n) {
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < n + 1;  j++) {
				HashMap<String, Double> m = new HashMap<String, Double>();
				m.put("" + j, (double) 0);
				distances.put("" + i, m);
			}
		}
		setSize(distances.size());
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}
