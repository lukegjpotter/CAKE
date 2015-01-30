//Written by: James Peyton

package demo;



import java.awt.*;
import javax.swing.*;

import messages.Client_MessagePacker;

import java.awt.event.*;
import java.util.ArrayList;


public class Chat_CreateNewChat extends JFrame implements ActionListener

{
	public static ArrayList<String> genres=new ArrayList<String>();
	public JComboBox list;
	public  JButton cancel, ok;
	public JTextField ChatroomID,ChatroomDesc,GenreID,GenreDesc;
	public String chatName, chatDesc,currentGenre, genreName, genreDesc;
  
	public Chat_CreateNewChat() {

	  super("Create a new Chatroom");
	  setIconImage(new ImageIcon("Icon.png").getImage());
	  JLabel l;

    Container container = getContentPane();

    container.setLayout(new GridBagLayout());

    container.setBackground(UIManager.getColor("control"));

    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;

    c.gridy = GridBagConstraints.RELATIVE;

    c.gridwidth = 1;

    c.gridheight = 1;

    c.insets = new Insets(2, 2, 2, 2);

    c.anchor = GridBagConstraints.EAST;



    container.add(l = new JLabel("Name of Chatroom:", SwingConstants.RIGHT), c);

    container.add(l = new JLabel("Description of New Chatroom:",SwingConstants.RIGHT), c);

    container.add(l = new JLabel("Chatroom Genre:",SwingConstants.RIGHT), c);
    
    container.add(l = new JLabel("Genre Name:",SwingConstants.RIGHT), c);
    
    container.add(l = new JLabel("Genre Description:",SwingConstants.RIGHT), c);

    container.add(ok = new JButton("OK"), c);
    ok.addActionListener(this);

    c.gridx = 1;

    c.gridy = 0;

    c.weightx = 1.0;

    c.fill = GridBagConstraints.HORIZONTAL;

    c.anchor = GridBagConstraints.CENTER;

    container.add(ChatroomID = new JTextField(20), c);

    c.gridx = 1;

    c.gridy = GridBagConstraints.RELATIVE;

    container.add(ChatroomDesc = new JTextField(20), c);
    
  
    
    c.weightx = 0.0;

    c.fill = GridBagConstraints.NONE;
    
    //Set up Combo Box
    String[] genreArray =(String[])genres.toArray(new String [genres.size ()+1]);
    genreArray[genreArray.length-1]="new genre...";
	list = new JComboBox(genreArray);
	list.setSelectedIndex(0);
	currentGenre=genreArray[0];
	list.addItemListener(
    	      
		//Sets speed	
		new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
  	          String arg = (String) e.getItem();
  	          
  	          	if(arg.equals("new genre..."))
  	          	{
  	          	  currentGenre="";
  	          	  GenreID.setEnabled(true);
  	          	  GenreDesc.setEnabled(true);
  	          		//Create new genre
  	          	}
  	          
  	          	else
    	        {
    	        	currentGenre=arg;
    	        	GenreID.setText("");
    	        	GenreDesc.setText("");
    	        	GenreID.setEnabled(false);
    	        	GenreDesc.setEnabled(false);
    	        }
    	        	
    	        }
    	      }
    	    );
	
	container.add(list,c);
	
	  container.add(GenreID = new JTextField(20), c);
	    container.add(GenreDesc = new JTextField(20), c);
	    GenreID.setEnabled(false);
	    GenreDesc.setEnabled(false);
    
    container.add(cancel = new JButton("Cancel"), c);
    cancel.addActionListener(this);
    
    pack();
    setMinimumSize(new Dimension(300,200));
    setLocationRelativeTo( null );
    setResizable(false);
    setVisible(true);

  }
  
  public void actionPerformed(ActionEvent evt)
	 {
		 if(evt.getSource()==ok)
		 {
			if(!ChatroomID.getText().equals("")&&!currentGenre.equals(""))
			{
			chatName=ChatroomID.getText();
			chatDesc=ChatroomDesc.getText();
			genreName=currentGenre;
			//Call create new chat
			Client_MessagePacker.packageCreateChat(chatName,chatDesc,genreName);
			dispose();
			}
		
			else if(!ChatroomID.getText().equals("")&&!GenreID.getText().equals(""))
			{
				chatName=ChatroomID.getText();
				chatDesc=ChatroomDesc.getText();
				genreName=GenreID.getText();
				genreDesc=GenreDesc.getText();
				//call create new genre and chat
				Client_MessagePacker.packageCreateGenreChat(chatName,chatDesc,genreName,genreDesc);
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Error with input data",null,JOptionPane.ERROR_MESSAGE);
			}
		 }
		 
		 else if(evt.getSource()==cancel)
		 {
			 dispose();
		 }
	 }
}

