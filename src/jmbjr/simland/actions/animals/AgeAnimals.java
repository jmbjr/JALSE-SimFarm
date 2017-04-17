package jmbjr.simland.actions.animals;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Adult;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Child;
import jmbjr.simland.properties.FarmAnimalProperties;

import java.util.Random;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle aging animals and converting from child->adult type
 */
public class AgeAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().forEach(animal -> {

			int newAge = (new Random().nextInt(1000) > 200) ? animal.getAge()+1:animal.getAge();	
			animal.setAge(newAge);
		    checkIfAdult(animal);
			
		});
	    }
	/**
	 * @param animal
	 * change child entity to an adult if:
	 * 1. is old enough
	 * 2. is large enough
	 * 
	 * this function also allows reverting an adult back to a child if the above conditions are not met
	 * this may or may not be a good idea long-term
	 */
	public static void checkIfAdult(Animal animal) {
		if (animal.getAge() >= FarmAnimalProperties.getAdultAge() && animal.getSize() >= FarmAnimalProperties.getMaxSize()) {
			//grow into an adult
			animal.markAsType(Adult.class);
			animal.unmarkAsType(Child.class);
		} else {
			animal.markAsType(Child.class);
			animal.unmarkAsType(Adult.class);
		}
	}
	
}
