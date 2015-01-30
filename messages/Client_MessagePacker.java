package messages;

import java.io.IOException;

import test.*;

public class Client_MessagePacker {
	
	public static void packageAuthMessage(String username,String password)
	{
		String[] output = new String[3];
		output[0]="Auth01";
		output[1]=username;
		output[2]=password;
		
		//Pass output to server
		outputPackedMessage(output);
		
	}
	
	public static void packageJoinChat(String chatID)
	{
		String[] output = new String[2];
		output[0]="JoinChat01";
		output[1]=chatID;
		
		outputPackedMessage(output);
	}
	
	public static void packageLeaveChat(String chatID)
	{
		String[] output = new String[2];
		output[0]="LeaveChat01";
		output[1]=chatID;
		
		outputPackedMessage(output);
	}
	
	
	public static void packageChat(String chatID, String message)
	{
		String[] output = new String[3];
		output[0]="Chat01";
		output[1]=chatID;
		output[2]=message;
		
		//Pass output to server
		outputPackedMessage(output);
	}
	
	public static void packageRetrievePosts(String chatID)
	{
		String[] output = new String[2];
		output[0]="RetrievePosts01";
		output[1]=chatID;
		
		//Pass output to server
		outputPackedMessage(output);
	}
	
	public static void packageCreateChat(String chatID, String chatDesc, String genreName)
	{
		String[] output = new String[4];
		output[0]="CreateChat01";
		output[1]=chatID;
		output[2]=chatDesc;
		output[3]=genreName;
		
		outputPackedMessage(output);
	}
	
	public static void packageCreateGenreChat(String chatID, String chatDesc, String genreName, String genreDesc)
	{
		String[] output = new String[5];
		output[0]="CreateGenreChat01";
		output[1]=chatID;
		output[2]=chatDesc;
		output[3]=genreName;
		output[4]=genreDesc;
		
		outputPackedMessage(output);
	}
	
	public static void outputPackedMessage(String[] string)
	{
		try 
		{
			ChatClient.streamOut.writeObject(string);
			ChatClient.streamOut.flush();
		}
		catch (IOException e) 
		{
			System.exit(-1);
		}
		
	}

}
