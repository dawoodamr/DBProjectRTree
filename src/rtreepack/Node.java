package rtreepack;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Node implements Serializable
{
	private static final long serialVersionUID = -1838418223669710147L;
	
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