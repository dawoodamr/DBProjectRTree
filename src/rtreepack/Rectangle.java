package rtreepack;

import java.util.ArrayList;

public class Rectangle 
{
	final int dimensions;
	
	protected ArrayList<Interval> intervals;
	
	public Rectangle(int dimensions, int... lowHigh)
	{
		this.dimensions = dimensions;
		intervals = new ArrayList<Interval>();
		for(int i=0;i<dimensions;i++)
		{
			int index = 2*i;
			intervals.add(new Interval(lowHigh[index],lowHigh[index+1]));
		}
	}

	public String toString() 
	{
		return "Rectangle [dimensions=" + dimensions + ", intervals="
				+ intervals + "]";
	}
	
}
