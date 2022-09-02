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

public class SeekController extends Controller{
    public GameObject target;
    
    public SeekController(GameObject _target) {
		target = _target;
	}
    
    public Vector seekVector(GameObject subject) {
    	Vector seek_car = new Vector(subject.getX(), subject.getY());
	    Vector target_car = new Vector(target.getX(), target.getY());
	    
	    seek_car = target_car.subtract(seek_car);
	    seek_car = seek_car.normalize();
	    seek_car = seek_car.multiply(MAX_ACCELERATION);
	    
	    return seek_car;
    }
	
	public void update(Car subject, Game game, double delta_t, double controlVariables[]) {
	    Vector seek_vector = seekVector(subject);
	    OutputFiltering output_filtering = new OutputFiltering(subject);
	    
	    controlVariables[VARIABLE_STEERING] = output_filtering.calculateSteering(seek_vector);
	    
	    if (output_filtering.calculateThrottle(seek_vector) == true) {
	    	controlVariables[VARIABLE_THROTTLE] = 1;
	    }
	    
	    else if (output_filtering.calculateThrottle(seek_vector) == false) {
	    	controlVariables[VARIABLE_BRAKE] = 1;
	    }
	}
}
