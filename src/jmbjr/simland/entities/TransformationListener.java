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

	animal.setColour(FarmAnimalProperties.getColour(type));
	animal.setSightRange(FarmAnimalProperties.getSightRange(type));
	animal.setSpeed(FarmAnimalProperties.getSpeed(type));
    }
}
