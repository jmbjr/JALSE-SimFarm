package jmbjr.simland.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import jalse.entities.Entity;
import jmbjr.simland.entities.animals.Adult;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Child;

public class FarmAnimalProperties {

    private static class AnimalProperties {

	private final AtomicInteger sightRange;
	private final AtomicLong speed;
	private final AtomicInteger stamina;
	private final AtomicInteger size;
	private final AtomicInteger age;
	

	AnimalProperties(final int sightRange, final double speed, final int stamina, final int size, final int age) {
	    this.sightRange = new AtomicInteger(sightRange);
	    this.speed = new AtomicLong(Double.doubleToLongBits(speed));
	    this.stamina = new AtomicInteger(stamina);
	    this.size = new AtomicInteger(size);
	    this.age = new AtomicInteger(age);
	}

    }

    private static final int SIZE_ADULT = 50;
    
    private static final int SIZE_CHILD = 15;
    
    private static final int AGE_ADULT = 100;

    private static AtomicInteger population = new AtomicInteger(3);

    private static Map<Class<?>, AnimalProperties> props = new HashMap<>();

    static {
    	//note: need to rethink how this works. in TransformationListener, we call these selectively.
    	//probably a better way to do this.
	//props.put(Animal.class, new AnimalProperties( 75, 3.0,100, SIZE_ADULT, 0));
	//props.put(Waker.class, new AnimalProperties( 500, 3.0,100, SIZE_ADULT, 0));
	//props.put(Sleeper.class, new AnimalProperties( 500, 3.0,100, SIZE_ADULT/2, 0));
	props.put(Child.class, new AnimalProperties( 500, 6.0,100, SIZE_CHILD, 0));
	props.put(Adult.class, new AnimalProperties( 75, 3.0,100, SIZE_ADULT, 100));
    }
   
	public static int getStamina(Class<? extends Entity> type) {
		return  props.get(type).stamina.get();
	}
	
    public static int getPopulation() {
	return population.get();
    }

    public static int getSightRange(final Class<? extends Entity> type) {
	return props.get(type).sightRange.get();
    }

    public static int getMaxSize() {
	return SIZE_ADULT;
    }
    
    public static int getAdultAge() {
    	return AGE_ADULT;
    }
    
    public static int getSize(final Class<? extends Entity> type) {
	return props.get(type).size.get();
    }
    
    public static int getAge(final Class<? extends Entity> type) {
	return props.get(type).age.get();
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
