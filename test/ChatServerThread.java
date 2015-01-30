//Written by: Charles Greene and Terry Lyons

package test;
import java.net.*;
import java.io.*;

import messages.Server_MessageHandler;

public class ChatServerThread extends Thread
{  private ChatServer       server    = null;
   private Socket           socket    = null;
   public int              ID        = -1;
   public  String username= ""; 
   public ObjectInputStream  streamIn  =  null;
   public  ObjectOutputStream streamOut = null;
   private String[] input;
   private Server_MessageHandler handler = new Server_MessageHandler();

//-------------------------------------------------------------------------------------------------    
   public ChatServerThread(ChatServer _server, Socket _socket)
   {  super();
      server = _server;
      socket = _socket;
      ID     = socket.getPort();
      //Constructor for the thread passes its handler a reference to itself
      handler.setParent(this);
   }
   
//-------------------------------------------------------------------------------------------------    
   public void send(String[] msg)
   {   try
       {  
	   	  streamOut.writeObject(msg);
          streamOut.flush();
       }
       catch(IOException ioe)
       {  
    	   System.out.println(ID + " ERROR sending: " + ioe.getMessage());
          server.remove(ID);
          stop();
       }
   }
   
//-------------------------------------------------------------------------------------------------   
   public int getID()
   {  return ID;
   }
   
//-------------------------------------------------------------------------------------------------    
   public void run()
   {  System.out.println("Server Thread " + ID + " running.");
   		
      while (true)
      {  try
         {
    	  //Cast the object from the input stream as a String array
				input=(String[])streamIn.readObject();
				
				if(input[0].equals("Auth01"))
					username=input[1];

				//Pass the String array into the message handler
				handler.handleMessage(input);
         }
         catch(IOException ioe)
         {  
        	 System.out.println(ID + " ERROR reading: " + ioe.getMessage());
            server.remove(ID);
            stop();
         }
         
         catch (ClassNotFoundException e) 
         {
        	  System.out.println(ID + "ClassNotFoundException"+ e.getMessage());
              server.remove(ID);
              stop();
 		}
      }
   }
   
//-------------------------------------------------------------------------------------------------    
   public void open() throws IOException
   { 
	   streamIn = new ObjectInputStream(socket.getInputStream());
      streamOut = new ObjectOutputStream(socket.getOutputStream());
   }
   
//-------------------------------------------------------------------------------------------------    
   public void close() throws IOException
   {  if (socket != null)    socket.close();
      if (streamIn != null)  streamIn.close();
      if (streamOut != null) streamOut.close();
   }

//-------------------------------------------------------------------------------------------------    
   public void logout()
   {
	   handler.logout();
   }

}

