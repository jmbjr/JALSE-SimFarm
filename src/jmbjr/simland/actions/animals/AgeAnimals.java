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
	public static void checkIfAdult(Animal animal) {
//animals can't be adults now. need to refactor logic
		if (animal.getAge() >= FarmAnimalProperties.getAdultAge() && animal.getSize() >= FarmAnimalProperties.getMaxSize()) {
			//grow into an adult
			animal.markAsType(Adult.class);
			animal.unmarkAsType(Child.class);
		} else {
			//means that we can make an adult a child if we revert the age. not sure if this is OK or not. leaving it for now
			animal.markAsType(Child.class);
			animal.unmarkAsType(Adult.class);
		}
	}
	
}
