package jmbjr.simland.entities;

import jalse.entities.Entity;
import jalse.entities.EntityTypeEvent;
import jalse.entities.EntityTypeListener;
import jmbjr.simland.FarmAnimalProperties;

public class TransformationListener implements EntityTypeListener {

    @Override
    public void entityMarkedAsType(final EntityTypeEvent event) {
	final Animal animal = event.getEntity().asType(Animal.class);
	final Class<? extends Entity> type = event.getTypeChange();

	//need to adjust stats based on makeup of Animal
	//i.e. size is based on maturity for now.
	
	animal.setSightRange(FarmAnimalProperties.getSightRange(type));
	animal.setSpeed(FarmAnimalProperties.getSpeed(type));
	animal.setHealth(FarmAnimalProperties.getHealth(type));
	if (type.equals(Adult.class) || type.equals(Child.class)) { //maybe add a new type Alive.class ? or something else to bucket these things so Rester and Grazer don't need defined
		animal.setSize(FarmAnimalProperties.getSize(type));
	}
    }
}
