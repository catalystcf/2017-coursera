import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {

	Point p0_0 = new Point(0, 0 );
	Point p0_1 = new Point(0, 1 );
	Point p1_0 = new Point( 1, 0 );
	Point p1_1 = new Point( 1,1 );
	Point p2_2 = new Point( 2, 2 );
	
	@Test
	public void test_slopeTo() {
		
		
		//  treat the slope of a vertical line segment as positive infinity; 
		assertEquals( Double.POSITIVE_INFINITY , p0_0.slopeTo( p0_1 ),1e-5);
	
		
		// Treat the slope of a horizontal line segment as positive zero
		assertEquals(0.0 , p0_0.slopeTo(p1_0), 1e-5 );
		
	
	
		//  treat the
		// slope of a degenerate line segment (between a point and itself) as negative infinity; 
		assertEquals( Double.NEGATIVE_INFINITY,  p1_0.slopeTo(p1_0), 1e-5 ) ;
	
		
		assertEquals( 1, p1_1.slopeTo(p2_2), 1e-5);
	}
	
	@Test
	public void test_compareTO() {
		/* The compareTo() method should compare points by their y-coordinates,
		 *  breaking ties by their x-coordinates.
		 */
		
		assertEquals( 0, p0_0.compareTo(new Point( 0, 0)) );
		
		assertTrue( p0_0.compareTo( p0_1) < 0 );
		assertTrue( p0_0.compareTo( p1_1) < 0 );
		
		
		
	}

}
