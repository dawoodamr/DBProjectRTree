package rtreepack;

import java.util.*;

public class Builder 
{
	RTree tree;
	int insert;
	int search;
	int delete;
	boolean saveBatch;
	String saveFileName;
	static int builderNum = 0;
	
	public Builder(RTree tree, int insert, int search, int delete, boolean saveBatch, String saveFileName)
	{
		this.tree = tree;
		this.insert = insert;
		this.search = search;
		this.delete = delete;
		this.saveBatch = saveBatch;
		this.saveFileName = saveFileName;
		Builder.builderNum++;
		this.run();
	}
	
	public void run()
	{
		int range = 500;
		
		int batch = 10000;
		
		if(saveBatch)
		{
			System.out.println("Builder " + Builder.builderNum + " " + 
					(saveBatch?"batch-saving ("+batch+")":"Xact-saving") + " RTree dims: " + tree.dimensions + " node size: " + tree.M + " :\n");
			long startTime = System.currentTimeMillis();
			
			Random r = new Random();
			
			//ArrayList<Inserter> inserters = new ArrayList<Inserter>();
			for(int i=0;i<insert;i++)
			{
				int[] dims = new int[tree.dimensions*2];
				int index = 0;
				for(int j=0;j<tree.dimensions;j++)
				{
					int l = r.nextInt()%range;
					int h = r.nextInt()%range;
					if(l>h)
					{
						int temp = l;
						l = h;
						h = temp;
					}
					dims[index++] = l;dims[index++] = h;
				}
				Rectangle rec = new Rectangle(tree.dimensions, dims);
				tree.insert(rec);
				if((i+1)%batch == 0)
					tree.save(saveFileName);
				//inserters.add(new Inserter(rec, tree));
			}
			
			//ArrayList<Searcher> searchers = new ArrayList<Searcher>();
			for(int i=0;i<search;i++)
			{
				int[] dims = new int[tree.dimensions*2];
				int index = 0;
				for(int j=0;j<tree.dimensions;j++)
				{
					int l = r.nextInt()%range;
					int h = r.nextInt()%range;
					if(l>h)
					{
						int temp = l;
						l = h;
						h = temp;
					}
					dims[index++] = l;dims[index++] = h;
				}
				Rectangle rec = new Rectangle(tree.dimensions, dims);
				tree.search(rec);
				//searchers.add(new Searcher(rec, tree));
			}
			
			//ArrayList<Deleter> deleters = new ArrayList<Deleter>();
			for(int i=0;i<delete;i++)
			{
				int[] dims = new int[tree.dimensions*2];
				int index = 0;
				for(int j=0;j<tree.dimensions;j++)
				{
					int l = r.nextInt()%range;
					int h = r.nextInt()%range;
					if(l>h)
					{
						int temp = l;
						l = h;
						h = temp;
					}
					dims[index++] = l;dims[index++] = h;
				}
				Rectangle rec = new Rectangle(tree.dimensions, dims);
				tree.delete(rec);
				if((i+1)%batch == 0)
					tree.save(saveFileName);
				//deleters.add(new Deleter(rec, tree));
			}
			
			tree.save(saveFileName);
			
			long endTime = System.currentTimeMillis();
			long timeSpent = endTime - startTime;
			System.out.println(insert + " insertions, " + search + " searchs, " + delete + " deletions.");
			System.out.println("Time spent: " + timeSpent + " milli seconds");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
					+ "throughput = " + (((float)(insert+search+delete))/(float)timeSpent)*1000 + " ops/second\n \n \n");
		}else{
			
			/* **************************************************************************
			 * * **************************************************************************
			 * * **************************************************************************
			 * * **************************************************************************
			 * * **************************************************************************
			 * * **************************************************************************
			 */
			
			System.out.println("Builder " + Builder.builderNum + " " + 
					(saveBatch?"batch-saving ("+batch+")":"Xact-saving") + " RTree dims: " + tree.dimensions + " node size: " + tree.M + " :\n");
			long startTime = System.currentTimeMillis();
			
			Random r = new Random();
			
			//ArrayList<Inserter> inserters = new ArrayList<Inserter>();
			for(int i=0;i<insert;i++)
			{
				int[] dims = new int[tree.dimensions*2];
				int index = 0;
				for(int j=0;j<tree.dimensions;j++)
				{
					int l = r.nextInt()%range;
					int h = r.nextInt()%range;
					if(l>h)
					{
						int temp = l;
						l = h;
						h = temp;
					}
					dims[index++] = l;dims[index++] = h;
				}
				Rectangle rec = new Rectangle(tree.dimensions, dims);
				tree.insert(rec);
				tree.save(saveFileName);
				//inserters.add(new Inserter(rec, tree));
			}
			
			//ArrayList<Searcher> searchers = new ArrayList<Searcher>();
			for(int i=0;i<search;i++)
			{
				int[] dims = new int[tree.dimensions*2];
				int index = 0;
				for(int j=0;j<tree.dimensions;j++)
				{
					int l = r.nextInt()%range;
					int h = r.nextInt()%range;
					if(l>h)
					{
						int temp = l;
						l = h;
						h = temp;
					}
					dims[index++] = l;dims[index++] = h;
				}
				Rectangle rec = new Rectangle(tree.dimensions, dims);
				tree.search(rec);
				//searchers.add(new Searcher(rec, tree));
			}
			
			//ArrayList<Deleter> deleters = new ArrayList<Deleter>();
			for(int i=0;i<delete;i++)
			{
				int[] dims = new int[tree.dimensions*2];
				int index = 0;
				for(int j=0;j<tree.dimensions;j++)
				{
					int l = r.nextInt()%range;
					int h = r.nextInt()%range;
					if(l>h)
					{
						int temp = l;
						l = h;
						h = temp;
					}
					dims[index++] = l;dims[index++] = h;
				}
				Rectangle rec = new Rectangle(tree.dimensions, dims);
				tree.delete(rec);
				tree.save(saveFileName);
				//deleters.add(new Deleter(rec, tree));
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
