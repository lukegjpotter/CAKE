package demo;

public class Chat_TreeNode {
	
	String nodeName;
	String nodeDesc;
	
	public Chat_TreeNode(String name, String desc)
	{
		nodeName=name;
		nodeDesc=desc;
	}
	
	public String toString()
	{
		return nodeName;
	}
	
	public String getDesc()
	{
		return nodeDesc;
	}

}
