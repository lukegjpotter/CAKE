//Written by: Eric Bannon, Charles Greene, Daire Keane, Terry Lyons, and Luke Potter

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

 
public class Connection_Manager {

	// credentials for connecting to database
	private static final String DRIVER = "com.mysql.jdbc.Driver";
//	private static final String URL = "jdbc:mysql://danu.it.nuigalway.ie/mydb266";
//	private static final String USERNAME = "mydb266";
//	private static final String PASSWORD = "mydb266804N";
	
	private static final String URL = "jdbc:mysql://bannon.dyndns.ws/c3po";
	private static final String USERNAME = "c3po";
	private static final String PASSWORD = "zo4Iezah";
	private String[] output;
	Connection connection = null;
	ResultSet resultSet = null;

 
	//Pass username and password into method that checks database, then return true/false
	public boolean Auth(String username,String password)
	{
		boolean result=false;
	
		PreparedStatement selectUserByUsername = null;
 
		// database connectivity
		try
		{
			// load the MySQL driver
			Class.forName (DRIVER);
 
			// make the connection
			connection = DriverManager.getConnection( URL, USERNAME, PASSWORD );
 
			// SQL for getting a single user by username and password
			selectUserByUsername = connection.prepareStatement( "SELECT * FROM users WHERE username = ? AND password = ?" );
		} // end try
 
		catch ( ClassNotFoundException classNotFoundException )
		{
			classNotFoundException.printStackTrace();
		}
 
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
			System.exit( 1 );
		} // end catch
 
		try
		{
			// specify username and password
			selectUserByUsername.setString( 1, username );
			selectUserByUsername.setString( 2, password);

			// execute query
			resultSet = selectUserByUsername.executeQuery();
		
			//If resultSet.next() is true, then username and password are correct
			if(resultSet.next())
			{
				result=true;
			}
			else
				result=false;
			
		}
 
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
			System.exit( 1 );
		} // end catch
 
		//Return true/false for authentication 
		return result;
	}
	
	public void setStatusOnline(String name)
	{
		PreparedStatement setOnline = null;
		try
		{
			setOnline = connection.prepareStatement( "UPDATE users SET online_status='online' WHERE username = ?" );
			setOnline.setString( 1, name );
			setOnline.execute();
		}
		catch (SQLException sqlException )
		{
			sqlException.printStackTrace();
			System.exit( 1 );
		} // end catch
		
	}
	
	public void setStatusOffline(String name)
	{
		PreparedStatement setOffline = null;
		try
		{
			setOffline = connection.prepareStatement( "UPDATE users SET online_status='offline' WHERE username = ?" );
			setOffline.setString( 1, name );
			setOffline.execute();
		}
		catch (SQLException sqlException )
		{
			sqlException.printStackTrace();
			System.exit( 1 );
		} // end catch
		
	}
	
		public String[] getUsers()
		{

			try
			{
			PreparedStatement selectUserByStatus = null;
			selectUserByStatus = connection.prepareStatement( "SELECT * FROM users ORDER BY online_status DESC, lname ASC" );
		
				// execute query
				resultSet = selectUserByStatus.executeQuery();
	 
				// process results
				output = new String[1+(getRowCount()*2)];
				String[] status = new String[getRowCount()];
				String[] user=new String[getRowCount()];
				output[0]="OnlineStatus02";
				
				int i=1;
				while( resultSet.next() )
				{
				
					String name=(String)(resultSet.getObject("fname"))+" "+(resultSet.getObject("lname"));
					output[i]=name;
					output[i+1]=(String)resultSet.getObject("online_status");
					i=i+2;
					
				}
			}
			catch ( SQLException sqlException )
			{
				sqlException.printStackTrace();
				System.exit( 1 );
			} // end catch
			
			
			return output;
			
		}
		
	    public String[] GenreChatList()   
	    {        
	        ArrayList<String> list = new ArrayList<String> ();
	           try {
	              
	               // Create a result set
	                Statement stmt = connection.createStatement();
	                resultSet = stmt.executeQuery("SELECT `genre_name` , `genre_desc` , `chat_name` , `chat_desc`  FROM `chatrooms` , `chat_genres`  WHERE `fk_genre_id` = `pk_genre_id`AND `is_public` =1 GROUP BY `genre_name` , `chat_name`");
	            int i = 0 ;
	            list.add(0, "ChatroomList02"); 
	           
	            while (resultSet.next()) {
	                    // Get the data from the row using the column index    
	            	  list.add(i+1,resultSet.getString("genre_name"));
	            	  list.add(i+2,Newline(resultSet.getString("genre_desc"),30));
	            	  list.add(i+3,resultSet.getString("chat_name"));
	            	  list.add(i+4,Newline(resultSet.getString("chat_desc"),30));                                                   
	                   
	                    i+=4;     
	                                   
	                }
	               
	           }
	           
	           catch (SQLException e)
               {
                   e.printStackTrace();
               }
         String output[] = (String [])list.toArray (new String [list.size ()]);
         System.out.println("Getting chat list\n");
       return output;       
   
	    }
		
		public void AddChatPost(String chat_name ,String username,String text)
		{
			try
			{
				PreparedStatement stmt = connection.prepareStatement("INSERT INTO `chat_posts`(`fk_chat_id`,`fk_user_id`,`msg_time`,`msg_text`,`attachment`) VALUES ((SELECT `pk_chat_id` FROM `chatrooms` WHERE `chat_name` =?),(SELECT `pk_user_id` FROM `users` WHERE `username` =?),now(),?,NULL);");	
				stmt.setString(1,chat_name);
				stmt.setString(2, username);
				stmt.setString(3,text);
				stmt.execute();
				
			}
			catch (SQLException e) {		
				e.printStackTrace();
			}
		}
		
		public String [] ShowNposts(int n,String chatID)
		{
			//Integer.parseInt(n);
			ArrayList<String> al = new ArrayList<String> ();
			   try {
			       
				   // Create a result set
			        PreparedStatement stmt = connection.prepareStatement("SELECT `username` , `msg_text` FROM `chat_posts` , `users` , `chatrooms` WHERE `fk_chat_id` = `pk_chat_id`  AND `fk_user_id` = `pk_user_id`  AND  `chat_name` = ? GROUP BY `pk_msg_id` ORDER BY `msg_time` LIMIT ? ");
			        stmt.setString(1, chatID);
			        stmt.setInt(2, n);
			        resultSet = stmt.executeQuery();

			        al.add(0, "ShowPosts02");
			        al.add(1,chatID);
			        int i =2;
			        while(resultSet.next())
			        {
			        	al.add(i, Newline ( resultSet.getString("username")+": "+resultSet.getString("msg_text"),70));
			      
			        	i+=1;
			        }
			   }
			   catch (SQLException e)
		        {
		        	e.printStackTrace();
		        } 
			   String chat [] = (String []) al.toArray (new String [al.size ()]);
			return chat;
			
		}
		
		public boolean AddSearchChatRoom(String chat_name)
		{
			ArrayList<String> chat = new ArrayList<String>();
			boolean exists = true;
		
			try {
				
				PreparedStatement stmt = connection.prepareStatement("SELECT `chat_name`FROM `chatrooms` WHERE `chat_name` =?");
				stmt.setString(1,chat_name);	
				resultSet = stmt.executeQuery();
				
				int i = 0;	
				while(resultSet.next())
			
				{		
					chat.add(i,resultSet.getString("chat_name"));
					i++;
				} 
			 
				exists = chat.contains(chat_name);
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
			return exists;
		}
		
		public boolean AddSearchGenre(String genre_name)
	    {
	        ArrayList<String> chat = new ArrayList<String>();
	        boolean exists = true;
	   
	        try {
	           
	            PreparedStatement stmt = connection.prepareStatement("SELECT `genre_name`FROM `chat_genres` WHERE `genre_name` =?");
	            stmt.setString(1,genre_name);   
	            resultSet = stmt.executeQuery();
	           
	            int i = 0;   
	            while(resultSet.next())
	       
	            {       
	                chat.add(i,resultSet.getString("chat_name"));
	                i++;
	            }
	         
	            exists = chat.contains(genre_name);
	        } catch (SQLException e) {
	            e.printStackTrace();
	       
	        }
	        return exists;
	    }
		
		public boolean AddNewChat(String genre_name ,String username ,String chat_name,String chat_desc)
		{
			boolean output=false;
			if(!AddSearchChatRoom(chat_name))
			{
				try {
				
					PreparedStatement stmt = connection.prepareStatement("INSERT INTO chatrooms ( fk_genre_id,fk_chat_initiator,date_created,is_public,chat_name,chat_desc) VALUES((SELECT `pk_genre_id` FROM `chat_genres`WHERE `genre_name` = ?), (SELECT `pk_user_id` FROM `users`WHERE `username` = ?) ,now(),'1',?,?)");
					stmt.setString(1,genre_name);
					stmt.setString(2,username);
					stmt.setString(3,chat_name);
					stmt.setString(4,chat_desc);
				
					stmt.execute();
					output=true;
			 
			}
				catch (SQLException e) 
				{
				e.printStackTrace();
				}
					
			}
				System.out.println("Response from createChat\n"+output);
				return output;
			
		}

		public boolean AddGenre(String genre_name,String genre_desc)
	    {
			boolean output=false;
			if(!AddSearchGenre(genre_name))
			{
				try
				{
	            PreparedStatement stmt = connection.prepareStatement("INSERT INTO `chat_genres` (`genre_name`,`genre_desc`) VALUES (?,?)");   
	            stmt.setString(1,genre_name);
	            stmt.setString(2,genre_desc);
	            stmt.execute();
	            output=true;
				}
				catch (SQLException e) {       
					e.printStackTrace();
				}
			}
			return output;
	    }
	    
	
	
	private int getRowCount()
	{
		int numRows=0;
		try
		{
			while(resultSet.next())
				numRows++;	
		}
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
			System.exit( 1 );
		}
		
		try
		{
		resultSet.beforeFirst();
		}
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
			System.exit( 1 );
		}
		
		return numRows;
	}
	
	
	public void Close()
		{
			try
			{
				resultSet.close();
				connection.close();
			}
 
			catch( Exception exception )
			{
				exception.printStackTrace();
			}
		} // end finally
	
	
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
 
} // end class ConnectionManager
