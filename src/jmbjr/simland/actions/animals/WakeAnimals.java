package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.isMarkedAsType;

import java.util.Random;
import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.ability.Sleeper;
import jmbjr.simland.entities.animals.state.Asleep;
import jmbjr.simland.entities.animals.state.Awake;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle putting animals to sleep and waking them up
 * 
 * future plan to separate into sleep and wake
 */
public class WakeAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().filter(isMarkedAsType(Sleeper.class))
						.filter(isMarkedAsType(Asleep.class)).forEach(animal -> {

			int newDrowsiness = (new Random().nextInt(1000) > 500) ? animal.getDrowsiness()+animal.getAlertnessDelta():animal.getDrowsiness();	
			animal.setDrowsiness(Math.max(newDrowsiness,0));
		    checkIfWaking(animal);
		});
	    
	}

	/**
	 * @param animal
	 * check to see if animal wakes up. Drowsiness must be less than alertnessLimit
	 */
	public static void checkIfWaking(Animal animal) {				
				if (animal.isMarkedAsType(Asleep.class) && animal.getDrowsiness() <= animal.getAlertnessLimit()) {
					animal.unmarkAsType(Asleep.class);
					animal.markAsType(Awake.class);
				} 
			}
}
