package rtreepack;

import java.util.*;

public class Builder 
{
	RTree tree;
	int insert;
	int search;
	int delete;
	static int builderNum = 0;
	
	public Builder(RTree tree, int insert, int search, int delete)
	{
		this.tree = tree;
		this.insert = insert;
		this.search = search;
		this.delete = delete;
		Builder.builderNum++;
		this.run();
	}
	
	public void run()
	{
		System.out.println("Builder " + Builder.builderNum + ":\n");
		long startTime = System.currentTimeMillis();
		
		Random r = new Random();
		
		//ArrayList<Inserter> inserters = new ArrayList<Inserter>();
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
			tree.insert(rec);
			//inserters.add(new Inserter(rec, tree));
		}
		
		//ArrayList<Searcher> searchers = new ArrayList<Searcher>();
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
			tree.delete(rec);
			//deleters.add(new Deleter(rec, tree));
		}
		
		long endTime = System.currentTimeMillis();
		long timeSpent = endTime - startTime;
		System.out.println(insert + " insertions, " + search + " searchs, " + delete + " deletions.");
		System.out.println("Time spent: " + timeSpent + " milli seconds");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n \n \n");
	}

}
