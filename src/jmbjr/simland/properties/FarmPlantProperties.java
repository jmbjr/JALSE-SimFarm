package jmbjr.simland.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import jalse.entities.Entity;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.plants.Grass;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handles initializing and getting plant properties
 */
public class FarmPlantProperties {

    private static class PlantProperties {

	private final AtomicLong speed;
	private final AtomicInteger stamina;
	private final AtomicInteger size;
	private final AtomicInteger age;
	

	PlantProperties(final double speed, final int stamina, final int size, final int age) {
	    this.speed = new AtomicLong(Double.doubleToLongBits(speed));
	    this.stamina = new AtomicInteger(stamina);
	    this.size = new AtomicInteger(size);
	    this.age = new AtomicInteger(age);
	}

    }

    private static final int SIZE_GRASS = 30;
    
    private static final int AGE_GRASS_MAX = 10;

    private static AtomicInteger population = new AtomicInteger(200);

    private static Map<Class<?>, PlantProperties> props = new HashMap<>();

    static {
    	//note: need to rethink how this works. in TransformationListener, we call these selectively.
    	//probably a better way to do this.
	//props.put(Animal.class, new AnimalProperties( 75, 3.0,100, SIZE_ADULT, 0));
	//props.put(Waker.class, new AnimalProperties( 500, 3.0,100, SIZE_ADULT, 0));
	//props.put(Sleeper.class, new AnimalProperties( 500, 3.0,100, SIZE_ADULT/2, 0));
	props.put(Grass.class, new PlantProperties(6.0,100, SIZE_GRASS, 0));
    }
   
	public static int getStamina(Class<? extends Entity> type) {
		return  props.get(type).stamina.get();
	}
	
    public static int getPopulation() {
	return population.get();
    }

    public static int getSizeGrass() {
	return SIZE_GRASS;
    }
    
    public static int getAgeGrassMax() {
	return AGE_GRASS_MAX;
    }   
    
    public static int getSize(final Class<? extends Entity> type) {
	return props.get(type).size.get();
    }
    
    public static int getAge(final Class<? extends Entity> type) {
	return props.get(type).age.get();
    }
    
    public static double getSpeed(final Class<? extends Entity> type) {
	return Double.longBitsToDouble(props.get(type).speed.get());
    }


    public static void setPopulation(final int population) {
	FarmPlantProperties.population.set(population);
    }


    public static void setSpeed(final Class<? extends Animal> type, final double speed) {
	props.get(type).speed.set(Double.doubleToLongBits(speed));
    }


}
