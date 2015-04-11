package rtreepack;

import java.io.Serializable;

public class Entry implements Serializable
{
	private static final long serialVersionUID = 4850569213309079988L;
	
	Node node;
	int lsnExpected;
	
	public Entry(Node node, int lsnExpected) 
	{
		this.node = node;
		this.lsnExpected = lsnExpected;
	}

	public String toString() 
	{
		return "Entry [node=" + node + ", lsnExpected=" + lsnExpected + "]";
	}
}
