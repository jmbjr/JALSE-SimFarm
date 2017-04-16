package jmbjr.simland.entities;

import java.awt.Point;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

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
