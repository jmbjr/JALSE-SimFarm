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
import jmbjr.simland.entities.animals.Worm;
import jmbjr.simland.entities.animals.age.Adult;
import jmbjr.simland.entities.animals.age.Child;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handles initializing and getting animal properties
 * for now, only child and adult properties are maintained, along with several constants
 */
public class FarmAnimalProperties {

    private static class AnimalProperties {

	private final AtomicInteger sightRange;
	private final AtomicLong speed;
	private final AtomicInteger stamina;
	private final AtomicInteger size;
	private final AtomicInteger age;
	private final BufferedImage image;
	

	AnimalProperties(final int sightRange, final double speed, final int stamina, final int size, final int age, final BufferedImage image) {
	    this.sightRange = new AtomicInteger(sightRange);
	    this.speed = new AtomicLong(Double.doubleToLongBits(speed));
	    this.stamina = new AtomicInteger(stamina);
	    this.size = new AtomicInteger(size);
	    this.age = new AtomicInteger(age);
	    this.image = image;
	}

    }

    //SIZES
    private static final int SIZE_ADULT = 50;
    private static final int SIZE_CHILD = 15;
    private static final int SIZE_WORM = 10;
    private static final int SIZE_CHICKEN = 25;
    
    
    //AGES
    private static final int AGE_ADULT = 100;
    
    private static final int AGE_CHILD = 0;
    
    private static final int MIN_AGE_ADULT = 50; //ensure that Children can't promote to an adult too young

    private static AtomicInteger population = new AtomicInteger(20);

    private static Map<Class<?>, AnimalProperties> props = new HashMap<>();

    static {
    BufferedImage imgCow = null;
    BufferedImage imgWorm = null;
    BufferedImage imgChicken = null;
	try {
		imgCow = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\cow.png"));
		imgWorm = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\worm.png"));
		imgChicken = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\chicken.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	props.put(Cow.class, new AnimalProperties( 75, 3.0,100, SIZE_ADULT, MIN_AGE_ADULT, imgCow));
	props.put(Worm.class, new AnimalProperties( 75, 4.0,100, SIZE_WORM, MIN_AGE_ADULT, imgWorm));
	props.put(Chicken.class, new AnimalProperties( 75, 4.0,100, SIZE_CHICKEN, MIN_AGE_ADULT, imgChicken));
	props.put(Adult.class, new AnimalProperties( 75, 3.0,100, SIZE_ADULT, MIN_AGE_ADULT, null));
	props.put(Child.class, new AnimalProperties( 1000, 10.0, 100, SIZE_CHILD, AGE_CHILD, null));
    }
   
    public static BufferedImage getImage(Class<? extends Entity> type) {
    	return props.get(type).image;
    }
    
	public static int getStamina(Class<? extends Entity> type) {
		return  props.get(type).stamina.get();
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
    	return MIN_AGE_ADULT;
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


}
