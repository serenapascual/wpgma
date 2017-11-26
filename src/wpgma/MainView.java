package wpgma;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

/**
 * @author Serena Pascual and Erin Yang
 * Class holding main visual components of the program
 */

public class MainView implements ChangeListener {
	private DataModel model;
	private JPanel panel;
	private JPanel table;
	
	public MainView(final DataModel model) {
		this.model = model;
		this.panel = new JPanel(new BorderLayout());
		JFrame frame = new JFrame("WPGMA Tree Builder");
		Controller controller = new Controller(model);
		
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
		JPanel labelsTop = new JPanel();
		labelsTop.setLayout(new BoxLayout(labelsTop, BoxLayout.LINE_AXIS));
		labelsTop.setBorder(BorderFactory.createEmptyBorder(40, 115, 0, 40));
		for (int i = 1; i < model.getSize() + 1; i++) {
			labelsTop.add(new JTextField("" + i)).setEnabled(false);
		}
		
		JPanel labelsLeft = new JPanel();
		labelsLeft.setLayout(new BoxLayout(labelsLeft, BoxLayout.PAGE_AXIS));
		labelsLeft.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 0));
		for (int i = 1; i < model.getSize() + 1; i++) {
			labelsLeft.add(new JTextField("" + i, 5));
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
				JTextField cell = new JTextField(distance);
				table.add(cell);
				if (j >= i) {
					cell.setEnabled(false);
				}
			}
		}
		
		panel.add(labelsTop, BorderLayout.NORTH);
		panel.add(labelsLeft, BorderLayout.WEST);
		panel.add(table, BorderLayout.CENTER);
		panel.validate();
		panel.repaint();
	}
	
	public void generateTree() {
		
	}

	public void stateChanged(ChangeEvent e) {
		newTable();
	}
}
