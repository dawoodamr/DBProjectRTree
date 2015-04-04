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
		root = new InternalNode(m, M, lsnCount++, new Rectangle(dimensions, 0, 0, 0, 0));
	}
	
	
	public static void main(String[] args)
	{
		RTree tree = new RTree(1, 3, 2);
		tree.root.entries.add(new Entry(
				new InternalNode(tree.m, tree.M, lsnCount, 
						new Rectangle(tree.dimensions, 3, 4, 2, 6)), lsnCount++));
		Rectangle c = new Rectangle(3, 3,6,3,6,3,6);
		Rectangle r = new Rectangle(3, 0,2,2,7,1,8);
		System.out.println("Hello RTree: "+tree.toString());
		System.out.println(c.getArea()+"  "+r.getArea());
	}

	public String toString() 
	{
		return "RTree [m=" + m + ", M=" + M + ", dimensions=" + dimensions
				+ ", root=" + root + "]";
	}

}