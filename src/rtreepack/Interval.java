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
}
