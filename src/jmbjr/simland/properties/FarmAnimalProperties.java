package jmbjr.simland.properties;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.ImageIO;

import jalse.entities.Entity;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;
import jmbjr.simland.entities.animals.Pig;
import jmbjr.simland.entities.animals.Worm;
import jmbjr.simland.entities.animals.ability.Ager;
import jmbjr.simland.entities.animals.ability.Disappearer;
import jmbjr.simland.entities.animals.ability.Grower;
import jmbjr.simland.entities.animals.ability.Sleeper;
import jmbjr.simland.entities.animals.ability.Tunneller;
import jmbjr.simland.entities.animals.age.Adult;
import jmbjr.simland.entities.animals.age.Child;
import jmbjr.simland.entities.animals.state.Awake;
import jmbjr.simland.entities.animals.state.Tunnelling;
import jmbjr.simland.entities.drawlayer.AnimalLayer;
import jmbjr.simland.entities.drawlayer.GroundLayer;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handles initializing and getting animal properties
 * for now, only child and adult properties are maintained, along with several constants
 */
public class FarmAnimalProperties {

    private static class AnimalProperties {

	private final AtomicInteger sightRange;
	private final AtomicLong speed;
	private final AtomicInteger drowsiness;
	private final AtomicInteger size;
	private final AtomicInteger age;
	private final BufferedImage image;
	

	AnimalProperties(final int sightRange, final double speed, final int stamina, final int size, final int age, final BufferedImage image) {
	    this.sightRange = new AtomicInteger(sightRange);
	    this.speed = new AtomicLong(Double.doubleToLongBits(speed));
	    this.drowsiness = new AtomicInteger(stamina);
	    this.size = new AtomicInteger(size);
	    this.age = new AtomicInteger(age);
	    this.image = image;
	}

    }
    
	public static void markDefaultTypes(Animal animal, Class<? extends Animal> species) {
		if (species.equals(Cow.class)) {
			animal.markAsType(Ager.class);
			animal.markAsType(Grower.class);
			animal.markAsType(Sleeper.class);
			animal.markAsType(AnimalLayer.class);
			animal.markAsType(Awake.class);
			
			animal.setDrowsinessDelta(1);
			animal.setDrowsinessLimit(400);
			animal.setAlertnessDelta(-1);
			animal.setAlertnessLimit(50);
			
		} else if (species.equals(Pig.class)) {
			animal.markAsType(Ager.class);
			animal.markAsType(Grower.class);	
			animal.markAsType(Sleeper.class);
			animal.markAsType(AnimalLayer.class);
			animal.markAsType(Awake.class);
			
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
	    
    //DROWSINESS
    private static final int DROWSINESS_MAX = 500;

    //SIZES
    private static final int SIZE_ADULT = 50;
    private static final int SIZE_COW = 50;
    private static final int SIZE_CHILD = 15;
    private static final int SIZE_WORM = 10;
    private static final int SIZE_CHICKEN = 25;
    private static final int SIZE_PIG = 30;
    
    
    //AGES
    private static final int AGE_ADULT = 100;
    private static final int AGE_CHILD = 0;
    private static final int AGE_ADULT_MIN = 50; 

    private static AtomicInteger population = new AtomicInteger(20);

    private static Map<Class<?>, AnimalProperties> props = new HashMap<>();

    static {
    BufferedImage imgCow = null;
    BufferedImage imgWorm = null;
    BufferedImage imgChicken = null;
    BufferedImage imgPig = null;
	try {
		imgCow = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\cow.png"));
		imgWorm = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\worm.png"));
		imgChicken = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\chicken.png"));
		imgPig = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\pig.png"));

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//for animal species
	props.put(Cow.class, new AnimalProperties( 75, 3.0,0, SIZE_COW, AGE_ADULT_MIN, imgCow));
	props.put(Worm.class, new AnimalProperties( 75, 4.0,0, SIZE_WORM, AGE_ADULT_MIN, imgWorm));
	props.put(Chicken.class, new AnimalProperties( 75, 4.0,0, SIZE_CHICKEN, AGE_ADULT_MIN, imgChicken));
	props.put(Pig.class, new AnimalProperties( 75, 4.0,0, SIZE_PIG, AGE_ADULT_MIN, imgPig));
	
	//for adults and children. 
	props.put(Adult.class, new AnimalProperties( 75, 3.0,0, SIZE_ADULT, AGE_ADULT_MIN, null));
	props.put(Child.class, new AnimalProperties( 1000, 10.0, 0, SIZE_CHILD, AGE_CHILD, null));
    }
   
    public static BufferedImage getImage(Class<? extends Entity> type) {
    	return props.get(type).image;
    }
    
	public static int getStamina(Class<? extends Entity> type) {
		return  props.get(type).drowsiness.get();
	}

	public static int getSize(Class<? extends Entity> type) {
		return  props.get(type).size.get();
	}

	public static int getAge(Class<? extends Entity> type) {
		return  props.get(type).age.get();
	}
	
    public static int getPopulation() {
	return population.get();
    }

    public static int getSightRange(final Class<? extends Entity> type) {
	return props.get(type).sightRange.get();
    }

    public static int getSizeAdult() {
	return SIZE_ADULT;
    }

    public static int getMinAgeAdult() {
    	return AGE_ADULT_MIN;
    }
    
    public static int getAgeAdult() {
    	return AGE_ADULT;
    }
        
    public static int getSizeChild() {
	return SIZE_CHILD;
    }

    public static double getSpeed(final Class<? extends Entity> type) {
	return Double.longBitsToDouble(props.get(type).speed.get());
    }


    public static void setPopulation(final int population) {
	FarmAnimalProperties.population.set(population);
    }


    public static void setSpeed(final Class<? extends Animal> type, final double speed) {
	props.get(type).speed.set(Double.doubleToLongBits(speed));
    }

    public static int getMaxDrowsiness() {
    	return DROWSINESS_MAX;
    }
    
}
