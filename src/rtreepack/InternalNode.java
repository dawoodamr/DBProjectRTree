package rtreepack;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InternalNode extends Node 
{
	private static final long serialVersionUID = 4489682376643576188L;
	
	int m, M;
	InternalNode right;
	LinkedList<Entry> entries;
	
	public InternalNode(int m, int M, int lsn, Rectangle bounds) 
	{
		this.m = m;
		this.M = M;
		this.lsn = lsn;
		this.bounds = bounds;
		entries = new LinkedList<Entry>();
		deleted = false;
		rwl = new ReentrantReadWriteLock();
	}

	public void updateBounds() 
	{
		bounds = new Rectangle(this.bounds.dimensions);
		
		for(int i=0;i<bounds.dimensions;i++)
		{
			int low=Integer.MAX_VALUE, high=Integer.MIN_VALUE;
			
			for(int j=0;j<entries.size();j++)
			{
				Entry e = entries.get(j);
				
				if(low>e.node.bounds.intervals.get(i).Low)
					low = e.node.bounds.intervals.get(i).Low;
				if(high<e.node.bounds.intervals.get(i).High)
					high = e.node.bounds.intervals.get(i).High;
			}
			
			bounds.intervals.get(i).Low = low;
			bounds.intervals.get(i).High = high;
		}
	}

	@Override
	public String toString() 
	{
		/*return "InternalNode [m=" + m + ", M=" + M + ", right=" + right
				+ ", \n entries=" + entries + ", lsn=" + lsn + ", bounds="
				+ bounds + "]";*/
		return "InternalNode [right=" + right + ", \n entries=" + entries + ", lsn=" + lsn + ", bounds="
		+ bounds + "]"; 
	}

}
