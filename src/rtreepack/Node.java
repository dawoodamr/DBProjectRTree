package rtreepack;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Node 
{
	int lsn;
	Rectangle bounds;
	boolean deleted;
	ReentrantReadWriteLock rwl;
	
	public abstract void updateBounds();

	
	public String toString() 
	{
		return "Node [lsn=" + lsn + ", bounds=" + bounds + "]";
	}

}
