package wpgma;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

/**
 * Class holding main visual components of the program
 * @author Serena Pascual and Erin Yang
 */

public class MainView {
	private DataModel model;
	private JPanel panel;
	private JPanel table;
	private ArrayList<JTextField> cells;
	private ArrayList<JTextField> labels;
	private ArrayList<JTextField> labelsLeft;
	private ArrayList<JTextField> labelsTop;

	public MainView(final DataModel model) {
		// initialize instance variables
		this.model = model;
		model.setView(this);
		this.panel = new JPanel(new BorderLayout());
		JFrame frame = new JFrame("WPGMA Calculator");
		Controller controller = new Controller(model);
		cells = new ArrayList<JTextField>();
		labels = new ArrayList<JTextField>();
		labelsLeft = new ArrayList<JTextField>();
		labelsTop = new ArrayList<JTextField>();
		
		panel.setBackground(Color.WHITE);
		frame.add(panel);
		frame.add(controller, BorderLayout.SOUTH);
		frame.setSize(450, 450);
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Generates table based on user input for number of elements
	 */
	public void newTable() {
		// refreshes panel
		panel.removeAll();
		
		// add upper panel with element names
		JPanel labelsTopPanel = new JPanel();
		labelsTopPanel.setLayout(new BoxLayout(labelsTopPanel, BoxLayout.LINE_AXIS));
		labelsTopPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 0, 40));
		for (int i = 1; i < model.getSize() + 1; i++) {
			JTextField label = new JTextField("" + i);
			// disable text field edit
			label.setEnabled(false);
			labelsTopPanel.add(label);
			labelsTop.add(label);
		}
		
		// add left panel with element names
		JPanel labelsLeftPanel = new JPanel();
		labelsLeftPanel.setLayout(new BoxLayout(labelsLeftPanel, BoxLayout.PAGE_AXIS));
		labelsLeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 0));
		for (int i = 1; i < model.getSize() + 1; i++) {
			JTextField label = new JTextField("" + i, 8);
			label.setFocusable(true);
			label.requestFocusInWindow();
			labelsLeftPanel.add(label);
			labels.add(label);
			labelsLeft.add(label);
		}
		
		// add key listeners to left panel element names
		for(final JTextField l : labelsLeft) {
			l.addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent e) {}

				public void keyPressed(KeyEvent e) {}

				// set upper panel element names to match left whenever the text field is changed
				public void keyReleased(KeyEvent e) {
					labelsTop.get(labelsLeft.indexOf(l)).setText(l.getText());
				}
			});
		}
		
		// create panel and add n x n grid
		table = new JPanel();
		table.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 40));
		table.setLayout(new GridLayout(model.getSize(), model.getSize()));
		// iterate through each element
		for (int i = 1; i < model.getSize() + 1; i++) {
			for (int j = 1; j < model.getSize() + 1; j++) {
				// initialize text to empty string and diagonals to 0
				String distance = "";
				if (model.getDistances().size() == 0) {
					if (i == j) distance += 0;
				}
				// otherwise set text to the ith jth value
				else {
					distance += model.getDistances().get("" + i).get("" + j);
				}
				// create the text field to a width of five columns with the above text
				final JTextField cell = new JTextField(distance, 5);
				cell.setFocusable(true);
				cell.requestFocusInWindow();
				// add the textfield to the panel
				table.add(cell);
				// add the element to the ArrayList
				cells.add(cell);
				// disable upper triangular elements
				if (j >= i) {
					cell.setEnabled(false);
				}
			}
		}
		
		// iterate through lower triangular elements to add key listeners
		for (int i = 0; i < model.getSize() - 1; i++) {
			for (int j = model.getSize() * (i + 1); j < model.getSize() * (i + 1) + i + 1; j++) {
				listenerSetText(i, j);
			}
		}
		
		panel.add(labelsTopPanel, BorderLayout.NORTH);
		panel.add(labelsLeftPanel, BorderLayout.WEST);
		panel.add(table, BorderLayout.CENTER);
		panel.validate();
		panel.repaint();
	}
	
	/**
	 * Helper method new for use in newTable() method to add key listeners to matrix elements
	 * @param i outer loop variable
	 * @param j inner loop variable
	 */
	private void listenerSetText(final int i, final int j) {
		cells.get(j).addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {}

			// has ith jth cell listen to jth ith cell
			public void keyReleased(KeyEvent e) {
				cells.get(j / model.getSize() + (j % model.getSize()) * model.getSize()).setText(cells.get(j).getText());
			}
		});
	}
	
	/**
	 * Returns ArrayList of all matrix elements
	 * @return ArrayList of all matrix elements
	 */
	public ArrayList<JTextField> getCells() {
		return cells;
	}

	/**
	 * Returns ArrayList of element names
	 * @return ArrayList of element names
	 */
	public ArrayList<JTextField> getLabels() {
		return labels;
	}
}
