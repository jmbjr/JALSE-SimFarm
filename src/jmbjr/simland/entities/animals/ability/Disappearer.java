package jmbjr.simland.entities.animals.ability;

import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Worm;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * used when animal is awake
 */
public interface Disappearer extends Animal {
	
	/**
	 * @param animal
	 * checks if animal should be a Ager
	 */
	public static void checkAndSetType(Animal animal) {
		if (animal.isMarkedAsType(Worm.class)) {
			animal.markAsType(Disappearer.class);
			animal.setVisibility(false);
		}
	}
	
}
