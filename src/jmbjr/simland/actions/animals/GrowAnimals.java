package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Sleeper;
import jmbjr.simland.properties.FarmAnimalProperties;

import java.util.Random;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle growing animal sizes. skips growing if animals are asleep
 * 
 * future plans to base growing on eating and overall health level.
 */
public class GrowAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().filter(notMarkedAsType(Sleeper.class)).forEach(animal -> {

			int newSize = (new Random().nextInt(1000) > 980) ? animal.getSize()+1:animal.getSize();	
			//ensure that we don't set animal size past max size
			animal.setSize(Math.min(newSize,FarmAnimalProperties.getSizeAdult()));
		});
	    }
}

