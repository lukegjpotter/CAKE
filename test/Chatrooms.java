//Written by: Terry Lyons

package test;

import java.util.ArrayList;

public class Chatrooms {
	
	public ArrayList<ChatroomSubs> chatrooms = new ArrayList<ChatroomSubs>();
	
	
	
	public boolean createChatroom(String ChatID)
	{
		boolean alreadyExists=false; 
		for(int i=0;i<chatrooms.size();i++)
		{
			if(chatrooms.get(i).ChatroomID.equals(ChatID))
				alreadyExists=true;
		}
		
		if(!alreadyExists)
		{
			chatrooms.add(new ChatroomSubs(ChatID));
			return true;
		}
		else 
			return false;
	}
	
	public ChatroomSubs findChatroom(String ChatID)
	{
		for(int i=0;i<chatrooms.size();i++)
			if(chatrooms.get(i).ChatroomID.equals(ChatID))
				return chatrooms.get(i);		
		return null;
	}
	
	public String[] removeSubFromAllChats(ChatServerThread sub)
	{
		String[] names;
		names= new String[10];
		for(int i=0;i<chatrooms.size();i++)
		{
			if(chatrooms.get(i).subscribers.contains(sub))
			{
				names[i]=chatrooms.get(i).ChatroomID;
				chatrooms.get(i).removeSub(sub);
			}
		}
		return names;
	}
	
	public void removeSubFromChat(String ChatID, ChatServerThread sub)
	{
		for(int i=0;i<chatrooms.size();i++)
		{
			if(chatrooms.get(i).ChatroomID.equals(ChatID))
			{
				chatrooms.get(i).removeSub(sub);
			}
		}
	}
	
	//Need to add method to remove chatroom
	
	
}
