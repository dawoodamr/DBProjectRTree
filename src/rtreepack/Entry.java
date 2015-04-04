package rtreepack;

public class Entry 
{
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
