//Written by: Charles Greene and Terry Lyons

package test;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ChatServer implements Runnable
{  
	public static ArrayList <ChatServerThread> clients = new ArrayList <ChatServerThread>();
	public static Chatrooms chatrooms = new Chatrooms();
   private ServerSocket server = null;
   private Thread       thread = null;
//-------------------------------------------------------------------------------------------------
   public ChatServer(int port)
   {  try
      	{ 
	   		System.out.println("Binding to port " + port + ", please wait  ...");
	   		server = new ServerSocket(port);  
	   		System.out.println("Server started: " + server);
	   		start(); 
         }
   
      catch(IOException ioe)
      { 
    	  System.out.println("Can not bind to port " + port + ": " + ioe.getMessage()); 
      }
   }
 
//-------------------------------------------------------------------------------------------------   
   public void run()
   {  
	   while (thread != null)
	   {  
		   try
		   	{ 
			   System.out.println("Waiting for a client ..."); 
			   addThread(server.accept()); 
            }
		   
         catch(IOException ioe)
         	{  
        	 	System.out.println("Server accept error: " + ioe); stop(); 
        	}
      }
   }
   
//-------------------------------------------------------------------------------------------------  
   public void start()
   {  
	   if (thread == null)
   		{  
		   	thread = new Thread(this); 
   			thread.start();
   		}
   }

//-------------------------------------------------------------------------------------------------    
   public void stop()   
   {  if (thread != null)
   		{  
	   		thread.stop(); 
	   		thread = null;
   		}
   }

//-------------------------------------------------------------------------------------------------    
   private int findClient(int ID)
   {  
	   for (int i = 0; i < clients.size(); i++)
         if (clients.get(i).getID() == ID)
            return i;
      return -1;
   }
   
//------------------------------------------------------------------------------------------------- 
   public synchronized void remove(int ID)
   {  int pos = findClient(ID);
      if (pos >= 0)
      {  
    	  ChatServerThread toTerminate = clients.get(pos);
    	  if(!toTerminate.username.equals(""))
    		  {
    		  	toTerminate.logout();
    		  }
         System.out.println("Removing client thread " + ID + " at " + pos);
        	 clients.remove(pos);
     
         try
         {  toTerminate.close(); }
         catch(IOException ioe)
         {  System.out.println("Error closing thread: " + ioe); }
         toTerminate.stop(); }      
   }
   
//-------------------------------------------------------------------------------------------------   
   private void addThread(Socket socket)
   {  

       
		   System.out.println("Client accepted: " + socket);
		   clients.add(new ChatServerThread(this, socket));
         try
         	{  
        	 clients.get(clients.size()-1).open(); 
        	  clients.get(clients.size()-1).start();  
            }
         catch(IOException ioe)
         	{  
        	 	System.out.println("Error opening thread: " + ioe); 
         	} 
   }

//-------------------------------------------------------------------------------------------------    
   public static void main(String args[]) { 
	  		ChatServer server = null;
	         server = new ChatServer(2253);
   }
}
