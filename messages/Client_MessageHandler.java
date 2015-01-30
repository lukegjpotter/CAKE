

package messages;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import test.ChatClient;

import demo.*;

public class Client_MessageHandler {
	 App_GUI GUI;
	
	public Client_MessageHandler(String[] input)
	{
		if(input[0].equals("Auth02"))
		{
			if(input[1].equals("true"))
			{
				ChatClient.GUI.dispose();
				new startGUIThread().start();
			}
			
			else if(input[1].equals("false"))
			{
				 JOptionPane.showMessageDialog(null, "Incorrect username or password", null, JOptionPane.ERROR_MESSAGE, null);
				 ChatClient.GUI.name="";
				 ChatClient.GUI.passwordString="";
				 ChatClient.GUI.username.setText(""); 
				 ChatClient.GUI.password.setText("");
			}
		}
		
		else if(input[0].equals("Chat02"))
		{
				Chat_tabbedPane.appendText(input[1],input[2]);
		}
		
		else if(input[0].equals("ShowPosts02"))
		{
			for(int i=2;i<input.length;i++)
			{
			  Chat_tabbedPane.appendText(input[1],input[i]);
			}
		}
		
		else if(input[0].equals("OnlineStatus02"))
		{
			 String[] status = new String[input.length-2];
			 for(int i=0;i<status.length;i++)
				 status[i]=input[i+2];
			Chat_tabbedPane.setOnlineStatus(input[1], status);
		
		}
		
		else if(input[0].equals("ChatroomList02"))
		{	
			//set  input[1] as a level one node for the tree and
			//add input[i+1] as the description for the genre
			//Chat_ChatroomsList.top.removeAllChildren();
			Chat_ChatroomsList.createGenreNode(input[1],input[2]);
			Chat_CreateNewChat.genres.add(input[1]);
			for( int i = 1; i<input.length-1;i+=4)
			{
				if(i>4&&!input[i].equals(input[i-4]))
				{
					//set  input[i] as a level one node for the tree and
					//add input[i+1] as the description for the genre
					System.out.println("\nGenre:"+input[i]);
					Chat_CreateNewChat.genres.add(input[i]);
					Chat_ChatroomsList.createGenreNode(input[i],input[i+1]);
				}
				
				//add input[i+2] as child of input[i] node and
				//add input[i+3] as description of the chat
				System.out.println("\nChat:"+input[i+2]);
				Chat_ChatroomsList.createChatNode(input[i+2], input[i+3]);
			}

		}
	}
	

	 private class startGUIThread extends Thread{
			
		 public void run()
		 {
			 //Need to put this thread to sleep until client has loaded
			  Chat_SplashScreen splash = new Chat_SplashScreen(200);
			  splash.showSplashAndExit();
			 App_GUI GUI = new App_GUI();
	   		    GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 }

    }
}
