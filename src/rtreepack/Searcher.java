package rtreepack;

import java.util.LinkedList;

public class Searcher extends Thread 
{
	RTree tree;
	Rectangle rec;
	LinkedList<LeafNode> result;
	
	public Searcher(Rectangle rec, LinkedList<LeafNode> result, RTree tree)
	{
		this.tree = tree;
		this.rec = rec;
		this.result = result;
		this.start();
	}
	
	public void run()
	{
		LinkedList<LeafNode> r = tree.search(rec);
		for(int i=0;i<r.size();i++)
		{
			result.add(r.get(i));
		}
	}

}
