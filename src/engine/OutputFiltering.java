package engine;

/*
 * Suin Kim
 * CS387-801
 * Project 1
 */

public class OutputFiltering {
	private Car subject;
	
	public OutputFiltering(Car _subject) {
		subject = _subject;
	}
	
	public boolean calculateThrottle(Vector target) {
		Vector v = new Vector(Math.cos(subject.getAngle()), Math.sin(subject.getAngle()));
		
		return v.dot(target) > 0;
	}
	
	public double calculateSteering(Vector target) {
		Vector v = new Vector(Math.cos(subject.getAngle()), Math.sin(subject.getAngle()));
		Vector right = new Vector ((v.y * -1), v.x);
		
		return right.dot(target)/2;
	}
}
