package rtreepack;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RTree 
{
	int m, M;
	int dimensions;
	InternalNode root;
	int lsnCount = 1;
	
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
		LinkedList<Node> queue = new LinkedList<Node>();
		Rectangle r8 = new Rectangle(2, 1,2,11,13);
		Rectangle r9 = new Rectangle(2, 5,6,15,16);
		Rectangle r10 = new Rectangle(2, 5,6,12,14);
		Rectangle r11 = new Rectangle(2, 8,10,5,17);
		Rectangle r12 = new Rectangle(2, 4,9,9,11);
		Rectangle r13 = new Rectangle(2, 12,13,9,16);
		Rectangle r14 = new Rectangle(2, 11,13,11,13);
		Rectangle r15 = new Rectangle(2, 0,1,0,3);
		Rectangle r16 = new Rectangle(2, 2,7,2,8);
		Rectangle r17 = new Rectangle(2, 14,19,6,9);
		Rectangle r18 = new Rectangle(2, 15,17,1,10);
		Rectangle r19 = new Rectangle(2, 16,18,4,7);
		
		/*Thread t1 = new Inserter(r19, tree);
		Thread t2 = new Inserter(r18, tree);
		Thread t3 = new Inserter(r17, tree);
		Thread t4 = new Inserter(r16, tree);
		Thread t5 = new Inserter(r15, tree);
		Thread t6 = new Inserter(r14, tree);
		Thread t7 = new Inserter(r13, tree);
		Thread t8 = new Inserter(r12, tree);
		Thread t9 = new Inserter(r11, tree);
		Thread t10 = new Inserter(r10, tree);
		Thread t11 = new Inserter(r9, tree);
		Thread t12 = new Inserter(r8, tree);
		
		/*ExecutorService ex = Executors.newCachedThreadPool();
		ex.submit(t1);ex.submit(t2);ex.submit(t3);ex.submit(t4);ex.submit(t5);ex.submit(t6);
		ex.submit(t7);ex.submit(t8);ex.submit(t9);ex.submit(t10);ex.submit(t11);ex.submit(t12);
		ex.shutdown();*/
		
		/*while(t1.getState()!=Thread.State.TERMINATED || t2.getState()!=Thread.State.TERMINATED
				|| t3.getState()!=Thread.State.TERMINATED || t4.getState()!=Thread.State.TERMINATED
				|| t5.getState()!=Thread.State.TERMINATED || t6.getState()!=Thread.State.TERMINATED
				|| t7.getState()!=Thread.State.TERMINATED || t8.getState()!=Thread.State.TERMINATED
				|| t9.getState()!=Thread.State.TERMINATED || t10.getState()!=Thread.State.TERMINATED
				|| t11.getState()!=Thread.State.TERMINATED || t12.getState()!=Thread.State.TERMINATED){}
		*/
		
		
		tree.insert(r19);tree.insert(r18);tree.insert(r17);
		tree.insert(r16);tree.insert(r15);tree.insert(r14);
		tree.insert(r13);tree.insert(r12);tree.insert(r11);
		tree.insert(r10);tree.insert(r9);tree.insert(r8);
		queue.add(tree.root);
		queue.add(new InternalNode(tree.m, tree.M, Integer.MAX_VALUE,tree.root.bounds));
		tree.printRTree(queue);
		/*queue.add(tree.root);
		queue.add(new InternalNode(tree.m, tree.M, Integer.MAX_VALUE,
				tree.root.bounds));
		tree.printHybrid(queue);*/
		Rectangle r = new Rectangle(2, 0, 19, 6, 11);
		new Searcher(r, tree);
		new Deleter(r, tree);
		new Searcher(r, tree);
		//System.out.println("Search: " + list.size() + " : " + list);
		
		
		//System.out.println(r.contains(r17));
		/*queue.add(tree.root);
		queue.add(new InternalNode(tree.m, tree.M, Integer.MAX_VALUE, tree.root.bounds));
		tree.printBounds(queue);
		/*tree.root.entries.add(new Entry(
				new LeafNode(tree.m, tree.M, tree.lsnCount, tree.root.bounds.Clone()),tree.lsnCount++));
		tree.root.entries.add(new Entry(
				new InternalNode(tree.m, tree.M, lsnCount, 
						new Rectangle(tree.dimensions, 3, 4, 2, 6)), lsnCount++));
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
		Rectangle r = new Rectangle(2, 2,2,5,5);
		System.out.println(tree.search(r));*/
		/*Rectangle r6 = new Rectangle(1, 3,8);
		Rectangle r5 = new Rectangle(1, 10,11);
		Rectangle r4 = new Rectangle(1, 7,9);
		Rectangle r3 = new Rectangle(1, 5,6);
		Rectangle r2 = new Rectangle(1, 2,4);
		Rectangle r1 = new Rectangle(1, 0,1);
		System.out.println(r1.contains(r6));
		System.out.println(r2.contains(r6));
		System.out.println(r3.contains(r6));
		System.out.println(r4.contains(r6));
		System.out.println(r5.contains(r6));*/
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
			InternalNode right = new InternalNode(m, M, leaf.lsn, rec);
			right.rwl.writeLock().lock();
			leaf.lsn = lsnCount++;
			leaf.right = right;
			// split
			for(int i=0;i<(M+1)/2;i++)
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
				leaf.rwl.writeLock().unlock();
			}
		}
	}
	
	void findLeaf(InternalNode n, int lsnExpected, Rectangle rec, Stack<InternalNode> stack)
	{
		if(n.entries.isEmpty() || n.entries.getFirst().node.getClass().getSimpleName().equals("LeafNode"))
		{
			// w lock n
			n.rwl.writeLock().lock();
		}else
		{
			// r lock n
			n.rwl.readLock().lock();
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
			if(n.entries.isEmpty() || n.entries.getFirst().node.getClass().getSimpleName().equals("LeafNode"))
				n.rwl.writeLock().unlock();
			else
				n.rwl.readLock().unlock();
			n = best;
			// lock n
			if(n.entries.isEmpty() || n.entries.getFirst().node.getClass().getSimpleName().equals("LeafNode"))
				n.rwl.writeLock().lock();
			else
				n.rwl.readLock().lock();
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
			n.rwl.readLock().unlock();
			findLeaf((InternalNode) best.node, best.lsnExpected, rec, stack);
		}
		
	}

	void updateParent(InternalNode p, Stack<InternalNode> stack)
	{
		if(stack.isEmpty())
		{
			// w unlock p
			p.rwl.writeLock().unlock();
		}
		else
		{
			InternalNode parent = stack.pop();
			// w lock parent
			parent.rwl.writeLock().lock();
		//	Entry e = parent.entries.getFirst();
			boolean found = false;
			while(parent != null)
			{
				for(int i=0;i<parent.entries.size();i++)
				{
					if(parent.entries.get(i).node == p)
					{
					//	e = parent.entries.get(i);
						found = true;
						break;
					}
				}
				if(found)
					break;
				// w unlock parent
				parent.rwl.writeLock().unlock();
				parent = parent.right;
				// w lock parent
				parent.rwl.writeLock().lock();
			}
			//e.node.bounds = p.bounds;
			// w unlock p
			p.rwl.writeLock().unlock();
			Rectangle rtemp = parent.bounds.Clone();
			parent.updateBounds();
			if(!rtemp.equals(parent.bounds))
				updateParent(parent, stack);
			else{
				// w unlock parent
				parent.rwl.writeLock().unlock();
			}
		}
	}
	
	void extendParent(InternalNode p, InternalNode q, Stack<InternalNode> stack)
	{
		if(stack.isEmpty())
		{
			InternalNode newroot = new InternalNode(m, M, lsnCount++, p.bounds.Clone());
			// w lock newroot
			newroot.rwl.writeLock().lock();
			// w unlock q, p, root
			p.rwl.writeLock().unlock();
			q.rwl.writeLock().unlock();
			Entry e1 = new Entry(p, p.lsn);
			Entry e2 = new Entry(q, q.lsn);
			newroot.entries.add(e1);
			newroot.entries.add(e2);
			this.root = newroot;
			newroot.rwl.writeLock().unlock();
		}else{
			InternalNode parent = stack.pop();
			// w lock parent
			parent.rwl.writeLock().lock();
			Entry e = null;//parent.entries.getFirst();
			boolean found = false;
			while(parent != null)
			{
				for(int i=0;i<parent.entries.size();i++)
				{
					if(parent.entries.get(i).node.equals(p))
					{
						e = parent.entries.get(i);
						found = true;
						break;
					}
				}
				if(found)
					break;
				parent.rwl.writeLock().unlock();
				parent = parent.right;
				parent.rwl.writeLock().lock();
			}
			e.lsnExpected = p.lsn;
			parent.entries.add(new Entry(q, q.lsn));
			// w unlock q, p
			p.rwl.writeLock().unlock();
			q.rwl.writeLock().unlock();
			if(parent.entries.size() > M)
			{
				// split
				InternalNode right = new InternalNode(m, M, parent.lsn, new Rectangle(dimensions));
				right.rwl.writeLock().lock();
				parent.lsn = lsnCount++;
				for(int i=0;i<(M+1)/2;i++)
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
					parent.rwl.writeLock().unlock();
				}
			}
		}
	}
	
	LinkedList<LeafNode> search(Rectangle rec)
	{
		LinkedList<LeafNode> result = new LinkedList<LeafNode>();
		Stack<Integer> lsns = new Stack<Integer>();
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		lsns.push(root.lsn);
		reduceStack(rec, stack, lsns, result);
		return result;
	}
	
	void reduceStack(Rectangle rec, Stack<Node> stack, Stack<Integer> lsns, LinkedList<LeafNode> result)
	{
		while(! stack.isEmpty())
		{
			Node p1 = stack.pop();
			int lsn = lsns.pop();
			if(p1.getClass().getSimpleName().equals("LeafNode"))
			{
				if(rec.contains(p1.bounds) && ! p1.deleted)
					result.add((LeafNode) p1);
			}else{
				InternalNode p = (InternalNode) p1;
				// r lock p
				p.rwl.readLock().lock();
				if(lsn < p.lsn)
				{
					InternalNode n = p.right;
					while(n != null && n.lsn != lsn)
					{
						// r lock n
						n.rwl.readLock().lock();
						stack.push(n);
						lsns.push(n.lsn);
						// r unlock n
						n.rwl.readLock().unlock();
						n = n.right;
					}
				}
				for(int i=0;i<p.entries.size();i++)
				{
					if(rec.intersecting(p.entries.get(i).node.bounds))
					{
						stack.push(p.entries.get(i).node);
						lsns.push(p.entries.get(i).lsnExpected);
					}
				}
				// r unlock p
				p.rwl.readLock().unlock();
			}
		}
	}
	
	void delete(Rectangle rec)
	{
		LinkedList<LeafNode> result = new LinkedList<LeafNode>();
		Stack<Integer> lsns = new Stack<Integer>();
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		lsns.push(root.lsn);
		reduceStack(rec, stack, lsns, result);
		for(int i=0;i<result.size();i++)
			result.get(i).deleted = true;
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
		String s = "#"+n.lsn+" ";
		if(n.getClass().getSimpleName().equals("InternalNode"))
		{
			InternalNode temp = (InternalNode) n;
			s += ":(";
			for(int i=0;i<temp.entries.size();i++)
			{
				s += temp.entries.get(i).lsnExpected + " - ";
				queue.addLast(temp.entries.get(i).node);
			}
			s = s.substring(0, s.length()-3);
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
			//s = s.substring(0, s.length()-3);
			//s += " )   ";
			if(temp.right == null)
				queue.addLast(new InternalNode(m, M, Integer.MAX_VALUE,temp.bounds));
		}
		System.out.print(s);
		printBounds(queue);
	}
	
	public void printHybrid(LinkedList<Node> queue)
	{
		if(queue.isEmpty())
			return;
		Node n = queue.removeFirst();
		if(n.lsn == Integer.MAX_VALUE)
		{
			System.out.println("");
			printHybrid(queue);
			return;
		}
		String s = "#"+n.lsn+"";
		if(n.getClass().getSimpleName().equals("InternalNode"))
		{
			InternalNode temp = (InternalNode) n;
			s += ":(";
			for(int i=0;i<temp.entries.size();i++)
			{
				s += temp.entries.get(i).lsnExpected + " - ";
				queue.addLast(temp.entries.get(i).node);
			}
			s = s.substring(0, s.length()-3);
			s += " )  ";
			if(temp.right == null)
				queue.addLast(new InternalNode(m, M, Integer.MAX_VALUE,temp.bounds));
		}
		s += n.bounds + "  "; 
		System.out.print(s);
		printHybrid(queue);
	}
}
