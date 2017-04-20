package jmbjr.simland.entities.drawlayer;

import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * used when animal is asleep
 */
public interface AnimalLayer extends Animal {
	/**
	 * @param animal
	 * checks if animal should be a AnimalLayer
	 */
	public static void checkAndSetType(Animal animal) {
		if (animal.isMarkedAsType(Cow.class) ||
			animal.isMarkedAsType(Chicken.class)) {
			
			animal.markAsType(AnimalLayer.class);
		}
	}
}
