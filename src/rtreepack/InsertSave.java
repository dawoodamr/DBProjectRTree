package rtreepack;

public class InsertSave extends Thread 
{
	RTree tree;
	Rectangle rec;
	String saveFileName;
	
	public InsertSave(Rectangle rec, RTree tree, String saveFileName)
	{
		this.tree = tree;
		this.rec = rec;
		this.saveFileName = saveFileName;
		//this.start();
	}
	
	public void run()
	{
		tree.insert(rec);
		tree.save(saveFileName);
	}
}
