package rtreepack;

public class Inserter extends Thread 
{
	RTree tree;
	Rectangle rec;
	
	public Inserter(Rectangle rec, RTree tree)
	{
		this.tree = tree;
		this.rec = rec;
		this.start();
	}
	
	public void run()
	{
		tree.insert(rec);
	}

}
