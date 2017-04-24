package jmbjr.simland.entities.animals;

import java.awt.Point;
import java.awt.image.BufferedImage;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

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
    
    //I hate that I have to make these attributes since I feel like I shouldn't have to have these set for ALL animals
    //but I can't come up with any alternative that doesn't even further complicate setting animal properties
    @GetAttribute
	int getSightRangeChild();
    
    @SetAttribute
    void setSightRangeChild(int sightRangeChild);   

    @GetAttribute
	double getSpeedChild();
    
    @SetAttribute
    void setSpeedChild(double speed);   

    @GetAttribute
	int getSizeChild();
    
    @SetAttribute
    void setSizeChild(int sightRangeChild); 
    
    @GetAttribute
	int getSightRangeAdult();
    
    @SetAttribute
    void setSightRangeAdult(int sightRangeAdult);   

    @GetAttribute
	double getSpeedAdult();
    
    @SetAttribute
    void setSpeedAdult(double speed);   

    @GetAttribute
	int getSizeAdult();
    
    @SetAttribute
    void setSizeAdult(int sizeAdult);   

    @GetAttribute
	int getSizeMaturity();
    
    @SetAttribute
    void setSizeMaturity(int sizeMaturity); 
 
    @GetAttribute
	int getAgeMaturity();
    
    @SetAttribute
    void setAgeMaturity(int ageMaturity); 
    
	@GetAttribute
	BufferedImage getImageChild();

    @SetAttribute
    void setImageChild(BufferedImage image_child);

	@GetAttribute
	BufferedImage getImageAdult();

    @SetAttribute
    void setImageAdult(BufferedImage image_adult);
    
}