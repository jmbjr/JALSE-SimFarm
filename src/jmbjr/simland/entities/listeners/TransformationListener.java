package jmbjr.simland.entities.listeners;

import jalse.entities.Entity;
import jalse.entities.EntityTypeEvent;
import jalse.entities.EntityTypeListener;
import jmbjr.simland.entities.animals.Adult;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Child;
import jmbjr.simland.properties.FarmAnimalProperties;

public class TransformationListener implements EntityTypeListener {

    @Override
    public void entityMarkedAsType(final EntityTypeEvent event) {
	final Animal animal = event.getEntity().asType(Animal.class);
	final Class<? extends Entity> type = event.getTypeChange();
	
	if (type.equals(Adult.class) || type.equals(Child.class)) { //maybe add a new type Alive.class ? or something else to bucket these things so Rester and Grazer don't need defined
		animal.setSize(FarmAnimalProperties.getSize(type));
		animal.setAge(FarmAnimalProperties.getAge(type));
		animal.setSightRange(FarmAnimalProperties.getSightRange(type));
		animal.setSpeed(FarmAnimalProperties.getSpeed(type));
		animal.setStamina(FarmAnimalProperties.getStamina(type));
	}
    }
}
