package rtreepack;

import java.util.*;
import java.util.concurrent.*;

public class Monitor extends Thread 
{
	RTree tree;
	int insert;
	int search;
	int delete;
	static int monitorNum = 0;
	
	public Monitor(RTree tree, int insert, int search, int delete)
	{
		this.tree = tree;
		this.insert = insert;
		this.search = search;
		this.delete = delete;
		Monitor.monitorNum++;
		this.start();
	}
	
	public void run()
	{
		System.out.println("Monitor " + Monitor.monitorNum + ":\n");
		long startTime = System.currentTimeMillis();
		
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
		
		/*try {
			new Inserter(r19, tree).join();
			new Inserter(r18, tree).join();
			new Inserter(r17, tree).join();
			new Inserter(r16, tree).join();
			new Inserter(r15, tree).join();
			new Inserter(r14, tree).join();
			new Inserter(r13, tree).join();
			new Inserter(r12, tree).join();
			new Inserter(r11, tree).join();
			new Inserter(r10, tree).join();
			new Inserter(r9, tree).join();
			new Inserter(r8, tree).join();
		} catch (InterruptedException e1) 
		{
			e1.printStackTrace();
		}*/
		
		/*Inserter t1 = new Inserter(r19, tree);
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
		Inserter t12 = new Inserter(r8, tree);
		
		Thread[] ts = new Thread[12];
		
		ts[0] = t1;ts[1] = t2;ts[2] = t3;ts[3] = t4;ts[4] = t5;ts[5] = t6;
		ts[6] = t7;ts[7] = t8;ts[8] = t9;ts[9] = t10;ts[10] = t11;ts[11] = t12;
		
		for(Thread t : ts)
		{
			t.start();
		}
		for(Thread t : ts)
		{
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
		
		/*ExecutorService ex = Executors.newCachedThreadPool();
		ex.submit(t1);ex.submit(t2);ex.submit(t3);ex.submit(t4);ex.submit(t5);ex.submit(t6);
		ex.submit(t7);ex.submit(t8);ex.submit(t9);ex.submit(t10);ex.submit(t11);ex.submit(t12);
		ex.shutdown();
		try {
			ex.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e1) 
		{
			e1.printStackTrace();
		}*/
		
		/*LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(tree.root);
		queue.add(new InternalNode(tree.m, tree.M, Integer.MAX_VALUE,tree.root.bounds));
		tree.printRTree(queue);
		
		Rectangle r = new Rectangle(2, 0, 19, 6, 11);
		try {
			new Searcher(r, tree).join();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}*/
		
		Random r = new Random();
		
		ArrayList<Inserter> inserters = new ArrayList<Inserter>();
		for(int i=0;i<insert;i++)
		{
			int[] dims = new int[tree.dimensions*2];
			int index = 0;
			for(int j=0;j<tree.dimensions;j++)
			{
				int l = r.nextInt();
				int h = r.nextInt();
				if(l>h)
				{
					int temp = l;
					l = h;
					h = temp;
				}
				dims[index++] = l;dims[index++] = h;
			}
			Rectangle rec = new Rectangle(tree.dimensions, dims);
			inserters.add(new Inserter(rec, tree));
		}
		
		ArrayList<Searcher> searchers = new ArrayList<Searcher>();
		for(int i=0;i<search;i++)
		{
			int[] dims = new int[tree.dimensions*2];
			int index = 0;
			for(int j=0;j<tree.dimensions;j++)
			{
				int l = r.nextInt();
				int h = r.nextInt();
				if(l>h)
				{
					int temp = l;
					l = h;
					h = temp;
				}
				dims[index++] = l;dims[index++] = h;
			}
			Rectangle rec = new Rectangle(tree.dimensions, dims);
			searchers.add(new Searcher(rec, tree));
		}
		
		ArrayList<Deleter> deleters = new ArrayList<Deleter>();
		for(int i=0;i<delete;i++)
		{
			int[] dims = new int[tree.dimensions*2];
			int index = 0;
			for(int j=0;j<tree.dimensions;j++)
			{
				int l = r.nextInt();
				int h = r.nextInt();
				if(l>h)
				{
					int temp = l;
					l = h;
					h = temp;
				}
				dims[index++] = l;dims[index++] = h;
			}
			Rectangle rec = new Rectangle(tree.dimensions, dims);
			deleters.add(new Deleter(rec, tree));
		}
		
		ExecutorService ex = Executors.newCachedThreadPool();
		for(int i=0;i<inserters.size();i++)
			ex.submit(inserters.get(i));
		for(int i=0;i<searchers.size();i++)
			ex.submit(searchers.get(i));
		for(int i=0;i<deleters.size();i++)
			ex.submit(deleters.get(i));
		ex.shutdown();
		try {
			ex.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		long timeSpent = endTime - startTime;
		System.out.println(insert + " insertions, " + search + " searchs, " + delete + " deletions.");
		System.out.println("Time spent: " + timeSpent + " milli seconds");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n \n \n");
	}

}
