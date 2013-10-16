package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class Login extends JPanel{
	private JTextField name;
	private JRadioButton male;
	private JRadioButton female;
	private JTextField email;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JButton submit;
	private ButtonGroup gender;
	private JLabel jLabel1;
	private boolean complete = false;
	private String mf;

	public Login() {
		try {
			{
				this.setPreferredSize(new java.awt.Dimension(617, 270));
				this.setLayout(null);
				{
					name = new JTextField();
					this.add(name);
					name.setBounds(140, 69, 145, 23);
				}
				{
					jLabel1 = new JLabel();
					this.add(jLabel1);
					jLabel1.setText("Alias*:");
					jLabel1.setBounds(88, 72, 38, 18);
				}
				{
					male = new JRadioButton("", true);
					this.add(male);
					male.setText("Male");
					
					male.setBounds(418, 70, 55, 23);
					getGender().add(male);
				}
				{
					female = new JRadioButton();
					this.add(female);
					female.setText("Female");
					female.setBounds(418, 100, 82, 18);
					getGender().add(female);
				}
				{
					email = new JTextField();
					this.add(email);
					email.setBounds(140, 118, 145, 23);
				}
				{
					jLabel2 = new JLabel();
					this.add(jLabel2);
					jLabel2.setText("E-Mail:");
					jLabel2.setBounds(77, 121, 45, 17);
				}
				{
					submit = new JButton();
					this.add(submit);
					submit.setText("submit");
					submit.setBounds(285, 175, 87, 56);
				}
				{
					jLabel3 = new JLabel();
					this.add(jLabel3);
					jLabel3.setText("Gender*:");
					jLabel3.setBounds(418, 49, 54, 15);
				}
				{
					jLabel4 = new JLabel();
					this.add(jLabel4);
					jLabel4.setText("* These options are necessary for game play");
					jLabel4.setBounds(77, 242, 400, 16);
				}
				{
					jLabel5 = new JLabel();
					this.add(jLabel5);
					jLabel5.setText("Login");
					jLabel5.setBounds(285, 12, 86, 40);
					jLabel5.setFont(new java.awt.Font("Segoe UI",1,26));
				}
			}
			
			submit.addActionListener(new submitListener());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Calls function to create new game
	 */
	private class submitListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(name.getText().length() < 2){
				JOptionPane.showMessageDialog(null, "Your alias has to be more then 3 characters!");
			} else{
				complete = true;
			}
		}
	}
	public boolean status(){
		return complete;
	}
	private ButtonGroup getGender() {
		if(gender == null) {
			gender = new ButtonGroup();
		}
		return gender;
	}

}
