package rtreepack;

public class RTree 
{
	int m, M;
	int dimensions;
	InternalNode root;
	static int lsnCount = 1;
	
	public RTree(int m, int M, int dimensions)
	{
		this.m = m;
		this.M = M;
		this.dimensions = dimensions;
		root = new InternalNode(m, M, lsnCount++, new Rectangle(dimensions));
	}
	
	public static void main(String[] args)
	{
		RTree tree = new RTree(1, 3, 2);
		System.out.println("Hello RTree: "+tree.toString());
	}

	public String toString() 
	{
		return "RTree [m=" + m + ", M=" + M + ", dimensions=" + dimensions
				+ ", root=" + root + "]";
	}

}
