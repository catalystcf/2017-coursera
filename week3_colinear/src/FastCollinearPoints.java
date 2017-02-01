import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;

public class FastCollinearPoints {

	private Stack<LineSegment> segments;

	public FastCollinearPoints(Point[] points) // finds all line segments
												// containing 4 or more points
	{
		this.segments = new Stack<LineSegment>();
		for (int i = 0; i < points.length; i++) {
			this._findLine(points, i);
		}

	}

	/**
	 * if this does not work, why not swap the selected element with first and
	 * sort 2nd to end?
	 */
	private void _findLine(Point[] points, int i) {

		Point pivot = points[i];

		Point[] pointNoPivot = new Point[points.length - 1];
		int ix = 0;
		for (int ci = 0; ci < points.length; ci++) {
			if (i == ci)
				continue;

			pointNoPivot[ix] = points[ci];
			ix++;
		}

		Arrays.sort(pointNoPivot, pivot.slopeOrder());
		double runningSlope = Double.NEGATIVE_INFINITY;

		int idx = 0; // walk
		int cnt = 0; // count of points with the same slope

		Stack<Point> linePoints = null;

		while (idx < pointNoPivot.length) {

			double pNextSlope = pivot.slopeTo(pointNoPivot[idx]);
			if (pNextSlope != runningSlope && pNextSlope != Double.NEGATIVE_INFINITY) {
				cnt = 0;
				runningSlope = pNextSlope;

				if (null != linePoints) {
					_createSegment(linePoints, pivot);
					linePoints = null;
				}
			}
			// we matched to previous spot
			cnt++;
			if (cnt == 3) {
				// time to add a segment.. we clearly have it..
				linePoints = new Stack<Point>();
				linePoints.push(pivot);
				linePoints.push(pointNoPivot[idx - 2]);
				linePoints.push(pointNoPivot[idx - 1]);
				linePoints.push(pointNoPivot[idx - 0]);
			} else if (cnt > 3) {
				linePoints.push(pointNoPivot[idx]);
			}

			idx++;
		}
		if (null != linePoints)
			_createSegment(linePoints, pivot);

	}

	private void _createSegment(Stack<Point> points, Point pivot) {

		Point[] ps = new Point[points.size()];
		int i = 0;

		for (Point p : points) {
			ps[i++] = p;
		}

		Arrays.sort(ps);
		
		if (ps[0].equals(pivot))
			this.segments.push(new LineSegment(ps[0], ps[ps.length - 1]));
	}

	public int numberOfSegments() // the number of line segments
	{
		return this.segments.size();
	}

	public LineSegment[] segments() // the line segments
	{

		LineSegment[] lss = new LineSegment[this.segments.size()];
		int i = 0;
		for (LineSegment ls : segments) {
			lss[i] = ls;
			i++;
		}

		return lss;
	}
}