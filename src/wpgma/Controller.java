package wpgma;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Controller class to modify model as requested by GUI
 * @author Serena Pascual and Erin Yang
 *
 */
public class Controller extends JPanel {
	public Controller(final DataModel model) {
		JPanel matrix = new JPanel();
		JPanel toolbar = new JPanel();
		
		JLabel inputSizeLabel = new JLabel("Input size:");
		final JTextField inputSizeField = new JTextField(2);
		JButton tableButton = new JButton("New table");
		JButton computeButton = new JButton("Compute averages");
		
		// retrieve text from text field requesting number of matrix elements
		tableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inputSizeField.getText().length() > 0) {
					// error handling to ensure valid input
					try {
						model.setSize(Integer.parseInt(inputSizeField.getText()));
					}
					catch (NumberFormatException e1) {
						JFrame frame = new JFrame();
	                	JOptionPane.showMessageDialog(frame,
	                            "Please enter a positive integer value.",
	                            "Invalid input",
	                            JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		computeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					// store matrix inputs into nested HashMap
					model.addElements();
					// perform algorithm
					WPGMAChart chart = new WPGMAChart(model.getDistances());
					chart.solveChart(); // error
			}
		});
		
		toolbar.add(inputSizeLabel);
		toolbar.add(inputSizeField);
		toolbar.add(tableButton);
		toolbar.add(computeButton);
		
		setLayout(new BorderLayout());
		add(matrix);
		add(toolbar, BorderLayout.SOUTH);
	}
}
