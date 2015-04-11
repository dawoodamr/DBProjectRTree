package rtreepack;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LeafNode extends Node 
{
	private static final long serialVersionUID = -4326526835690819196L;
	
	int m, M;
	
	public LeafNode(int m, int M, int lsn, Rectangle bounds)
	{
		this.m = m;
		this.M = M;
		this.lsn = lsn;
		this.bounds = bounds;
		deleted = false;
		rwl = new ReentrantReadWriteLock();
	}

	@Override
	public String toString() 
	{
		/*return "LeafNode [m=" + m + ", M=" + M + ", lsn=" + lsn + ", bounds="
				+ bounds + "]";*/
		return "LeafNode [lsn=" + lsn + ", deleted=" + deleted + ", bounds=" + bounds + "]";
	}

	public void updateBounds() 
	{
		

	}

}
