package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.Obstacle;
import engine.Vector;
import engine.OutputFiltering;
import engine.RotatedRectangle;

/*
 * Suin Kim
 * CS387-801
 * Project 1
 */

public class WallAvoidanceController extends SeekController {
	private double move_length;
	private double new_x;
	private double new_y;
	boolean left;
	boolean right;
	boolean front;
    
    public WallAvoidanceController(GameObject _target) {
		super(_target);
		target = _target;
	}
    
    public Vector turnVector(Car subject, double x, double y) {
    	Vector turn_car = new Vector(subject.getX(), subject.getY());
	    Vector target_car = new Vector(x, y);
	    
	    turn_car = target_car.subtract(turn_car);
	    turn_car = turn_car.normalize();
	    turn_car = turn_car.multiply(MAX_ACCELERATION);
	    
	    return turn_car;
    }
    
    public boolean collisionRectangle(Car subject, Game game, double angle, double distance)
    {
    	double rect_x = subject.getX() + Math.cos(angle) * distance;
    	double rect_y = subject.getY() + Math.sin(angle) * distance;
    	
    	RotatedRectangle rectangle = new RotatedRectangle(rect_x, rect_y, subject.getImgWidth() * 1.0, subject.getImgHeight() * 1.0, angle);
    	
    	for (GameObject game_object: game.getObjects()) {
    		if ((game_object instanceof Obstacle) && (RotatedRectangle.RotRectsCollision(rectangle,  game_object.getCollisionBox()))) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public void rayCasting(Car subject, Game game) {
    	left = false;
    	right = false;
    	front = false;
    	double cast_length = 30;
    	move_length = cast_length;
    	
    	for (int i = 1; i < cast_length; i++) {
    		left = collisionRectangle(subject, game, subject.getAngle() - Math.PI/4, i);
    		
			if (left) {
				move_length = i;
    			break;
			}
    	}
    	
    	for (int i = 1; i < cast_length; i++) {
    		right = collisionRectangle(subject, game, subject.getAngle() + Math.PI/4, i);
    		
    		if (right) {
    			move_length = i;
    			break;
    		}
    	}
    	
    	for (int i = 1; i < cast_length; i++) {
    		front = collisionRectangle(subject, game, subject.getAngle(), i);
    		
			if (front) {
				move_length = i;
    			break;
			}
    	}
    }
	
	public void update(Car subject, Game game, double delta_t, double controlVariables[]) {
		rayCasting(subject, game);
		
		if (left) {
			new_x = subject.getX() + Math.cos(subject.getAngle() + Math.PI/4) * move_length;
			new_y = subject.getY() + Math.sin(subject.getAngle() + Math.PI/4) * move_length;
			
			Vector turn_vector = turnVector(subject, new_x, new_y);
			
			OutputFiltering output_filtering = new OutputFiltering(subject);
		    
		    controlVariables[VARIABLE_STEERING] = output_filtering.calculateSteering(turn_vector);
		    
		    if (output_filtering.calculateThrottle(turn_vector) == true) {
		    	controlVariables[VARIABLE_THROTTLE] = 1;
		    }
		    
		    else if (output_filtering.calculateThrottle(turn_vector) == false) {
		    	controlVariables[VARIABLE_BRAKE] = 1;
		    }
		}
		
		else if (right) {
			new_x = subject.getX() + Math.cos(subject.getAngle() - Math.PI/4) * move_length;
			new_y = subject.getY() + Math.sin(subject.getAngle() - Math.PI/4) * move_length;
			
			Vector turn_vector = turnVector(subject, new_x, new_y);
			
			OutputFiltering output_filtering = new OutputFiltering(subject);
		    
		    controlVariables[VARIABLE_STEERING] = output_filtering.calculateSteering(turn_vector);
		    
		    if (output_filtering.calculateThrottle(turn_vector) == true) {
		    	controlVariables[VARIABLE_THROTTLE] = 1;
		    }
		    
		    else if (output_filtering.calculateThrottle(turn_vector) == false) {
		    	controlVariables[VARIABLE_BRAKE] = 1;
		    }
		}
		
		else if (front) {
			new_x = subject.getX() + Math.cos(subject.getAngle()) * -move_length;
			new_y = subject.getY() + Math.sin(subject.getAngle()) * -move_length;
			
			Vector turn_vector = turnVector(subject, new_x, new_y);
			
			OutputFiltering output_filtering = new OutputFiltering(subject);
		    
		    controlVariables[VARIABLE_STEERING] = output_filtering.calculateSteering(turn_vector);
		    
		    if (output_filtering.calculateThrottle(turn_vector) == true) {
		    	controlVariables[VARIABLE_THROTTLE] = 1;
		    }
		    
		    else if (output_filtering.calculateThrottle(turn_vector) == false) {
		    	controlVariables[VARIABLE_BRAKE] = 1;
		    }
		}
		
		else {
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
}
