package rtreepack;

public class DeleteSave extends Thread 
{
	RTree tree;
	Rectangle rec;
	String saveFileName;
	
	public DeleteSave(Rectangle rec, RTree tree, String saveFileName)
	{
		this.rec = rec;
		this.tree = tree;
		this.saveFileName = saveFileName;
		//this.start();
	}
	
	public void run()
	{
		tree.delete(rec);
		tree.save(saveFileName);
	}

}
