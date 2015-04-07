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
			if(lowHigh.length == 0)
				intervals.add(new Interval(0,0));
			else
			{
				int index = 2*i;
				intervals.add(new Interval(lowHigh[index],lowHigh[index+1]));
			}
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
		//return "Rectangle [dimensions=" + dimensions + ", intervals=" + intervals + "]";
		return intervals.toString();
	}

	public Rectangle Clone() 
	{
		ArrayList<Interval> temp = new ArrayList<Interval>();
		for(int i=0;i<dimensions;i++)
		{
			temp.add(intervals.get(i).Clone());
		}
		Rectangle result = new Rectangle(dimensions);
		result.intervals = temp;
		return result;
	}

	public boolean equals(Rectangle rec) 
	{
		if(this.dimensions != rec.dimensions)
			return false;
		
		for(int i=0;i<this.dimensions;i++)
		{
			boolean result = false;
			for(int j=0;j<rec.dimensions;j++)
			{
				if(this.intervals.get(i).equals(rec.intervals.get(j)))
				{
					result = true;
					break;
				}
			}
			if(! result)
				return false;
		}
		for(int i=0;i<rec.dimensions;i++)
		{
			boolean result = false;
			for(int j=0;j<this.dimensions;j++)
			{
				if(this.intervals.get(j).equals(rec.intervals.get(i)))
				{
					result = true;
					break;
				}
			}
			if(! result)
				return false;
		}
		
		return true;
	}
	
	public boolean intersecting(Rectangle rec)
	{
		for(int i=0;i<dimensions;i++)
		{
			if(! this.intervals.get(i).intersecting(rec.intervals.get(i)))
				return false;
		}
		
		return true;
	}
	
	public boolean contains(Rectangle rec)
	{
		for(int i=0;i<dimensions;i++)
		{
			if(! this.intervals.get(i).contains(rec.intervals.get(i)))
				return false;
		}
		
		return true;
	}
	
}
