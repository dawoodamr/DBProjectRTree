package rtreepack;

public class Deleter extends Thread 
{
	RTree tree;
	Rectangle rec;
	
	public Deleter(Rectangle rec, RTree tree)
	{
		this.rec = rec;
		this.tree = tree;
		//this.start();
	}
	
	public void run()
	{
		tree.delete(rec);
	}
}
