package rtreepack;

public abstract class Node 
{
	int lsn;
	Rectangle bounds;
	
	public abstract void updateBounds();

	
	public String toString() 
	{
		return "Node [lsn=" + lsn + ", bounds=" + bounds + "]";
	}

}
