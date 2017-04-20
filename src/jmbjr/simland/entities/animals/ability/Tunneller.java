package jmbjr.simland.entities.animals.ability;

import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Worm;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * used when animal is awake
 */
public interface Tunneller extends Animal {

	/**
	 * @param animal
	 * checks if animal should be a tunneller
	 */
	public static void checkAndSetType(Animal animal) {
		if (animal.isMarkedAsType(Worm.class)) {
			animal.markAsType(Tunneller.class);
		}
	}
}
