//Written by: Terry Lyons

package demo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.security.*;
import messages.*;

public class Auth_GUI extends JFrame implements ActionListener {
	
	private Container container;
	private GridBagConstraints constraints;
	private GridBagLayout gridBag;
	public JTextField username;
	public JPasswordField password;
	private JPanel textPanel, buttonPanel;
	private JButton ok,cancel;
	private JLabel user_Label,password_Label;
	public static String name="",passwordString="",OK="Ok";
	private char[] word;

	public Auth_GUI()
	{
		//Sets the title of the window 
		super("Authentication");
		//Set the window icon
		setIconImage(new ImageIcon("Icon.png").getImage());
		
		//Set up the container and layout manager for the window
		container = getContentPane();
		gridBag=new GridBagLayout(); // This is a type of layout manager
		container.setLayout(gridBag);
		constraints=new GridBagConstraints();

		//Create a panel that will hold the text and text boxes
		textPanel = new JPanel();

		
		//Create a panel that will hold the buttons
		buttonPanel = new JPanel();
	
		
		//Set the layout manager of the panels
		textPanel.setLayout(gridBag);
	
		buttonPanel.setLayout(gridBag);
	
		
		//Create the "Username:" label
		user_Label = new JLabel("Username:");
		//Using GridBagConstraints, specify x/y position, width/height then add user_label with these constraints to textPanel
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(user_Label, constraints);
		textPanel.add(user_Label);
		
		//Create a TextField to input the username
		username = new JTextField(10);
		username.setActionCommand(OK);
		username.addActionListener(this);
		
		//Specify constraints of text field and add it to textPanel
		constraints.gridx=1;
		constraints.gridy=2;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(username, constraints);
		textPanel.add(username);	
		
		//Password label
		password_Label = new JLabel("Password:");
		//Label constraints
		constraints.gridx=1;
		constraints.gridy=3;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(password_Label, constraints);
		textPanel.add(password_Label);
		
		//Password field
		password = new JPasswordField(10);
		password.setActionCommand(OK);
		password.addActionListener(this);
		
		//Password field constraints
		constraints.gridx=1;
		constraints.gridy=4;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(password, constraints);
		textPanel.add(password);
		
		//Create an ok button and add an action listener
		ok = new JButton(OK);
		ok.addActionListener(this);
		
		//Ok button constraints
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(ok, constraints);
		buttonPanel.add(ok);
		
		//Cancel button + action listener
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		//Cancel constraints
		constraints.gridx=2;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(cancel, constraints);
		buttonPanel.add(cancel);
		
		//Set text panel constraints and add it to JFrame
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(textPanel, constraints);
		add(textPanel);
		
		//Set button panel constraints and add it to JFrame
		constraints.insets = new Insets(10,0,0,0);
		constraints.gridx=1;
		constraints.gridy=2;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		gridBag.setConstraints(buttonPanel, constraints);	
		add(buttonPanel);
			
		//Set constraints for JFrame
		setMinimumSize(new Dimension(200,150));
		setLocationRelativeTo( null );
		setVisible(true);
		setResizable(false);
	}
	
	
	//Action listener method handles keyboard/ button click input
	 public void actionPerformed(ActionEvent evt)
	 {
		 String cmd = evt.getActionCommand();

		 //Action listener for Ok button gets text in username and password fields when button clicked
		 if (OK.equals(cmd)&&!username.getText().equals(""))
		 {
			 name=username.getText();
			 //Password is read in as a char array, so must be converted to a
			 //String before being encrypted
			 word=password.getPassword();
				for(int i=0;i<word.length;i++)
				{
					
					passwordString=passwordString+word[i];	
				}
					try
					{
						Encryption encrypt= new Encryption();
					passwordString=encrypt.SHA1(passwordString);
					}
					catch(NoSuchAlgorithmException e)
					{
						System.exit(1);
					}
					
					catch(UnsupportedEncodingException e)
					{
						System.exit(1);
					}
					
					//package Authentication message and send it to server
					Client_MessagePacker.packageAuthMessage(name, passwordString);
		 }
		 
		 //Handles the cancel button
		 else if (evt.getSource()==cancel)
		 {
			 System.exit(0);
		 }	 
	 }
 
}
