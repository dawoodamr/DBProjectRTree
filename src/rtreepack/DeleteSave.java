package rtreepack;

import java.io.*;

public class DeleteSave extends Thread 
{
	RTree tree;
	Rectangle rec;
	
	public DeleteSave(Rectangle rec, RTree tree)
	{
		this.rec = rec;
		this.tree = tree;
		this.start();
	}
	
	public void run()
	{
		tree.delete(rec);
		try
		{
			FileOutputStream f = new FileOutputStream("RTree");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(tree);
			o.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
