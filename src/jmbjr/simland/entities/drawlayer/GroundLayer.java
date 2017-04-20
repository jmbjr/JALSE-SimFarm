package jmbjr.simland.entities.drawlayer;

import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Worm;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * used when animal is asleep
 */
public interface GroundLayer extends Animal {

	/**
	 * @param animal
	 * checks if animal should be a GroundLayer
	 */
	public static void checkAndSetType(Animal animal) {
		if (animal.isMarkedAsType(Worm.class)) {
			animal.markAsType(GroundLayer.class);
		}
	}
}
