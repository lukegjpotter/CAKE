//Written by: Terry Lyons

package demo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;

public class Chat_GUI{
	
	public JPanel chatPanel;
	private GridBagConstraints constraints;
	private GridBagLayout gridBag;
	public static JPanel OnlineStatusPanel;
	Chat_ChatroomsList chatrooms;
	Chat_tabbedPane tabs;
	
	public  Chat_GUI()
	{

		chatPanel = new JPanel();

		gridBag=new GridBagLayout();
		chatPanel.setLayout(gridBag);
		constraints=new GridBagConstraints();
				
	
		tabs = new Chat_tabbedPane();
		chatrooms = new Chat_ChatroomsList ();
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weightx=10;
		constraints.weighty=10;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets = new Insets(10,0,0,0);
		gridBag.setConstraints(chatrooms.Chatrooms, constraints);
		chatPanel.add(chatrooms.Chatrooms);

		
		constraints.gridx=2;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weightx=100;
		constraints.weighty=100;
		constraints.anchor=GridBagConstraints.CENTER;
		gridBag.setConstraints(tabs.Tabs, constraints);
		chatPanel.add(tabs.Tabs);
		
	}
}
