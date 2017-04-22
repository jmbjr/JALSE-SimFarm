package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.notMarkedAsType;
import static jalse.entities.Entities.isMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.ability.Sleeper;
import jmbjr.simland.entities.animals.state.Asleep;
import jmbjr.simland.entities.animals.state.Awake;
import jmbjr.simland.properties.FarmAnimalProperties;

import java.util.Random;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle putting animals to sleep and waking them up
 * 
 * future plan to separate into sleep and wake
 */
public class SleepAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().filter(isMarkedAsType(Sleeper.class))
						.filter(isMarkedAsType(Awake.class)).forEach(animal -> {

			int newDrowsiness = (new Random().nextInt(1000) > 500) ? animal.getDrowsiness()+animal.getDrowsinessDelta():animal.getDrowsiness();	
			animal.setDrowsiness(Math.min(newDrowsiness,FarmAnimalProperties.getMaxDrowsiness()));
		    checkIfSleeping(animal);
		});
   
	}
	/**
	 * @param animal
	 * check if animal drowsiness is greater than the limit for this animal. if so, mark as asleep
	 */
	public static void checkIfSleeping(Animal animal) {
		
		if (!animal.isMarkedAsType(Asleep.class) && animal.getDrowsiness() >= animal.getDrowsinessLimit()) {
			//rester/dead
			animal.markAsType(Asleep.class);
			animal.unmarkAsType(Awake.class);
		} 
	}
}

