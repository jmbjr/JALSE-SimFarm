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
import jmbjr.simland.entities.plants.Grass;
import jmbjr.simland.entities.plants.Plant;

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
	private final BufferedImage[] image;
	

	PlantProperties(final double speed, final int stamina, final int size, final int age, final BufferedImage[] image) {
	    this.speed = new AtomicLong(Double.doubleToLongBits(speed));
	    this.stamina = new AtomicInteger(stamina);
	    this.size = new AtomicInteger(size);
	    this.age = new AtomicInteger(age);
	    this.image = image;
	}

    }

    private static final int SIZE_GRASS = 30;
    
    private static final int AGE_GRASS_MAX = 10;

    private static AtomicInteger population = new AtomicInteger(200);

    private static Map<Class<?>, PlantProperties> props = new HashMap<>();

    static {
    BufferedImage[] imgGrass = new BufferedImage[10];
	try {
		for (int g = 0; g < FarmPlantProperties.getAgeGrassMax();g++) {
			System.out.println(g + " C:\\dev\\JALSE\\JALSE-SimLand\\img\\plants\\grass"+g+".png" );
			imgGrass[g] = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\plants\\grass"+g+".png"));
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	props.put(Grass.class, new PlantProperties(6.0,100, SIZE_GRASS, 0, imgGrass));
    }

    public static BufferedImage[] getImage(Class<? extends Entity> type) {
    	return props.get(type).image;
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


    public static void setSpeed(final Class<? extends Plant> type, final double speed) {
	props.get(type).speed.set(Double.doubleToLongBits(speed));
    }


}
