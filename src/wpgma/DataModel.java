package wpgma;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * @author Serena Pascual and Erin Yang
 * Model for the distance matrix
 */

public class DataModel {
	private MainView view;
	private HashMap<String, HashMap<String, Double>> elements1;
	private HashMap<String,Double> elements2;
	private int size;
	
	public DataModel() {
		elements1 = new HashMap<String, HashMap<String, Double>>();
		elements2 = new HashMap<String, Double>();
		size = 0;
	}
	
	public void setView(MainView view) {
		this.view = view;
	}
	
	public MainView getView() {
		return view;
	}
	
	/**
	 * Returns the distance matrix in hash table format
	 * @return the distance matrix in hash table format
	 */
	public HashMap<String, HashMap<String, Double>> getDistances() {
		return elements1;
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
		view.newTable();
	}
	
	
	public void addElements() {
		for (int i = 0; i < view.getCells().size(); i++) {
			String name = view.getLabels().get(i / size).getText();
			double distance = 0;
			try {
				distance = Integer.parseInt(view.getCells().get(i).getText());
			}
			catch (NumberFormatException e) {
				JFrame frame = new JFrame();
            	JOptionPane.showMessageDialog(frame,
                        "Please enter a positive numeric value in each cell of the distance matrix.",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            	break;
			}
			if (distance < 0) {
				JFrame frame = new JFrame();
            	JOptionPane.showMessageDialog(frame,
                        "Please enter a positive numeric value in each cell of the distance matrix.",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            	break;
			}
			String name2 = view.getLabels().get(i % size).getText();
			elements2.put(name2, distance);
			elements1.put(name, elements2);
		}
	}
}
