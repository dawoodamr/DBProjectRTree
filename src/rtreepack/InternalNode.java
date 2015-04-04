package rtreepack;

import java.util.LinkedList;

public class InternalNode extends Node 
{
	int m, M;
	Node right;
	LinkedList<Entry> entries;
	
	public InternalNode(int m, int M, int lsn, Rectangle bounds) 
	{
		this.m = m;
		this.M = M;
		this.lsn = lsn;
		this.bounds = bounds;
		entries = new LinkedList<Entry>();
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

	public String toString() 
	{
		return "InternalNode [m=" + m + ", M=" + M + ", right=" + right
				+ ", entries=" + entries + "]";
	}

}
