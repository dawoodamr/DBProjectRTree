package rtreepack;

import java.util.Stack;

public class RTree 
{
	int m, M;
	int dimensions;
	InternalNode root;
	static int lsnCount = 1;
	
	public RTree(int m, int M, int dimensions)
	{
		this.m = m;
		this.M = M;
		this.dimensions = dimensions;
		root = new InternalNode(m, M, lsnCount++, new Rectangle(dimensions, 0, 0, 0, 0));
	}
	
	public static void main(String[] args)
	{
		RTree tree = new RTree(1, 3, 2);
		tree.root.entries.add(new Entry(
				new InternalNode(tree.m, tree.M, lsnCount, 
						new Rectangle(tree.dimensions, 3, 4, 2, 6)), lsnCount++));
		System.out.println("Hello RTree: "+tree.toString());
		Rectangle c = new Rectangle(3, 2,3,2,3,4,5);
		Rectangle cl = new Rectangle(3, 2,3,4,5,6,7);
		System.out.println(cl.equals(c));
	}
	
	public void insert(Rectangle rec)
	{
		Stack<InternalNode> stack = new Stack<InternalNode>();
		findLeaf(root, root.lsn, rec, stack);
		InternalNode leaf = stack.pop();
		LeafNode n = new LeafNode(m, M, lsnCount++, rec);
		Entry e = new Entry(n, n.lsn);
		leaf.entries.add(e);
		if(leaf.entries.size() > M)
		{
			InternalNode right = new InternalNode(m, M, lsnCount++, rec);
			leaf.right = right;
			// split
			for(int i=0;i<M/2;i++)
			{
				right.entries.addFirst(leaf.entries.removeLast());
			}
			leaf.updateBounds();
			right.updateBounds();
			// extendParent()
		}else{
			Rectangle rtemp = leaf.bounds.Clone();
			leaf.updateBounds();
			if(!rtemp.equals(leaf.bounds))
			{
				//update parent
			}else{
				// w unlock leaf
			}
		}
		
	}
	
	void findLeaf(InternalNode n, int lsnExpected, Rectangle rec, Stack<InternalNode> stack)
	{
		if(n.entries.getFirst().getClass().getSimpleName() == "LeafNode")
		{
			// w lock n
		}else
		{
			// r lock n
		}
		if(n.lsn < lsnExpected)
		{
			// get geometrically optimal node on the right link chain for rec
			InternalNode temp = n;
			int change = Integer.MAX_VALUE;
			InternalNode best = n;
			while(temp.lsn != lsnExpected)
			{
				if(temp.bounds.enlargement(rec) < change)
				{
					change = temp.bounds.enlargement(rec);
					best = temp;
				}
				temp = temp.right;
			}
			// unlock n
			n = best;
			// lock n
		}
		if(n.entries.getFirst().node.getClass().getSimpleName() == "LeafNode")
		{
			stack.push(n);
		}else{
			// get geometrically best entry
			Entry best = n.entries.getFirst();
			int change = Integer.MAX_VALUE;
			for(int i=0;i<n.entries.size();i++)
			{
				if(n.entries.get(i).node.bounds.enlargement(rec) < change)
				{
					change = n.entries.get(i).node.bounds.enlargement(rec);
					best = n.entries.get(i);
				}
			}
			stack.push(n);
			// r unlock n
			findLeaf((InternalNode) best.node, best.lsnExpected, rec, stack);
		}
		
	}

	void updateParent(InternalNode p, Stack<InternalNode> stack)
	{
		if(stack.isEmpty())
		{
			// w unlock p
		}
		else
		{
			InternalNode parent = stack.pop();
			// w lock parent
			Entry e = parent.entries.getFirst();
			boolean found = false;
			while(parent.right != null)
			{
				for(int i=0;i<parent.entries.size();i++)
				{
					if(parent.entries.get(i).node == p)
					{
						e = parent.entries.get(i);
						found = true;
						break;
					}
				}
				if(found)
					break;
				// w unlock parent
				parent = parent.right;
				// w lock parent
			}
			e.node.bounds = p.bounds;
			// w unlock p
			Rectangle rtemp = parent.bounds.Clone();
			parent.updateBounds();
			if(rtemp != parent.bounds)
				updateParent(parent, stack);
			else{
				// w unlock parent
			}
		}
	}
	
	public String toString() 
	{
		return "RTree [m=" + m + ", M=" + M + ", dimensions=" + dimensions
				+ ", root=" + root + "]";
	}

}
