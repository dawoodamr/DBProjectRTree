package rtreepack;

public class LeafNode extends Node 
{
	int m, M;
	
	public LeafNode(int m, int M, int lsn, Rectangle bounds)
	{
		this.m = m;
		this.M = M;
		this.lsn = lsn;
		this.bounds = bounds;
	}

	@Override
	public String toString() 
	{
		return "LeafNode [m=" + m + ", M=" + M + ", lsn=" + lsn + ", bounds="
				+ bounds + "]";
	}

	public void updateBounds() 
	{
		

	}

}
