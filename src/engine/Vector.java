package engine;

/*
 * Suin Kim
 * CS387-801
 * Project 1
 */

public class Vector {
	public double x;
	public double y;
	
	public Vector(double _x, double _y) {
	    x = _x;
	    y = _y;
	}
	
	public Vector subtract(Vector v2) {
	    Vector v = new Vector(x, y);

	    v.x = this.x - v2.x;
	    v.y = this.y - v2.y;
	    
	    return v;
	}
	
	public Vector multiply(double factor) {	    
	    Vector v = new Vector(x, y);

	    v.x = factor * this.x;
	    v.y = factor * this.y;
	    
	    return v;
	}
	
	public double dot(Vector v2) {
	    double sum = 0.0;
	    
	    sum = sum + (this.x*v2.x);
	    sum = sum + (this.y*v2.y);
	    
	    return sum;
	}
	
	public double magnitude() {
	    return Math.sqrt(this.dot(this));
	}
	
	public Vector normalize() {
		Vector v = new Vector(x, y);

		v.x = this.x / this.magnitude();
		v.y = this.y / this.magnitude();
		
		return v;
	}
}
