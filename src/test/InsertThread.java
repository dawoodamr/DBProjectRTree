package test;

public class InsertThread extends Thread 
{
	Tree t;
	int v;
	
	public InsertThread(Tree t, int v)
	{
		this.t = t;
		this.v = v;
		this.start();
	}
	
	public void run()
	{
		t.insert(v);
	}

}
