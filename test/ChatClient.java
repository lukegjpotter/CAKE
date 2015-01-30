//Written by: Charles Greene and Terry Lyons

package test;

import demo.*;
import messages.*;

import java.net.*;
import java.io.*;

import javax.swing.JFrame;

public class ChatClient implements Runnable
{ 
	 public static ObjectOutputStream streamOut = null;
	private Socket socket = null;
   private Thread thread = null;

   private ChatClientThread client = null;
   public static Auth_GUI GUI; 

   public ChatClient(String serverName, int serverPort)
   { 
	   System.out.println("Establishing connection. Please wait ...");
     
   try
      {  
	   socket = new Socket(serverName, serverPort);
         System.out.println("Connected: " + socket);
         start();
         GUI = new Auth_GUI();
         GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      }
      catch(UnknownHostException uhe)
      {  System.out.println("Host unknown: " + uhe.getMessage()); }
     
      catch(IOException ioe)
      {  System.out.println("Unexpected exception: " + ioe.getMessage());
      	 System.exit(-1);	
      }
   }
   public void run()
   { 
	   while (thread != null){}

     
   }
   public void handle(String[] msg)
   {  
     
      {
         //Call the message handler
    	  Client_MessageHandler handler = new Client_MessageHandler(msg);
      }
   	
   }
   public void start() throws IOException
   {  
      streamOut = new ObjectOutputStream(socket.getOutputStream());
      if (thread == null)
      {  
    	  client = new ChatClientThread(this, socket);
         thread = new Thread(this);                   
         thread.start();
      }
   }
   public void stop()
   {  if (thread != null)
      {  
	   	 thread.stop();  
         thread = null;
      }
      try
      { 
         if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {  System.out.println("Error closing ..."); }
      client.close();  
      client.stop();
   }
   
   public static void main(String args[])
   {  
	   ChatClient client = null;
         client = new ChatClient("localhost", 2253);
   }
}
