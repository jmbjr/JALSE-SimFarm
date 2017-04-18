package jmbjr.simland.entities.listeners;

import jalse.entities.Entity;
import jalse.entities.EntityTypeEvent;
import jalse.entities.EntityTypeListener;
import jmbjr.simland.entities.animals.Adult;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Child;
import jmbjr.simland.entities.animals.Cow;
import jmbjr.simland.entities.animals.Worm;
import jmbjr.simland.properties.FarmAnimalProperties;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * attached to all animals. triggers when there's a type transformation
 * used to set animal properties when creating animals or (d)evolving child/adult animals
 */
public class AnimalTransformationListener implements EntityTypeListener {

    @Override
    public void entityMarkedAsType(final EntityTypeEvent event) {
	final Animal animal = event.getEntity().asType(Animal.class);
	final Class<? extends Entity> type = event.getTypeChange();
	
	if (type.equals(Cow.class) || type.equals(Worm.class)) { //maybe add a new type Alive.class ? or something else to bucket these things so Rester and Grazer don't need defined
		animal.setImage(FarmAnimalProperties.getImage(type));
	}

	
	//add listeners for Child.Class to set the stuff we are setting in FarmPanel like age, size, sightRange
	if (type.equals(Adult.class) || type.equals(Child.class)) {
		animal.setSightRange(FarmAnimalProperties.getSightRange(type));
		animal.setSpeed(FarmAnimalProperties.getSpeed(type));
		animal.setStamina(FarmAnimalProperties.getStamina(type));
		animal.setSize(FarmAnimalProperties.getSize(type));
		animal.setAge(FarmAnimalProperties.getAge(type));
	}
    }
}