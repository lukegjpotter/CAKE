//Written by: Terry Lyons
package demo;


import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import javax.swing.*;


public class Chat_ChatroomsList extends JPanel implements TreeSelectionListener, ActionListener  {


	   
        public Chat_ChatroomsDescription  description;
        public static  DefaultMutableTreeNode top = new DefaultMutableTreeNode("Chatrooms");
       /*Tree must be initialised here or else will give 
        nullValueException when trying to add chats from database on login*/
        private JTree tree = new JTree(top);
       	public static   DefaultMutableTreeNode tmpLevel1Node;
        public JScrollPane treeView;
        public JPanel treePanel=new JPanel(), buttonPanel=new JPanel();
        public JButton JoinChat=new JButton("Join"), CreateChat= new JButton("Create Chat");
    	private GridBagConstraints constraints;
    	private GridBagLayout gridBag;
        private String ChatID="";
  
        //Set the look and feel.
       // private static boolean useSystemLookAndFeel = true;

    	
    	public JPanel Chatrooms;
        
	
	public Chat_ChatroomsList()
	{		
				Chatrooms = new JPanel();
				gridBag=new GridBagLayout();
				treePanel.setLayout(gridBag);
				Chatrooms.setLayout(gridBag);
				constraints=new GridBagConstraints();
		
		        //Create a tree that allows one selection at a time.
		        tree.getSelectionModel().setSelectionMode
		                (TreeSelectionModel.SINGLE_TREE_SELECTION);

		 
		        //Listen for when the selection changes.
		        tree.addTreeSelectionListener(this);
		        tree.setRootVisible(false);
		        
		        //Create the scroll pane and add the tree to it. 
		        treeView = new JScrollPane(tree);

		        //Create the description field
		        description = new Chat_ChatroomsDescription();
	
		        //Create buttons
		        JoinChat.addActionListener(this);
		        CreateChat.addActionListener(this);
		        buttonPanel.add(JoinChat);
		        buttonPanel.add(CreateChat);

		        Dimension Size = new Dimension(190,415);
		        treeView.setMinimumSize(Size);
		        treeView.setPreferredSize(Size);
		        treeView.setMaximumSize(Size);
		        
		        
		    	constraints.gridx=1;
				constraints.gridy=1;
				constraints.gridwidth=1;
				constraints.gridheight=1;
				constraints.weightx=1;
				constraints.weighty=1;
				constraints.insets = new Insets(0,0,10,0);
				gridBag.setConstraints(treeView, constraints);
				treePanel.add(treeView);
				
				constraints.gridx=1;
				constraints.gridy=2;
				constraints.gridwidth=1;
				constraints.gridheight=1;
				constraints.weightx=1;
				constraints.weighty=1;
				gridBag.setConstraints( buttonPanel, constraints);
				treePanel.add( buttonPanel);
				
				constraints.gridx=1;
				constraints.gridy=3;
				constraints.gridwidth=1;
				constraints.gridheight=1;
				constraints.weightx=1;
				constraints.weighty=1;
				constraints.insets = new Insets(5,0,0,0);
				gridBag.setConstraints(description.ChatDescription, constraints);
				treePanel.add(description.ChatDescription);
				
				
				Chatrooms.setMinimumSize(new Dimension(200, 700));
				Chatrooms.setPreferredSize(new Dimension(200, 700));
				Chatrooms.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			
				
				constraints.gridx=1;
				constraints.gridy=1;
				constraints.gridwidth=1;
				constraints.gridheight=1;
				constraints.weightx=1;
				constraints.weighty=1;
				constraints.anchor=GridBagConstraints.FIRST_LINE_END;
				constraints.insets = new Insets(0,0,0,0);
				gridBag.setConstraints(treePanel, constraints);
				Chatrooms.add(treePanel);

	}
	
	/* Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) 
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        Chat_TreeNode TreeNode;
        TreeNode=(Chat_TreeNode)nodeInfo;
        //Currently just displays node name,need to display description
        description.setDescription(TreeNode.getDesc());
       
        if (node.isLeaf()) 
        {
           ChatID=TreeNode.toString();
        }
        else
        {
        	ChatID="";
        	
        }
    }
    
	 public void actionPerformed(ActionEvent evt)
	 {
		 if(evt.getSource()==JoinChat)
		 {
			 if(!ChatID.equals(""))
			 {
				 Chat_tabbedPane.addtaby(ChatID);
			 }
		 }
		 else if(evt.getSource()==CreateChat)
		 {
			 Chat_CreateNewChat newChat=new Chat_CreateNewChat();
		 }
	 }
    
    public static void createGenreNode(String genre,String genreDesc) 
    { 
    	Chat_TreeNode node = new Chat_TreeNode(genre, genreDesc);
        DefaultMutableTreeNode level1Node = new DefaultMutableTreeNode(node);
        top.add(level1Node);
        tmpLevel1Node=level1Node;
        System.out.println("Adding Genre..."+genre);
    }
    
    public static void createChatNode(String chat,String chatDesc)
    {
    	Chat_TreeNode node = new Chat_TreeNode(chat, chatDesc);
    	 DefaultMutableTreeNode Level2Node = new DefaultMutableTreeNode(node);
    	tmpLevel1Node.add(Level2Node);
    	 System.out.println("Adding Chat..."+chat);
    }

}




