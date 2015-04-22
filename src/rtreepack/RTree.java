package rtreepack;

import java.io.*;
import java.util.*;

public class RTree implements Serializable
{
	private static final long serialVersionUID = -2216591716780441626L;
	
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
	
	public static void main(String[] args) throws InterruptedException
	{
		//RTree.proveCorrectness();
		//RTree.buildRTreeToFile(1000, "RTree1000-3", 1, 3, 2);
		//RTree.buildRTreeToFile(1000, "RTree1000-6", 3, 6, 2);
		//RTree.buildRTreeToFile(1000, "RTree1000-10", 5, 10, 2);
		
		RTree tree3 = RTree.readRTree("RTree1000-3");
		RTree tree6 = RTree.readRTree("RTree1000-6");
		RTree tree10 = RTree.readRTree("RTree1000-10");
		
		//tree10.printRTree();
		
		int insertions = 1030;
		
		for(int i=2;i<insertions;i*=2)
			new Builder(tree10, i, 1000, 300, true, "RTree test1");
		
		for(int i=2;i<insertions;i*=2)
			new Monitor(tree10, i, 1000, 300, true, "RTree test2").join();
		
		/*for(int i=2;i<insertions;i*=2)
			new Builder(tree6, i, 0, 0, false, "RTree test3");
		
		for(int i=2;i<insertions;i*=2)
			new Monitor(tree6, i, 0, 0, false, "RTree test4").join();
		
		for(int i=2;i<insertions;i*=2)
			new Builder(tree10, i, 0, 0, false, "RTree test5");
		
		for(int i=2;i<insertions;i*=2)
			new Monitor(tree10, i, 0, 0, false, "RTree test6").join();
		
		
		/*RTree tree = null;
		
		
		
		// serial - save batch
		tree = RTree.readRTree("RTree");
		
		for(int i=2;i<insertions;i*=2)
			new Builder(tree, i, 0, 0, true, "RTree test");
		
		// serial - save Xacts
		tree = RTree.readRTree("RTree");
		
		for(int i=2;i<insertions;i*=2)
			new Builder(tree, i, 0, 0, false, "RTree test");

		// concurrent - save batch
		tree = RTree.readRTree("RTree");
		
		try{
		for(int i=2;i<insertions;i*=2)
		{
			//tree = new RTree(1, 3, 2);
			new Monitor(tree, i, 0, 0, true, "RTree test").join();
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// concurrent - save Xacts
		tree = RTree.readRTree("RTree");
		
		try{
		for(int i=2;i<insertions;i*=2)
		{
			//tree = new RTree(1, 3, 2);
			new Monitor(tree, i, 0, 0, false, "RTree test").join();
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}*/
		

		
		/*Rectangle r8 = new Rectangle(2, 1,2,11,13);
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
		
		Inserter t1 = new Inserter(r19, tree);
		Inserter t2 = new Inserter(r18, tree);
		Inserter t3 = new Inserter(r17, tree);
		Inserter t4 = new Inserter(r16, tree);
		Inserter t5 = new Inserter(r15, tree);
		Inserter t6 = new Inserter(r14, tree);
		Inserter t7 = new Inserter(r13, tree);
		Inserter t8 = new Inserter(r12, tree);
		Inserter t9 = new Inserter(r11, tree);
		Inserter t10 = new Inserter(r10, tree);
		Inserter t11 = new Inserter(r9, tree);
		Inserter t12 = new Inserter(r8, tree);*/
		
		/*try {
			t1.join();t2.join();t3.join();t4.join();t5.join();t6.join();
			t7.join();t8.join();t9.join();t10.join();t11.join();t12.join();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}*/
		
		
		
		
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
	
	private void findLeaf(InternalNode n, int lsnExpected, Rectangle rec, Stack<InternalNode> stack)
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

	private void updateParent(InternalNode p, Stack<InternalNode> stack)
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
	
	private void extendParent(InternalNode p, InternalNode q, Stack<InternalNode> stack)
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
	
	public LinkedList<LeafNode> search(Rectangle rec)
	{
		LinkedList<LeafNode> result = new LinkedList<LeafNode>();
		Stack<Integer> lsns = new Stack<Integer>();
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		lsns.push(root.lsn);
		reduceStack(rec, stack, lsns, result);
		return result;
	}
	
	private void reduceStack(Rectangle rec, Stack<Node> stack, Stack<Integer> lsns, LinkedList<LeafNode> result)
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
	
	public void delete(Rectangle rec)
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
	
	public void save(String fileName)
	{
		try
		{
			FileOutputStream f = new FileOutputStream(fileName);
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(this);
			o.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static RTree readRTree(String fileName)
	{
		RTree tree = null;
		try 
		{
			FileInputStream fi = new FileInputStream(fileName);
			ObjectInputStream oi = new ObjectInputStream(fi);
			tree = (RTree) oi.readObject();
			oi.close();
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		
		return tree;
	}
	
	public String toString() 
	{
		return "RTree [m=" + m + ", M=" + M + ", dimensions=" + dimensions
				+ ", root=" + root + "]";
	}
	
	public void printRTree()
	{
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(this.root);
		queue.add(new InternalNode(this.m, this.M, Integer.MAX_VALUE,this.root.bounds));
		printRTree(queue);
	}
	
	private void printRTree(LinkedList<Node> queue)
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
	
	public void printBounds()
	{
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(this.root);
		queue.add(new InternalNode(this.m, this.M, Integer.MAX_VALUE,this.root.bounds));
		printBounds(queue);
	}
	
	private void printBounds(LinkedList<Node> queue)
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
	
	public void printHybrid()
	{
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(this.root);
		queue.add(new InternalNode(this.m, this.M, Integer.MAX_VALUE,this.root.bounds));
		printHybrid(queue);
	}
	
	private void printHybrid(LinkedList<Node> queue)
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
	
	public static RTree proveCorrectness()
	{
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
		
		RTree tree = new RTree(1, 3, 2);
		
		tree.insert(r19);tree.insert(r18);tree.insert(r17);
		tree.insert(r16);tree.insert(r15);tree.insert(r14);
		tree.insert(r13);tree.insert(r12);tree.insert(r11);
		tree.insert(r10);tree.insert(r9);tree.insert(r8);
		
		tree.printRTree();
		System.out.println();
		
		Rectangle r = new Rectangle(2, 0, 19, 6, 11);
		
		LinkedList<LeafNode> list = tree.search(r);
		System.out.println("Search: " + list.size() + " : " + list + "\n");
		
		Rectangle rec = new Rectangle(2, 14, 19, 6, 9); 
		tree.delete(rec);
		System.out.println("Rectangle: " + rec + "  deleted!\n");
		
		list = tree.search(r);
		System.out.println("Search: " + list.size() + " : " + list + "\n");
		
		tree.printBounds();
		
		return tree;
	}

	public static void buildRTreeToFile(int treeSize, String fileName, int m, int M, int dimensions)
	{
		RTree tree = new RTree(m, M, dimensions);
		
		Random random = new Random();
		
		for(int i=0;i<treeSize;i++)
		{
			int size = dimensions * 2;
			LinkedList<Integer> l = new LinkedList<Integer>();
			for(int j=0;j<size;j++)
				l.add(random.nextInt());
			Collections.sort(l);
			int[] intervals = new int[size];
			for(int j=0;j<size;j++)
				intervals[j] = l.get(j);
			tree.insert(new Rectangle(dimensions, intervals));
		}
		
		tree.save(fileName);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
