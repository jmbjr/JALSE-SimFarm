package jmbjr.simland;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import jalse.entities.Entity;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Rester;
import jmbjr.simland.entities.Roamer;

public class AnimalProperties {

    private static class PersonProperties {

	private final AtomicReference<Color> colour;
	private final AtomicInteger sightRange;
	private final AtomicLong speed;

	PersonProperties(final Color colour, final int sightRange, final double speed) {
	    this.colour = new AtomicReference<>(colour);
	    this.sightRange = new AtomicInteger(sightRange);
	    this.speed = new AtomicLong(Double.doubleToLongBits(speed));
	}
    }

    private static final int SIZE = 50;

    private static AtomicLong infectionTime = new AtomicLong(Double.doubleToLongBits(5));

    private static AtomicLong starveTime = new AtomicLong(Double.doubleToLongBits(10));

    private static AtomicInteger population = new AtomicInteger(3);

    private static Map<Class<?>, PersonProperties> props = new HashMap<>();

    static {
	props.put(Animal.class, new PersonProperties(Color.WHITE, 75, 3.0));
	props.put(Roamer.class, new PersonProperties(new Color(100,50,15), 75, 3.0));
	props.put(Rester.class, new PersonProperties(new Color(40,30,20), 75, 3.0));
    }

    public static Color getColour(final Class<? extends Entity> type) {
    	return props.get(type).colour.get();
    }

    public static double getInfectionTime() {
	return Double.longBitsToDouble(infectionTime.get());
    }

    public static int getPopulation() {
	return population.get();
    }

    public static int getSightRange(final Class<? extends Entity> type) {
	return props.get(type).sightRange.get();
    }

    public static int getSize() {
	return SIZE;
    }

    public static double getSpeed(final Class<? extends Entity> type) {
	return Double.longBitsToDouble(props.get(type).speed.get());
    }

    public static double getStarveTime() {
	return Double.longBitsToDouble(starveTime.get());
    }




    public static void setInfectionTime(final double infectionTime) {
	AnimalProperties.infectionTime.set(Double.doubleToLongBits(infectionTime));
    }

    public static void setPopulation(final int population) {
	AnimalProperties.population.set(population);
    }


    public static void setSpeed(final Class<? extends Animal> type, final double speed) {
	props.get(type).speed.set(Double.doubleToLongBits(speed));
    }

    public static void setStarveTime(final double starveTime) {
	AnimalProperties.starveTime.set(Double.doubleToLongBits(starveTime));
    }
}
