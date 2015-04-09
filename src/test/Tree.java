package test;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Tree 
{
	SimpleNode root;
	int size;
	WriteLock w = new ReentrantReadWriteLock().writeLock();
	
	public Tree()
	{
		size = 0;
	}
	
	public void insert(int v)
	{
		if(root == null)
		{
			root = new SimpleNode(v);
			size++;
			return;
		}
		SimpleNode last = root;
		SimpleNode temp = last.next;
		last.rwl.readLock().lock();	
		while(temp != null)
		{
			last.rwl.readLock().unlock();
			last = temp;
			last.rwl.readLock().lock();
			temp = temp.next;
		}
		last.rwl.readLock().unlock();
		last.rwl.writeLock().lock();
		last.next = new SimpleNode(v);
		size++;
		last.rwl.writeLock().unlock();
	}
	
	public SimpleNode search(int x)
	{
		SimpleNode res = root;
		if(root != null)
			res.rwl.readLock().lock();
		while(res != null && res.value != x)
		{
			res.rwl.readLock().unlock();
			res = res.next;
			if(res != null)
				res.rwl.readLock().lock();
		}
		return res;
	}
	
	public static void main(String[] args) 
	{
		Tree t = new Tree();
		new InsertThread(t, 3);
		new InsertThread(t, 2);
		new SearchThread(t, 34);
		new InsertThread(t, 34);
		new InsertThread(t, 43);
		new InsertThread(t, 54);
		new SearchThread(t, 34);
		new InsertThread(t, 26);
		new InsertThread(t, 5);
		new SearchThread(t, 34);
		new InsertThread(t, 74);
		new InsertThread(t, 64);
		new SearchThread(t, 74);
		new InsertThread(t, 33);
		new InsertThread(t, 53);
		new InsertThread(t, 36);
		new SearchThread(t, 3);
		new SearchThread(t, 34);
		new SearchThread(t, 443);
	//	while(t.size<12){}
		System.out.println("finally: "+t.root);
		
	}

}
