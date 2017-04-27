package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.isMarkedAsType;

import java.util.Random;
import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.ability.Ager;
import jmbjr.simland.entities.animals.state.Adult;
import jmbjr.simland.entities.animals.state.Child;
import jmbjr.simland.entities.animals.state.Mover;
import jmbjr.simland.entities.plants.Grass;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle aging animals and converting from child->adult type
 */
public class AgeAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().filter(isMarkedAsType(Ager.class)).forEach(animal -> {

			int newAge = (new Random().nextInt(1000) > 200) ? animal.getAge()+1:animal.getAge();	
			animal.setAge(newAge);
		    checkIfAdult(animal);
			
		});
	    }
	/**
	 * @param animal
	 * change child entity to an adult if:
	 * 1. is old enough (must be > getMinAgeAdult)
	 * 2. is large enough
	 * 
	 * NOTE: growing up is a one-way journey. only Children are checked.
	 */
	public static void checkIfAdult(Animal animal) {
		if (animal.isMarkedAsType(Child.class)) {
			if (animal.getAge() >= animal.getAgeMaturity() && animal.getSize() >= animal.getSizeMaturity()) {
				//grow into an adult
				animal.markAsType(Adult.class);
				animal.unmarkAsType(Child.class);		
			}
		}
	}
	
}
