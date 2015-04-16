package rtreepack;

import java.util.*;
import java.util.concurrent.*;

public class Monitor extends Thread 
{
	RTree tree;
	int insert;
	int search;
	int delete;
	boolean saveBatch;
	String saveFileName;
	static int monitorNum = 0;
	
	public Monitor(RTree tree, int insert, int search, int delete, boolean saveBatch, String saveFileName)
	{
		this.tree = tree;
		this.insert = insert;
		this.search = search;
		this.delete = delete;
		this.saveBatch = saveBatch;
		this.saveFileName = saveFileName;
		Monitor.monitorNum++;
		this.start();
	}
	
	public void run()
	{
		if(saveBatch)
		{
			System.out.println("Monitor " + Monitor.monitorNum + " " + 
					(saveBatch?"batch-saving":"Xact-saving") + ":\n");
			long startTime = System.currentTimeMillis();
			
			
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
				ex.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			tree.save(saveFileName);
			
			long endTime = System.currentTimeMillis();
			long timeSpent = endTime - startTime;
			System.out.println(insert + " insertions, " + search + " searchs, " + delete + " deletions.");
			System.out.println("Time spent: " + timeSpent + " milli seconds");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
					+ "throughput = " + (((float)(insert+search+delete))/(float)timeSpent)*1000 + " ops/second\n \n \n");
		}else{
			
			/* ************************************************************************
			 * *************************************************************************
			 * *************************************************************************
			 * *************************************************************************
			 */
			
			System.out.println("Monitor " + Monitor.monitorNum + " " + 
					(saveBatch?"batch-saving":"Xact-saving") + ":\n");
			long startTime = System.currentTimeMillis();
			
			Random r = new Random();
			
			ArrayList<InsertSave> inserters = new ArrayList<InsertSave>();
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
				inserters.add(new InsertSave(rec, tree, saveFileName));
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
			
			ArrayList<DeleteSave> deleters = new ArrayList<DeleteSave>();
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
				deleters.add(new DeleteSave(rec, tree, saveFileName));
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
				ex.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			long endTime = System.currentTimeMillis();
			long timeSpent = endTime - startTime;
			System.out.println(insert + " insertions, " + search + " searchs, " + delete + " deletions.");
			System.out.println("Time spent: " + timeSpent + " milli seconds");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
					+ "throughput = " + (((float)(insert+search+delete))/(float)timeSpent)*1000 + " ops/second\n \n \n");
		}
	}

}
