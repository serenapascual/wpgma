package wpgma;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

/**
 * @author Serena Pascual and Erin Yang
 * Class holding main visual components of the program
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
		frame.setSize(400, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void newTable() {
		panel.removeAll();
		JPanel labelsTopPanel = new JPanel();
		labelsTopPanel.setLayout(new BoxLayout(labelsTopPanel, BoxLayout.LINE_AXIS));
		labelsTopPanel.setBorder(BorderFactory.createEmptyBorder(40, 115, 0, 40));
		for (int i = 1; i < model.getSize() + 1; i++) {
			JTextField label = new JTextField("" + i);
			label.setEnabled(false);
			labelsTopPanel.add(label);
			labelsTop.add(label);
		}
		
		JPanel labelsLeftPanel = new JPanel();
		labelsLeftPanel.setLayout(new BoxLayout(labelsLeftPanel, BoxLayout.PAGE_AXIS));
		labelsLeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 0));
		for (int i = 1; i < model.getSize() + 1; i++) {
			JTextField label = new JTextField("" + i, 5);
			label.setFocusable(true);
			label.requestFocusInWindow();
			labelsLeftPanel.add(label);
			labels.add(label);
			labelsLeft.add(label);
		}
		
		for(final JTextField l : labelsLeft) {
			l.addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}

				public void keyReleased(KeyEvent e) {
					labelsTop.get(labelsLeft.indexOf(l)).setText(l.getText());
				}
			});
		}
		
		table = new JPanel();
		table.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 40));
		table.setLayout(new GridLayout(model.getSize(), model.getSize()));
		for (int i = 1; i < model.getSize() + 1; i++) {
			for (int j = 1; j < model.getSize() + 1; j++) {
				String distance = "";
				if (model.getDistances().size() == 0) {
					if (i == j) distance += 0;
				}
				else {
					distance += model.getDistances().get("" + i).get("" + j);
				}
				final JTextField cell = new JTextField(distance);
				cell.setFocusable(true);
				cell.requestFocusInWindow();
				table.add(cell);
				cells.add(cell);
				if (j >= i) {
					cell.setEnabled(false);
				}
			}
		}
		
		for (int i = 0; i < model.getSize() - 1; i++) {
			for (int j = 5 * (i + 1); j < 5 * (i + 1) + i + 1; j++) {
				listenerSetText(i, j);
				System.out.println("i: " + i + ", j: " + j);
			}
		}
		
		panel.add(labelsTopPanel, BorderLayout.NORTH);
		panel.add(labelsLeftPanel, BorderLayout.WEST);
		panel.add(table, BorderLayout.CENTER);
		panel.validate();
		panel.repaint();
	}
	
	private void listenerSetText(final int i, final int j) {
		cells.get(j).addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				cells.get(j / model.getSize() + (j % model.getSize()) * model.getSize()).setText(cells.get(j).getText());
			}
		});
	}
	
	public ArrayList<JTextField> getCells() {
		return cells;
	}

	public ArrayList<JTextField> getLabels() {
		return labels;
	}
	
	public void computeAverages() {
		
	}
}
