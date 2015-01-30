//Written by: Martin Farrell and Terry Lyons
package demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import messages.*;

public class Chat_CreateTab extends JPanel implements ActionListener {
	
	private GridBagConstraints constraints;
	private GridBagLayout gridBag;
	public JTextArea output = new JTextArea(30,52);
	public JTextArea Online = new JTextArea();
	public JTextField input = new JTextField();
	public String ChatID="";
	private JPanel panel = new JPanel();
	
		public Chat_CreateTab(String ID)
		{
			ChatID=ID;
			gridBag=new GridBagLayout();
			setLayout(gridBag);
			
			constraints=new GridBagConstraints();
		
			panel.setLayout(gridBag);
			
			output.setEditable(false);
			Online.setEditable(false);
					
			JPanel OnlinePanel=new JPanel();
			JLabel inChatroom = new JLabel("In This Chatroom:");
			JScrollPane scrollPane = new JScrollPane(output);
			JScrollPane OnlineStatus = new JScrollPane(Online); 
			
			OnlinePanel.setMinimumSize(new Dimension(225,540));
			OnlinePanel.setPreferredSize(new Dimension(225,540));
			OnlinePanel.setMaximumSize(new Dimension(225,540));
			
			OnlineStatus.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			OnlineStatus.setMinimumSize(new Dimension(225,513));
			OnlineStatus.setPreferredSize(new Dimension(225,513));
			OnlineStatus.setMaximumSize(new Dimension(225,513));
			OnlineStatus.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			
	       
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	        scrollPane.setMinimumSize(new Dimension(500,510));
	        scrollPane.setPreferredSize(new Dimension(500,510));
	        scrollPane.setMaximumSize(new Dimension(500,510));
	        scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
	        
			input.setMinimumSize(new Dimension(500,25));
			input.setPreferredSize(new Dimension(500,25));
			input.setMaximumSize(new Dimension(500,25));
			input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			
			input.addActionListener(this);
			
			OnlinePanel.add(inChatroom,  BorderLayout.PAGE_START);
			OnlinePanel.add(OnlineStatus,BorderLayout.CENTER);
			
			constraints.gridx=1;
			constraints.gridy=1;
			constraints.gridwidth=1;
			constraints.gridheight=1;
			constraints.weightx=10;
			constraints.weighty=10;
			gridBag.setConstraints(scrollPane, constraints);	
			panel.add(scrollPane); 
			
			constraints.gridx=1;
			constraints.gridy=2;
			constraints.gridwidth=1;
			constraints.gridheight=1;
			constraints.weightx=10;
			constraints.weighty=10;
			constraints.insets = new Insets(5,0,0,0);
			gridBag.setConstraints(input, constraints);	
			panel.add(input);
			
			constraints.gridx=1;
			constraints.gridy=1;
			constraints.gridwidth=1;
			constraints.gridheight=1;
			constraints.weightx=10;
			constraints.weighty=10;
			constraints.anchor=GridBagConstraints.FIRST_LINE_START;
			constraints.insets = new Insets(5,5,0,0);
			gridBag.setConstraints(panel, constraints);	
			add(panel);
			
			constraints.gridx=2;
			constraints.gridy=1;
			constraints.gridwidth=1;
			constraints.gridheight=1;
			constraints.weightx=1;
			constraints.weighty=1;
			constraints.anchor=GridBagConstraints.FIRST_LINE_END;
			constraints.insets = new Insets(5,5,0,5);
			gridBag.setConstraints(OnlinePanel, constraints);	
			add(OnlinePanel);
		
		}
		
		 public void actionPerformed(ActionEvent evt) {
		        String text = input.getText();
		        if(!text.isEmpty())
		        {
		        	Client_MessagePacker.packageChat(ChatID, text);
		        	input.setText("");
		        }
		 }
}
