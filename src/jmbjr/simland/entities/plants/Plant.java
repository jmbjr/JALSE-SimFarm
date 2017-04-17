package jmbjr.simland.entities.plants;

import java.awt.Point;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * generic Plant entity
 */
public interface Plant extends Entity {
	
    @GetAttribute
    Point getPosition();

    @SetAttribute
    void setPosition(Point p);
 
    
    @GetAttribute
    int getSize();

    @SetAttribute
    void setSize(int size);

    @GetAttribute
    int getAge();

    @SetAttribute
    void setAge(int age);
    
    @GetAttribute
    String getName();

    @SetAttribute
    void setName(String name);
}
