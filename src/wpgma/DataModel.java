package wpgma;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * Model for distance matrix
 * @author Serena Pascual and Erin Yang
 */

public class DataModel {
	private MainView view;
	private HashMap<String, HashMap<String, Double>> elements1;
	private HashMap<String,Double> elements2;
	private int size;
	
	public DataModel() {
		// initialize instance variables
		elements1 = new HashMap<String, HashMap<String, Double>>();
		elements2 = new HashMap<String, Double>();
		size = 0;
	}
	
	/**
	 * Sets the instance of MainView to reflect the data model
	 * @param view the view to reflect the data model
	 */
	public void setView(MainView view) {
		this.view = view;
	}
	
	/**
	 * Gets the instance of MainView reflecting the data model
	 * @return the view reflecting the data model
	 */
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
	
	/**
	 * Sets the intended number of elements in the matrix
	 * @param i the intended number of elements in the matrix
	 */
	public void setSize(int i) {
		size = i;
		view.newTable();
	}
	
	/**
	 * Retrieves the matrix values input by the user and stores in a nested HashMap
	 */
	public void addElements() {
		// iterate through all cells in the matrix
		for (int i = 0; i < view.getCells().size(); i++) {
			// retrieve label corresponding to element at current iteration
			String name = view.getLabels().get(i / size).getText();
			double distance = 0;
			// get the value at this particular element
			try {
				distance = Integer.parseInt(view.getCells().get(i).getText());
			}
			// error handling to ensure there is a valid number
			catch (NumberFormatException e) {
				JFrame frame = new JFrame();
            	JOptionPane.showMessageDialog(frame,
                        "Please enter a positive numeric value in each cell of the distance matrix.",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            	break;
			}
			// error handling for runtime error from a negative number
			if (distance < 0) {
				JFrame frame = new JFrame();
            	JOptionPane.showMessageDialog(frame,
                        "Please enter a positive numeric value in each cell of the distance matrix.",
                        "Invalid input",
                        JOptionPane.WARNING_MESSAGE);
            	break;
			}
			// retrieve label for second element being compared against
			String name2 = view.getLabels().get(i % size).getText();
			// populate inner HashMap
			elements2.put(name2, distance);
			// populate outer HashMap
			elements1.put(name, elements2);
		}
	}
}
