//Written by: Charles Greene and Terry Lyons


package test;

import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread
{  private Socket           socket   = null;
   private ChatClient       client   = null;
   private ObjectInputStream  streamIn = null;
   private String[] inputMessage;

   public ChatClientThread(ChatClient _client, Socket _socket)
   {  client   = _client;
      socket   = _socket;
      open();  
      start();
   }
   public void open()
   {  try
      {  streamIn  = new ObjectInputStream(socket.getInputStream());
    
     
      }
      catch(IOException ioe)
      {  System.out.println("Error getting input stream: " + ioe);
         client.stop();
      }
   }
   public void close()
   {  try
      {  if (streamIn != null) streamIn.close();
      }
      catch(IOException ioe)
      {  System.out.println("Error closing input stream: " + ioe);
      }
   }
   public void run()
   {  while (true)
      {  try
         {  
    	  inputMessage = (String[])streamIn.readObject();
    	  client.handle(inputMessage);
         }
         catch(IOException ioe)
         {  System.out.println("Listening error: " + ioe.getMessage());
         	System.exit(0);
            client.stop();
         }
         catch(ClassNotFoundException e)
         {
        	 System.exit(-1);
         }
      }
   }
}
