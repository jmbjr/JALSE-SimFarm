package jmbjr.simland.entities.animals;

import java.awt.Point;
import java.awt.image.BufferedImage;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;
import jmbjr.simland.entities.animals.ability.Ager;
import jmbjr.simland.entities.animals.ability.Disappearer;
import jmbjr.simland.entities.animals.ability.Grower;
import jmbjr.simland.entities.animals.ability.Sleeper;
import jmbjr.simland.entities.animals.ability.Tunneller;
import jmbjr.simland.entities.animals.state.Tunnelling;
import jmbjr.simland.entities.drawlayer.AnimalLayer;
import jmbjr.simland.entities.drawlayer.GroundLayer;
import jmbjr.simland.properties.FarmAnimalProperties;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * generic animal entity
 */
public abstract interface Animal extends Entity {

	/**
	 * @param animal
	 * not sure if this is the correct way to do this
	 * want to force some subclasses to implement the method
	 * doesn't make sense for all of them though
	 */
	public static void checkAndSetType(Animal animal){};

	
	/**
	 * @param animal
	 * @param species
	 * this feels like a more centralized way to handle setting default types
	 * at least this is one function, but it will probably get huge as we add more animals
	 * 
	 */
	public static void markDefaultTypes(Animal animal, Class<? extends Animal> species) {
		if (species.equals(Cow.class)) {
			animal.markAsType(Ager.class);
			animal.markAsType(Grower.class);
			animal.markAsType(Sleeper.class);
			animal.markAsType(AnimalLayer.class);
			
			animal.setDrowsinessDelta(1);
			animal.setDrowsinessLimit(400);
			animal.setAlertnessDelta(-1);
			animal.setAlertnessLimit(50);
			
		} else if (species.equals(Pig.class)) {
			animal.markAsType(Ager.class);
			animal.markAsType(Grower.class);	
			animal.markAsType(Sleeper.class);
			animal.markAsType(AnimalLayer.class);
			
			animal.setDrowsinessDelta(2);
			animal.setDrowsinessLimit(450);
			animal.setAlertnessDelta(-2);
			animal.setAlertnessLimit(25);
						
		} else if (species.equals(Chicken.class)) {
			animal.markAsType(Ager.class);
			animal.markAsType(Grower.class);	
			animal.markAsType(Sleeper.class);
			animal.markAsType(AnimalLayer.class);
			
			animal.setDrowsinessDelta(1);
			animal.setDrowsinessLimit(450);
			animal.setAlertnessDelta(-1);
			animal.setAlertnessLimit(25);
			
		} else if (species.equals(Worm.class)) {
			animal.markAsType(Disappearer.class);
			animal.markAsType(Tunneller.class);
			animal.setVisibility(false);
			animal.markAsType(Tunnelling.class);
			animal.markAsType(GroundLayer.class);
		}
	}
	
	@GetAttribute
	int getAge();
	
	@GetAttribute
	int getDrowsiness();
	
	@GetAttribute
	int getSize();
	
	@GetAttribute
    double getAngle();
	
	@GetAttribute
	BufferedImage getImage();

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
    void setDrowsiness(int drowsiness);
    
    @SetAttribute
    void setSize(int size);

    @SetAttribute
    void setAngle(double angle);
    
    @SetAttribute
    void setImage(BufferedImage image);

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
	int getDrowsinessDelta();
    
    @SetAttribute
    void setDrowsinessDelta(int drowsinessDelta);

    @GetAttribute
	int getDrowsinessLimit();
    
    @SetAttribute
    void setDrowsinessLimit(int drowsinessLimit);   

    @GetAttribute
	int getAlertnessDelta();
    
    @SetAttribute
    void setAlertnessDelta(int alertnessDelta);

    @GetAttribute
	int getAlertnessLimit();
    
    @SetAttribute
    void setAlertnessLimit(int alertnessLimit);   
}