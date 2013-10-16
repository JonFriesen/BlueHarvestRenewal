package ui;

import javax.swing.JTextArea;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class Help extends javax.swing.JFrame {
	private JTextArea jTextArea1;

	
	public Help() {
		super();
		initGUI();
	}
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(240, 155);
			setTitle("About BlokBuster");
			setIgnoreRepaint(true);
	        setResizable(false);
	        setVisible(true);
	        getContentPane().setLayout(null);
	        {
	        	jTextArea1 = new JTextArea();
	        	jTextArea1.setEditable(false);
	        	getContentPane().add(jTextArea1);
	        	jTextArea1.setText("Really? You need help...\nyou can't do anything yet...\n Tell you what, email me\nand I'll try and answer your question\nEmail:Jon@JonFriesen.ca");
	        	jTextArea1.setBounds(12, 12, 203, 100);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
