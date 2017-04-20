package jmbjr.simland.entities.animals.ability;

import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * used when animal is awake
 */
public interface Ager extends Animal {
	
	/**
	 * @param animal
	 * checks if animal should be a Ager
	 */
	public static void checkAndSetType(Animal animal) {
		if (animal.isMarkedAsType(Cow.class) ||
			animal.isMarkedAsType(Chicken.class)) {
			
			animal.markAsType(Ager.class);
		}
	}
	
}
