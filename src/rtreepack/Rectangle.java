package rtreepack;

import java.util.ArrayList;

public class Rectangle 
{
	final int dimensions;
	
	protected ArrayList<Interval> intervals;
	
	public Rectangle(int dimensions)
	{
		this.dimensions = dimensions;
		intervals = new ArrayList<Interval>();
		for(int i=0;i<dimensions;i++)
		{
			intervals.add(new Interval());
		}
	}

	public String toString() 
	{
		return "Rectangle [dimensions=" + dimensions + ", intervals="
				+ intervals + "]";
	}
	
}
