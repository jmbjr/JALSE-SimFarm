package jmbjr.simland.entities;

import java.awt.Point;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

public interface Animal extends Entity {

	@GetAttribute
	int getHealth();
	
	@GetAttribute
	int getSize();
	
	@GetAttribute
    double getAngle();

    @GetAttribute
    Point getPosition();

    @GetAttribute
    int getSightRange();

    @GetAttribute
    double getSpeed();

    @SetAttribute
    void setHealth(int health);
    
    @SetAttribute
    void setSize(int size);

    @SetAttribute
    void setAngle(double angle);

    @SetAttribute
    void setPosition(Point position);

    @SetAttribute
    void setSightRange(int range);

    @SetAttribute
    void setSpeed(double speed);

}