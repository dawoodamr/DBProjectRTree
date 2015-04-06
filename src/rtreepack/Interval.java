package rtreepack;

public class Interval 
{
	int Low, High;

	public Interval(int low, int high) 
	{
		Low = low;
		High = high;
	}

	public String toString() 
	{
		return "Interval [Low=" + Low + ", High=" + High + "]";
	}

	public Interval Clone() 
	{
		return new Interval(Low, High);
	}
	
	public boolean equals(Interval v)
	{
		return this.Low==v.Low && this.High==v.High;
	}
	
	public boolean intersecting(Interval v)
	{
		return (this.Low>=v.Low && v.High>= this.Low) ||
				(this.Low<=v.Low && v.High<=this.High) ||
				(this.High>=v.Low && v.High>=this.High) ||
				(v.Low>=this.Low && this.High>= v.Low) ||
				(v.Low<=this.Low && this.High<=v.High) ||
				(v.High>=this.Low && this.High>=v.High);
	}
}
