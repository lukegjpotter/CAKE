//Written by: Martin Farrell and Terry Lyons

package demo;

import javax.swing.*;
import java.awt.*;

import messages.*;

public class Chat_tabbedPane {
	static JTabbedPane mypane;
	public static int i=0;
	public JPanel Tabs;
	public static Chat_CreateTab tab;
	public static Chat_CreateTab[] tabArray = new Chat_CreateTab[6];
	
	public Chat_tabbedPane()
	{
		Tabs = new JPanel();
		mypane = new JTabbedPane();
		Tabs.add(mypane, BorderLayout.CENTER);
		mypane.setMinimumSize(new Dimension(750,590));
		mypane.setPreferredSize(new Dimension(750,590));
		mypane.setMaximumSize(new Dimension(750,590));
		addtaby("Public Chat");		
		
		Tabs.add(mypane);
		Tabs.setMinimumSize(new Dimension(760,600));
		Tabs.setPreferredSize(new Dimension(760,600));
		Tabs.setMaximumSize(new Dimension(760,600));
		Tabs.setVisible(true);
	}
	
	public static void addtaby(String title)
	{
		if(i==0)
		{
			tab = new Chat_CreateTab("PublicChat");
			mypane.addTab(title, tab);
			JLabel label=new JLabel(title);
			label.setMinimumSize(new Dimension(title.length()+55,30));
			label.setPreferredSize(new Dimension(title.length()+55,30));
			label.setMaximumSize(new Dimension(title.length()+55,30));
			mypane.setTabComponentAt(i, label);
			tabArray[i]=tab;
			Client_MessagePacker.packageJoinChat(tabArray[i].ChatID);
			Client_MessagePacker.packageRetrievePosts("PublicChat");
			i++;
		}
		
		else if (i>5)
		{
			JOptionPane.showMessageDialog(null, "You have too many tabs open");
		}
		
		else
		{
			 boolean exists=false;
			 for(int i=0;i<Chat_tabbedPane.tabArray.length;i++)
			 {
				 if(Chat_tabbedPane.tabArray[i]!=null&&Chat_tabbedPane.tabArray[i].ChatID.equals(title))
						 exists=true;
				 
			 }
			 if(exists!=true)
			 {
			
				 Chat_CreateTab addTab = new Chat_CreateTab(title);
				 mypane.addTab(title, addTab);
				 tabArray[i]=addTab;
			
				 Client_MessagePacker.packageJoinChat(tabArray[i].ChatID);
			
				 Chat_CloseButton button =new Chat_CloseButton(title);

				 mypane.setTabComponentAt(i,button );
				 Client_MessagePacker.packageRetrievePosts(title);
				 i++;
			 }
		}
		
	}
	public static void rmvtaby(int e)
	{
            mypane.remove(e);
            Client_MessagePacker.packageLeaveChat(tabArray[e].ChatID);
            for(int i=e+1;i<tabArray.length;i++)
            	tabArray[i-1]=tabArray[i];
            i--;
        
	}
	
	public static void appendText(String ID,String s)
	{
		for(int i=0;i<tabArray.length;i++)
		{
			if(tabArray[i]!=null&&tabArray[i].ChatID.equals(ID))
			{
				tabArray[i].output.append(s);
				tabArray[i].output.setCaretPosition(tabArray[i].output.getDocument().getLength());
			}
		}
	}
	
	public static void setOnlineStatus(String ID,String[] s)
	{
		for(int i=0;i<tabArray.length;i++)
		{
			if(tabArray[i]!=null&&tabArray[i].ChatID.equals(ID))
			{
				tabArray[i].Online.setText("");
				for(int j=0;j<s.length;j++)
				tabArray[i].Online.append("\n\t"+s[j]+"\n");
			}
		}
	}

	
}

