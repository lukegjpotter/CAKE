package messages;

import database.*;
import test.*;

public class Server_MessageHandler {

	private String Username="";
	private ChatServerThread parent = null;
	
	private Server_MessagePacker packer = new Server_MessagePacker();
	private Connection_Manager Connect = new Connection_Manager();
	
	public void handleMessage(String[] input)
	{
		if(input[0].equals("Auth01"))
		{
			//Call Connection Manager, Check username, password
			//Position 1 is username, position 2 is password
			if(Connect.Auth(input[1],input[2]))
			{
				packer.packageAuthMessage("true");
				Username=input[1];	
				packer.packageSingleChatList(Connect.GenreChatList());
			}
			else
			{
				packer.packageAuthMessage("false");
			}
		}
		
		
		//Position 0 is message type, for chatrooms position 1 is chatroom ID
		else if(input[0].equals("Chat01"))
		{
	
					Connect.AddChatPost(input[1], Username, input[2]);
					packer.packageChat(input[1],Username+": "+input[2]);
		
		}
		
		else if(input[0].equals("JoinChat01"))
		{
			//Adds the user to the list of subscribers
			if(ChatServer.chatrooms.findChatroom(input[1])==null)
			{
				ChatServer.chatrooms.createChatroom(input[1]);
			}
			ChatServer.chatrooms.findChatroom(input[1]).addSub(parent);
			packer.packageOnlineStatus(input[1]);
			
			//Retrieve last x posts from database and send to client
		}
		
		else if(input[0].equals("LeaveChat01"))
		{
			ChatServer.chatrooms.removeSubFromChat(input[1], parent);
			packer.packageOnlineStatus(input[1]);
		}
		
		else if(input[0].equals("RetrievePosts01"))
		{
			packer.packageArchivedPosts(Connect.ShowNposts(30, input[1]));
		}
		
		else if(input[0].equals("CreateChat01"))
		{
			if(Connect.AddNewChat(input[3], Username, input[1], input[2]))
			{
				packer.packageChatList(Connect.GenreChatList());
				
			}
		}
		
		else if(input[0].equals("CreateGenreChat01"))
		{
			if(Connect.AddGenre(input[3], input[4]))
			{
				if(Connect.AddNewChat(input[3], Username, input[1], input[2]))
				{
					packer.packageChatList(Connect.GenreChatList());
				}
				
			}
		}
		
		
	}
	

	public void logout()
	{
	//	Connect.setStatusOffline(Username);
		String[] names = ChatServer.chatrooms.removeSubFromAllChats(parent);
		for(int i=0;i<names.length;i++)
		{
			if(names[i]!=null)
			packer.packageOnlineStatus(names[i]);
		}
			
		
	}
	
	public void setParent(ChatServerThread thread)
	{
		parent = thread; 
		packer.setGrandParent(thread);
	}
	
	public ChatServerThread getParent()
	{
		return parent;
	}
	
	
}
