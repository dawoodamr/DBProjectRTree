package test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimpleNode 
{
	SimpleNode next;
	int value;
	ReentrantReadWriteLock rwl;
	
	
	public SimpleNode(int v) 
	{
		rwl = new ReentrantReadWriteLock();
		value = v;
	}


	@Override
	public String toString() {
		return "SimpleNode [next=" + next + ", value=" + value + "]";
	}
	
	

}
