package messages;

import java.io.IOException;
import java.util.ArrayList;

import test.*;

public class Server_MessagePacker {

	private ChatServerThread grandparent = null;
	
	public void packageAuthMessage(String s)
	{
		String[] output = new String[2];
		output[0]="Auth02";
		output[1]=s;
		
		//Pass output to server
		grandparent.send(output);
	}
	
	public void packageChat(String chatID, String message)
	{
		String[] output = new String[3];
		output[0]="Chat02";
		output[1]=chatID;
		output[2]= Newline(message,70);
		
		//Pass output to server
		{
		//This code will get the online users in the specified chatroom and send the message to them.
			
		ArrayList<ChatServerThread> sendTo = new ArrayList<ChatServerThread>();
		sendTo =ChatServer.chatrooms.findChatroom(chatID).subscribers;
	
		for (int i=0;i<sendTo.size();i++)
		{
        	sendTo.get(i).send(output);
		}
		
		}

	}
	
	public void packageArchivedPosts(String[] output)
	{
		grandparent.send(output);
	}
	
	public void packageSingleChatList(String[] output)
	{
		grandparent.send(output);
	}
	
	public void packageChatList(String[] output)
	{
		for(int i=0;i<ChatServer.clients.size();i++)
		{
			 System.out.println("Sending chat list\n");
			ChatServer.clients.get(i).send(output);
		}
	}
	
	public void packageOnlineStatus(String chatID)
	{
		if(ChatServer.chatrooms.findChatroom(chatID).subscribers.size()!=0)	
		{
		String[] output = new String[ChatServer.chatrooms.findChatroom(chatID).subscribers.size()+2];
		output[0]="OnlineStatus02";
		output[1]=chatID;

		  for (int i = 2; i < ChatServer.chatrooms.findChatroom(chatID).subscribers.size()+2; i++)
	         {
			  output[i]=ChatServer.chatrooms.findChatroom(chatID).subscribers.get(i-2).username;
			  System.out.println(chatID+"\n"+output[i]);
	         }
		  
			ArrayList<ChatServerThread> sendTo = new ArrayList<ChatServerThread>();
			sendTo =ChatServer.chatrooms.findChatroom(chatID).subscribers;
			
		if(sendTo.size()!=0)	
			for (int i=0;i<sendTo.size();i++)
			{
	        	sendTo.get(i).send(output);
			}
		}
		  
	}
	
	public void setGrandParent(ChatServerThread thread)
	{
		grandparent = thread; 
		System.out.println("\nGrand Parent is: "+grandparent.ID);
	}
	
	
	public String Newline (String mess,int period)
	{	   
			String s;
	        StringBuffer buf = new StringBuffer();
	        int difference =0;
	        int x=0;
	        if(mess.length()>period)
	        {
	        for (int i =0;i<mess.length();i+=period) {      
	            //String is divided with length of period
	            buf.append(mess.substring(i,i+period));
	           //will put the new line
	            buf.append("\n");   
	            x++;  
	            //keeps track of the remaining characters
	            difference = ( mess.length()-( buf.length()-x) );
	         
	            //indicates we are at the last line
	            if(difference <period)
	            {
	                //The remaining line will be put into the  buffer
	                i+=period;
	                buf.append(mess.substring(i,i+difference));
	                buf.append("\n");   
	                x++;

	              
	            }
	        }
	        s=buf.toString();
	        }
	        else	
	        s=mess+"\n";
	   
	        return s;
	   

	    }   

	
}
