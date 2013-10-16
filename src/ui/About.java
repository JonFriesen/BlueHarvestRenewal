package ui;

import javax.swing.JTextArea;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class About extends javax.swing.JFrame {
	private JTextArea jTextArea1;

	
	public About() {
		super();
		initGUI();
	}
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(240, 155);
			setTitle("BlueHarvest");
			setIgnoreRepaint(true);
	        setResizable(false);
	        setVisible(true);
	        getContentPane().setLayout(null);
	        {
	        	jTextArea1 = new JTextArea();
	        	jTextArea1.setEditable(false);
	        	getContentPane().add(jTextArea1);
	        	jTextArea1.setText("Created by Jon Friesen\n\nContact me: Jon@JonFriesen.ca\nwww.JonFriesen.ca");
	        	jTextArea1.setBounds(12, 12, 203, 100);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
