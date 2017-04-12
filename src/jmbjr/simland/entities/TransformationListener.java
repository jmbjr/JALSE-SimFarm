package jmbjr.simland.entities;

import jalse.entities.Entity;
import jalse.entities.EntityTypeEvent;
import jalse.entities.EntityTypeListener;
import jmbjr.simland.AnimalProperties;

public class TransformationListener implements EntityTypeListener {

    @Override
    public void entityMarkedAsType(final EntityTypeEvent event) {
	final Animal animal = event.getEntity().asType(Animal.class);
	final Class<? extends Entity> type = event.getTypeChange();

	animal.setColour(AnimalProperties.getColour(type));
	animal.setSightRange(AnimalProperties.getSightRange(type));
	animal.setSpeed(AnimalProperties.getSpeed(type));
    }
}
