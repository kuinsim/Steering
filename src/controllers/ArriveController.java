package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.Vector;
import engine.OutputFiltering;

/*
 * Suin Kim
 * CS387-801
 * Project 1
 */

public class ArriveController extends Controller{
    private GameObject target;
    private double target_radius;
    private double slow_radius;
    private double target_speed;
    
    public ArriveController(GameObject _target) {
		target = _target;
		target_radius = 0.5;
		slow_radius = 300;
		target_speed = 0;
	}
    
    public Vector arriveVector(Car subject, double delta_t) {
    	Vector a = new Vector(subject.getX(), subject.getY());
		Vector d = new Vector(subject.getX(), subject.getY());
		Vector e = new Vector(target.getX(), target.getY());
		Vector character = new Vector(subject.getX(),  subject.getY());
		Vector target_velocity = new Vector(subject.getX(), subject.getY());
		
		d = e.subtract(character);
		
		double distance = d.magnitude();
		
		if (distance < target_radius) {
			return new Vector(0, 0);
		} 
		
		else if (distance > slow_radius) {
			target_speed = subject.getMaxSpeed();
		}
		
		else {
			target_speed = subject.getMaxSpeed() * (distance/slow_radius);
		}
		
		target_velocity = d.normalize();
		target_velocity = target_velocity.multiply(target_speed);
		
		Vector character_velocity = new Vector(Math.cos(subject.getAngle()), Math.sin(subject.getAngle()));
		
		character_velocity = character_velocity.normalize();
		character_velocity = character_velocity.multiply(subject.getSpeed());

		a = target_velocity.subtract(character_velocity).multiply((1/delta_t));
		
		if (a.magnitude() > MAX_ACCELERATION) {
			a = a.normalize();
			a = a.multiply(MAX_ACCELERATION);
		}
		
		return a;
	}
    
    public void update(Car subject, Game game, double delta_t, double controlVariables[]) {
    	Vector arrive_vector = arriveVector(subject, delta_t);
	    OutputFiltering output_filtering = new OutputFiltering(subject);
	    
	    controlVariables[VARIABLE_STEERING] = output_filtering.calculateSteering(arrive_vector);
	    
	    if (output_filtering.calculateThrottle(arrive_vector) == true) {
	    	controlVariables[VARIABLE_THROTTLE] = 1;
	    }
	    
	    else if (output_filtering.calculateThrottle(arrive_vector) == false) {
	    	controlVariables[VARIABLE_BRAKE] = 1;
	    }
	}
}
