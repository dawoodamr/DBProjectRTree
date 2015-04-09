package test;

public class SearchThread extends Thread 
{
	Tree t;
	int v;
	
	public SearchThread(Tree t, int v)
	{
		this.t = t;
		this.v = v;
		this.start();
	}
	
	public void run()
	{
		System.out.println(t.search(v));
	}

}
