package jmbjr.simland.entities.animals.ability;

import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * used when animal is awake
 */
public interface Grower extends Animal {

	/**
	 * @param animal
	 * routine checks if animal should be a Grower
	 * need a better way to do this, but this should work for now
	 */
	public static void checkAndSetType(Animal animal) {
		if (animal.isMarkedAsType(Cow.class)||
			animal.isMarkedAsType(Chicken.class)) {
			
			animal.markAsType(Grower.class);
		}
	}
} 
