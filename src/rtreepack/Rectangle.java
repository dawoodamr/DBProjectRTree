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
	
	/**
	 * @param rec the new Rectangle to be contained
	 * @return the space that the Rectangle needs to be enlarged with in order to contain rec
	 */
	public int enlargement(Rectangle rec)
	{
		int[] diffs = new int[this.dimensions];
		for(int i=0;i<this.dimensions;i++)
		{
			int diff = 0;
			if(this.intervals.get(i).Low>rec.intervals.get(i).Low)
				diff += this.intervals.get(i).Low - rec.intervals.get(i).Low;
			if(this.intervals.get(i).High<rec.intervals.get(i).High)
				diff += rec.intervals.get(i).High - this.intervals.get(i).High;
			diffs[i] = diff;
		}
		int enlargement =  0;
		for(int i=0;i<diffs.length;i++)
		{
			if(diffs[i]>0)
			{
				if(enlargement == 0)
					enlargement++;
				enlargement *= diffs[i];
			}
		}
		
		return enlargement;
	}
	
	public int getArea()
	{
		int area = 0;
		
		for(int i=0;i<dimensions;i++)
		{
			Interval v = intervals.get(i);
			int width = v.High - v.Low;
			if(width > 0)
			{
				if(area == 0)
					area++;
				area *= width;
			}
		}
		
		return area;
	}
	
	public String toString() 
	{
		return "Rectangle [dimensions=" + dimensions + ", intervals="
				+ intervals + "]";
	}
	
}