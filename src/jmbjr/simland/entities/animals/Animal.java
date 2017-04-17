package jmbjr.simland.entities.animals;

import java.awt.Point;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * generic animal entity
 */
public interface Animal extends Entity {

	@GetAttribute
	int getAge();
	
	@GetAttribute
	int getStamina();
	
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
    void setStamina(int stamina);
    
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

}