package jmbjr.simland.entities;

import java.awt.Point;
import java.util.UUID;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * field class for main farm area
 */
public interface FarmObject extends Entity {

    UUID ID = UUID.randomUUID();

	@GetAttribute
	int getAge();

	@GetAttribute
	int getSize();
	
	@GetAttribute
    double getAngle();

    @GetAttribute
    Point getPosition();
    
    @GetAttribute
    String getName();
    
    @GetAttribute
    int getSightRange();

    @GetAttribute
    double getSpeed();
    
    @SetAttribute
    void setAge(int age);
    
    @SetAttribute
    void setSize(int size);

    @SetAttribute
    void setAngle(double angle);

    @SetAttribute
    void setPosition(Point position);

    @SetAttribute
    void setName(String name);
    
    @SetAttribute
    void setSightRange(int range);

    @SetAttribute
    void setSpeed(double speed);

    @GetAttribute
	boolean getVisibility();

    @SetAttribute
	void setVisibility(boolean b);

    @GetAttribute
    Class<? extends Entity> getSpecies();
    
    @SetAttribute
    void setSpecies( Class<? extends Entity> type);
  
}