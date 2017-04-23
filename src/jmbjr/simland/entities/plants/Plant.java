package jmbjr.simland.entities.plants;

import java.awt.Point;
import java.awt.image.BufferedImage;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;
import jmbjr.simland.entities.animals.Worm;
import jmbjr.simland.entities.animals.ability.Ager;
import jmbjr.simland.entities.animals.ability.Disappearer;
import jmbjr.simland.entities.animals.ability.Grower;
import jmbjr.simland.entities.animals.ability.Tunneller;
import jmbjr.simland.entities.drawlayer.AnimalLayer;
import jmbjr.simland.entities.drawlayer.GroundLayer;
import jmbjr.simland.entities.drawlayer.PlantLayer;

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
	BufferedImage[] getImage();
    
    @SetAttribute
    void setImage(BufferedImage[] image);
    
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
