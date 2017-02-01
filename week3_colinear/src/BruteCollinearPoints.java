import java.lang.reflect.Array;
import java.util.Arrays;

public class BruteCollinearPoints {
	
	LineSegment [] als = new LineSegment[1];
	int aLen = 0;

	public BruteCollinearPoints(Point[] points) // finds all line segments
												// containing 4 points
	{
		
		if ( points.length < 4 )
			return;
		
		for (int i = 0; i < points.length - 3; i++) {
			for (int j = i + 1; j < points.length - 2; j++) {
				for (int k = j + 1; k < points.length - 1; k++) {
					for (int l = k + 1; l < points.length; l++) {
						System.out.println("i=" + i + " j= " + j + " k=" + k + " l=" + l);

						this.addLine(points[i], points[j], points[k], points[l]);
					}
				}
			}
		}

	}

	private void addLine( Point p1, Point p2, Point p3, Point p4 ){

		double s12 = p1.slopeTo(p2);
		double s13 = p1.slopeTo(p3);
		
		if ( Double.NEGATIVE_INFINITY == s12 || Double.NEGATIVE_INFINITY == s13)
			throw new java.lang.IllegalArgumentException( "Duplicate Points" );
		
		if ( p2.equals(p3) ||p3.equals( p4) || p2.equals(p4) )
			throw new java.lang.IllegalArgumentException( "Duplicate Points" );


		if ( s12 != s13 ) return;
		
		double s14  = p1.slopeTo(p4);
		if ( Double.NEGATIVE_INFINITY == s14)
			throw new java.lang.IllegalArgumentException( "Duplicate Point 2" );
		
		if ( s12 == s14){
			
			LineSegment ls = new LineSegment(p1, p2);
			
			if (aLen == als.length) {
				als = java.util.Arrays.copyOf( als ,  als.length*2);
			}
			als[aLen] =ls;
			aLen ++;
		}
		
	}
	
	public int numberOfSegments() // the number of line segments
	{
		
		return this.aLen;
	}

	public LineSegment[] segments() {

		return Arrays.copyOf( als, aLen );
	}
}