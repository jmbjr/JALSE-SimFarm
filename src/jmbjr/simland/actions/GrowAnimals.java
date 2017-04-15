package jmbjr.simland.actions;

import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.FarmAnimalProperties;
import jmbjr.simland.entities.Adult;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Child;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Sleeper;

import java.util.Random;

public class GrowAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().filter(notMarkedAsType(Sleeper.class)).forEach(animal -> {

			int newSize = (new Random().nextInt(1000) > 980) ? animal.getSize()+1:animal.getSize();	
			//ensure that we don't set animal size past max size
			animal.setSize(Math.min(newSize,FarmAnimalProperties.getMaxSize()));
		    checkIfAdult(animal);
			
		});
	    }
	public static void checkIfAdult(Animal animal) {

		if (animal.getSize() >= FarmAnimalProperties.getMaxSize()) {
			//adult
			animal.markAsType(Adult.class);
			animal.unmarkAsType(Child.class);
		} else {
			//child
			animal.markAsType(Child.class);
			animal.unmarkAsType(Adult.class);
		}
	}
	
}
