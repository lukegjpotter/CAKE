//Written by: Terry Lyons

package test;

import java.util.ArrayList;

public class ChatroomSubs {
	
	public String ChatroomID;
	public ArrayList<ChatServerThread> subscribers = new ArrayList<ChatServerThread>();
	
	public ChatroomSubs(String ChatID)
	{
		ChatroomID=ChatID;
	}
	
	public void addSub(ChatServerThread sub)
	{ 
		subscribers.add(sub);
	}
	
	public void removeSub(ChatServerThread sub)
	{
		int pos=getSub(sub);
		  
		if (pos >= 0)
		{
			subscribers.remove(pos);
	
		}
		
	}
	
	private int getSub(ChatServerThread sub)
	{
		for(int i=0;i<subscribers.size();i++)
			if(subscribers.get(i).username.equals(sub.username))
				return i;
		return -1;
	
		
	}
	

}
