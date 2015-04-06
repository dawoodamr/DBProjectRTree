package rtreepack;

import java.util.LinkedList;
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
		/*tree.root.entries.add(new Entry(
				new LeafNode(tree.m, tree.M, tree.lsnCount, tree.root.bounds.Clone()),tree.lsnCount++));
		tree.root.entries.add(new Entry(
				new InternalNode(tree.m, tree.M, lsnCount, 
						new Rectangle(tree.dimensions, 3, 4, 2, 6)), lsnCount++));*/
		tree.insert(new Rectangle(2, 2,3,4,5));
		tree.insert(new Rectangle(2, 2,3,4,5));
		tree.insert(new Rectangle(2, 2,3,4,5));
		tree.insert(new Rectangle(2, 2,3,4,5));
		tree.insert(new Rectangle(2, 2,3,4,5));
		tree.insert(new Rectangle(2, 2,3,4,5));
		tree.insert(new Rectangle(2, 2,3,4,5));
		System.out.println("Hello RTree: "+tree.toString());
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addLast(tree.root);
		queue.addLast(new InternalNode(tree.m, tree.M, Integer.MAX_VALUE, tree.root.bounds));
		tree.printRTree(queue);
		queue.addLast(tree.root);
		queue.addLast(new InternalNode(tree.m, tree.M, Integer.MAX_VALUE, tree.root.bounds));
		tree.printBounds(queue);
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
			extendParent(leaf, right, stack);
		}else{
			Rectangle rtemp = leaf.bounds.Clone();
			leaf.updateBounds();
			if(!rtemp.equals(leaf.bounds))
				updateParent(leaf, stack);
			else{
				// w unlock leaf
			}
		}
		
	}
	
	void findLeaf(InternalNode n, int lsnExpected, Rectangle rec, Stack<InternalNode> stack)
	{
		if(n.entries.isEmpty() || n.entries.getFirst().getClass().getSimpleName().equals("LeafNode"))
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
		if(n.entries.isEmpty() || n.entries.getFirst().node.getClass().getSimpleName().equals("LeafNode"))
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
			if(!rtemp.equals(parent.bounds))
				updateParent(parent, stack);
			else{
				// w unlock parent
			}
		}
	}
	
	void extendParent(InternalNode p, InternalNode q, Stack<InternalNode> stack)
	{
		if(stack.isEmpty())
		{
			InternalNode root = new InternalNode(m, M, lsnCount++, p.bounds.Clone());
			// w lock root
			Entry e1 = new Entry(p, p.lsn);
			Entry e2 = new Entry(q, q.lsn);
			root.entries.add(e1);
			root.entries.add(e2);
			this.root = root;
			// w unlock q, p, root
		}else{
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
			}
			// w unlock q, p
			e.lsnExpected = p.lsn;
			parent.entries.add(new Entry(q, q.lsn));
			if(parent.entries.size() > M)
			{
				// split
				InternalNode right = new InternalNode(m, M, lsnCount++, new Rectangle(dimensions));
				for(int i=0;i<M/2;i++)
				{
					right.entries.addFirst(parent.entries.removeLast());
				}
				parent.right = right;
				parent.updateBounds();
				right.updateBounds();
				extendParent(parent, right, stack);
			}else{
				Rectangle rtemp = parent.bounds.Clone();
				parent.updateBounds();
				if(! parent.bounds.equals(rtemp))
					updateParent(parent, stack);
				else
				{
					// w unlock parent
				}
			}
		}
	}
	
	public String toString() 
	{
		return "RTree [m=" + m + ", M=" + M + ", dimensions=" + dimensions
				+ ", root=" + root + "]";
	}
	
	public void printRTree(LinkedList<Node> queue)
	{
		if(queue.isEmpty())
			return;
		Node n = queue.removeFirst();
		if(n.lsn == Integer.MAX_VALUE)
		{
			System.out.println("");
			printRTree(queue);
			return;
		}
		String s = "Node "+n.lsn+" ";
		if(n.getClass().getSimpleName().equals("InternalNode"))
		{
			InternalNode temp = (InternalNode) n;
			s += ":(";
			for(int i=0;i<temp.entries.size();i++)
			{
				s += temp.entries.get(i).lsnExpected + " - ";
				queue.addLast(temp.entries.get(i).node);
			}
			s += " )   ";
			if(temp.right == null)
				queue.addLast(new InternalNode(m, M, Integer.MAX_VALUE,temp.bounds));
		}
		System.out.print(s);
		printRTree(queue);
	}
	
	public void printBounds(LinkedList<Node> queue)
	{
		if(queue.isEmpty())
			return;
		Node n = queue.removeFirst();
		if(n.lsn == Integer.MAX_VALUE)
		{
			//System.out.println("");
			printBounds(queue);
			return;
		}
		String s = "Node "+n.lsn+" ("+n.bounds.intervals.toString()+")\n";
		if(n.getClass().getSimpleName().equals("InternalNode"))
		{
			InternalNode temp = (InternalNode) n;
			//s += ":(";
			for(int i=0;i<temp.entries.size();i++)
			{
				//s += temp.entries.get(i).lsnExpected + " - ";
				queue.addLast(temp.entries.get(i).node);
			}
			//s += " )   ";
			if(temp.right == null)
				queue.addLast(new InternalNode(m, M, Integer.MAX_VALUE,temp.bounds));
		}
		System.out.print(s);
		printBounds(queue);
	}
}
