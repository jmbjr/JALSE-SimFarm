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
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;
import jmbjr.simland.entities.animals.Pig;
import jmbjr.simland.entities.animals.Worm;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handles initializing and getting animal properties
 * for now, only child and adult properties are maintained, along with several constants
 */
public class FarmAnimalProperties {

    private static class AnimalProperties {

	private final AtomicInteger sightRange_child;
	private final AtomicInteger sightRange_adult;
	private final AtomicLong speed_child;
	private final AtomicLong speed_adult;
	private final AtomicInteger size_child;
	private final AtomicInteger size_adult;
	private final BufferedImage image_child;
	private final BufferedImage image_adult;
	
	private final AtomicInteger drowsiness;
	private final AtomicInteger drowsinessDelta;
	private final AtomicInteger drowsinessLimit;
	private final AtomicInteger alertnessDelta;
	private final AtomicInteger alertnessLimit;
	
	private final AtomicInteger age;

	
	AnimalProperties(final int sightRange_child, final int sightRange_adult, 
					 final double speed_child, final double speed_adult,  
					 final int size_child, final int size_adult,
					 final BufferedImage image_child, final BufferedImage image_adult, 
					 final int drowsiness, final int drowsinessDelta, final int drowsinessLimit,
					 final int alertnessDelta, final int alertnessLimit, final int age) {
	    this.sightRange_child = new AtomicInteger(sightRange_child);
	    this.sightRange_adult = new AtomicInteger(sightRange_adult);
	    this.speed_child = new AtomicLong(Double.doubleToLongBits(speed_child));
	    this.speed_adult = new AtomicLong(Double.doubleToLongBits(speed_adult));
	    this.size_child = new AtomicInteger(size_child);
	    this.size_adult = new AtomicInteger(size_adult);
	    this.image_child = image_child;
	    this.image_adult = image_adult;
	    
	    this.drowsiness = new AtomicInteger(drowsiness);
	    this.drowsinessDelta = new AtomicInteger(drowsinessDelta);
	    this.drowsinessLimit = new AtomicInteger(drowsinessLimit);
	    this.alertnessDelta = new AtomicInteger(alertnessDelta);
	    this.alertnessLimit = new AtomicInteger(alertnessLimit);
	    this.age = new AtomicInteger(age);
	   
	}

    }
    
	
	//SIGHTRANGE
	private static final int SIGHTRANGE_NORMAL = 75;
	private static final int SIGHTRANGE_FAR = 1000;
	
	//SPEED
	private static final double SPEED_SLOW = 3.0;
	private static final double SPEED_NORMAL = 4.0;
	private static final double SPEED_VERY_FAST = 10.0;
	
    //DROWSINESS
    private static final int DROWSINESS_MAX = 500;
    private static final int DROWSINESS_INIT = 0;
    private static final int DROWSINESS_DELTA_LOW = 1;
    private static final int DROWSINESS_DELTA_NORMAL = 2;
    private static final int DROWSINESS_LIMIT_LOW = 350;
    private static final int DROWSINESS_LIMIT_NORMAL = 450;
    private static final int ALERTNESS_DELTA_LOW = -1;
    private static final int ALERTNESS_DELTA_NORMAL = -2;
    private static final int ALERTNESS_LIMIT_LOW = 25;
    private static final int ALERTNESS_LIMIT_NORMAL = 50;
	
    //SIZES
    private static final int SIZE_ADULT = 50;
    private static final int SIZE_COW = 60;
    private static final int SIZE_CHILD = 15;
    private static final int SIZE_WORM = 10;
    private static final int SIZE_CHICKEN = 20;
    private static final int SIZE_PIG = 30;
    
    //AGES
    private static final int AGE_ADULT = 100;
    private static final int AGE_ADULT_MIN = 50; 

    private static AtomicInteger population = new AtomicInteger(20);

    private static Map<Class<?>, AnimalProperties> props = new HashMap<>();

    static {
    BufferedImage imgCow = null;
    BufferedImage imgWorm = null;
    BufferedImage imgChicken = null;
    BufferedImage imgChickenChild = null;
    BufferedImage imgPig = null;
	try {
		imgCow = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\cow.png"));
		imgWorm = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\worm.png"));
		imgChicken = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\chicken.png"));
		imgChickenChild = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\chicken_baby.png"));
		imgPig = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\pig.png"));

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//for animal species
	props.put(Cow.class, new AnimalProperties( SIGHTRANGE_FAR, SIGHTRANGE_NORMAL, SPEED_VERY_FAST, SPEED_SLOW, SIZE_COW - 30, SIZE_COW, imgCow, imgCow, DROWSINESS_INIT, DROWSINESS_DELTA_LOW, DROWSINESS_LIMIT_LOW, ALERTNESS_DELTA_LOW, ALERTNESS_LIMIT_LOW, AGE_ADULT_MIN));
	props.put(Worm.class, new AnimalProperties( SIGHTRANGE_FAR, SIGHTRANGE_NORMAL, SPEED_VERY_FAST, SPEED_NORMAL, SIZE_CHILD, SIZE_WORM, imgWorm, imgWorm, DROWSINESS_INIT, DROWSINESS_DELTA_LOW, DROWSINESS_LIMIT_LOW, ALERTNESS_DELTA_LOW, ALERTNESS_LIMIT_LOW, AGE_ADULT_MIN));
	props.put(Chicken.class, new AnimalProperties( SIGHTRANGE_FAR, SIGHTRANGE_NORMAL, SPEED_VERY_FAST, SPEED_NORMAL, SIZE_CHICKEN - 10, SIZE_CHICKEN, imgChickenChild, imgChicken, DROWSINESS_INIT, DROWSINESS_DELTA_NORMAL, DROWSINESS_LIMIT_LOW, ALERTNESS_DELTA_LOW, ALERTNESS_LIMIT_NORMAL, AGE_ADULT_MIN));
	props.put(Pig.class, new AnimalProperties(SIGHTRANGE_FAR, SIGHTRANGE_NORMAL, SPEED_VERY_FAST, SPEED_NORMAL, SIZE_PIG - 10, SIZE_PIG, imgPig, imgPig, DROWSINESS_INIT,DROWSINESS_DELTA_LOW, DROWSINESS_LIMIT_NORMAL, ALERTNESS_DELTA_NORMAL, ALERTNESS_LIMIT_LOW, AGE_ADULT_MIN));
	
    }
   
    public static BufferedImage getImage(Class<? extends Entity> type) {
    	return props.get(type).image_adult;
    }
    
    public static int getSightRangeAdult(final Class<? extends Entity> type) {
	return props.get(type).sightRange_adult.get();
    }

    public static int getSightRangeChild(final Class<? extends Entity> type) {
	return props.get(type).sightRange_child.get();
    }

	public static int getSizeAdult(Class<? extends Entity> type) {
		return  props.get(type).size_adult.get();
	}
	
	public static int getSizeChild(Class<? extends Entity> type) {
		return  props.get(type).size_child.get();
	}

    public static double getSpeedAdult(final Class<? extends Entity> type) {
	return Double.longBitsToDouble(props.get(type).speed_adult.get());
    }

    public static double getSpeedChild(final Class<? extends Entity> type) {
	return Double.longBitsToDouble(props.get(type).speed_child.get());
    }	
    
	public static int getAge(Class<? extends Entity> type) {
		return  props.get(type).age.get();
	}
	
    public static int getPopulation() {
	return population.get();
    }

	public static int getDrowsiness(Class<? extends Entity> type) {
		return  props.get(type).drowsiness.get();
	}

	public static int getDrowsinessDelta(Class<? extends Entity> type) {
		return  props.get(type).drowsinessDelta.get();
	}

	public static int getDrowsinessLimit(Class<? extends Entity> type) {
		return  props.get(type).drowsinessLimit.get();
	}

	public static int getAlertnessDelta(Class<? extends Entity> type) {
		return  props.get(type).alertnessDelta.get();
	}

	public static int getAlertnessLimit(Class<? extends Entity> type) {
		return  props.get(type).alertnessLimit.get();
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
    
    public static BufferedImage getImageChild(Class<? extends Entity> type) {
    	return props.get(type).image_child;
    }
    
    public static BufferedImage getImageAdult(Class<? extends Entity> type) {
    	return props.get(type).image_adult;
    }
    
    public static void setPopulation(final int population) {
	FarmAnimalProperties.population.set(population);
    }

    public static int getMaxDrowsiness() {
    	return DROWSINESS_MAX;
    }
    
}
