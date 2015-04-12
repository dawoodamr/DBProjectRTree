package rtreepack;

import java.io.*;

public class InsertSave extends Thread 
{
	RTree tree;
	Rectangle rec;
	
	public InsertSave(Rectangle rec, RTree tree)
	{
		this.tree = tree;
		this.rec = rec;
		this.start();
	}
	
	public void run()
	{
		tree.insert(rec);
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
