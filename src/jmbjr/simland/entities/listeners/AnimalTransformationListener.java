package jmbjr.simland.entities.listeners;

import jalse.entities.Entity;
import jalse.entities.EntityTypeEvent;
import jalse.entities.EntityTypeListener;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;
import jmbjr.simland.entities.animals.Pig;
import jmbjr.simland.entities.animals.Worm;
import jmbjr.simland.entities.animals.ability.Tunneller;
import jmbjr.simland.entities.animals.age.Adult;
import jmbjr.simland.entities.animals.age.Child;
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
	
	if (type.equals(Cow.class) || type.equals(Worm.class) || type.equals(Chicken.class)||type.equals(Pig.class)) { //maybe add a new type Alive.class ? or something else to bucket these things so Rester and Grazer don't need defined
		animal.setImage(FarmAnimalProperties.getImage(type));
		animal.setVisibility(true); // really really need to pull this into Properties somehow.
		//this is dumb but if we let Child or Adult set stats, we don't get worm-specific stats
		//so we do it here, which feels silly
		if (type.equals(Worm.class)) {
			animal.setSightRange(FarmAnimalProperties.getSightRange(type));
			animal.setSpeed(FarmAnimalProperties.getSpeed(type));
			animal.setDrowsiness(FarmAnimalProperties.getStamina(type));
			animal.setSize(FarmAnimalProperties.getSize(type));
			animal.setAge(FarmAnimalProperties.getAge(type));
		}
	}
	
	//add listeners for Child.Class to set the stuff we are setting in FarmPanel like age, size, sightRange
	if (type.equals(Adult.class) || type.equals(Child.class)) {
		animal.setSightRange(FarmAnimalProperties.getSightRange(type));
		animal.setSpeed(FarmAnimalProperties.getSpeed(type));
		animal.setDrowsiness(FarmAnimalProperties.getStamina(type));
		animal.setSize(FarmAnimalProperties.getSize(type));
		animal.setAge(FarmAnimalProperties.getAge(type));
	}
    }
}
