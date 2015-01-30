//Written by: James Peyton

package demo;

import java.awt.*;

import javax.swing.*;

public class Chat_ChatroomsDescription 
{
	public JPanel ChatDescription;

	JTextArea MyDescription = new JTextArea(12 , 17);

	    //constructor

	    public Chat_ChatroomsDescription()

	     {
	    	JLabel label = new JLabel("Description:");
	    	
	    	MyDescription.setEditable(false);
	        
	        JScrollPane scrollingArea = new JScrollPane(MyDescription);

	        ChatDescription = new JPanel();

	        ChatDescription.setLayout(new BorderLayout());
	        
	        ChatDescription.add(label, BorderLayout.PAGE_START);
	        ChatDescription.add(scrollingArea, BorderLayout.CENTER);
	    

	    }
	    
	    public void setDescription(String s)
	    {
	    	MyDescription.setText(s);
	    }

	}

	
	

