package wpgma;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Controller extends JPanel {
	public Controller(final DataModel model) {
		JPanel matrix = new JPanel();
		JPanel toolbar = new JPanel();
		
		JLabel inputSizeLabel = new JLabel("Input size:");
		final JTextField inputSizeField = new JTextField(2);
		JButton tableButton = new JButton("New table");
		JButton treeButton = new JButton("Generate tree");
		
		tableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inputSizeField.getText().length() > 0) {
					model.setSize(Integer.parseInt(inputSizeField.getText()));
				}
			}
		});
		
		treeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		toolbar.add(inputSizeLabel);
		toolbar.add(inputSizeField);
		toolbar.add(tableButton);
		toolbar.add(treeButton);
		
		setLayout(new BorderLayout());
		add(matrix);
		add(toolbar, BorderLayout.SOUTH);
	}
}
