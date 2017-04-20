package jmbjr.simland.entities.drawlayer;

import jmbjr.simland.entities.plants.Grass;
import jmbjr.simland.entities.plants.Plant;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * used when animal is asleep
 */
public interface PlantLayer extends Plant {
	/**
	 * @param animal
	 * checks if animal should be a AnimalLayer
	 */
	public static void checkAndSetType(Plant plant) {
		if (plant.isMarkedAsType(Grass.class)) {
			plant.markAsType(PlantLayer.class);
		}
	}
}
