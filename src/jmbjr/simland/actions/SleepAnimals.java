package jmbjr.simland.actions;

import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Waker;
import jmbjr.simland.entities.Sleeper;

import java.util.Random;

public class SleepAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().filter(notMarkedAsType(Sleeper.class)).forEach(animal -> {

			int newStamina = (new Random().nextInt(1000) > 500) ? animal.getStamina()-1:animal.getStamina();	
			animal.setStamina(Math.max(newStamina,0));
		    checkIfSleeping(animal);
		});

		animals.stream().filter(notMarkedAsType(Waker.class)).forEach(animal -> {
		if (animal.isMarkedAsType(Sleeper.class)) {
			int newEnergy= (new Random().nextInt(1000) > 500) ? animal.getStamina()+1:animal.getStamina();	
			animal.setStamina(Math.min(newEnergy,100));
			
			checkIfWaking(animal);
		}}
		);
	    
	}
	public static void checkIfSleeping(Animal animal) {
//ToDo: set sleeping threshold. maybe 30 or so. after this point, chance of sleeping is high.
		//then while sleeping, set a waking threshold. maybe 60 or so, after this point, chance of waking is high
		//then use constants to set threshold. 
		//update setStamina to enforce 0 to max stamina.
		//get rid of checks vs max stamina and 0
		
		if (!animal.isMarkedAsType(Sleeper.class) && animal.getStamina() <= 0) {
			//rester/dead
			animal.markAsType(Sleeper.class);
			animal.unmarkAsType(Waker.class);
			animal.setStamina(-50);
		} 
	}
	public static void checkIfWaking(Animal animal) {
		//ToDo: set sleeping threshold. maybe 30 or so. after this point, chance of sleeping is high.
				//then while sleeping, set a waking threshold. maybe 60 or so, after this point, chance of waking is high
				//then use constants to set threshold. 
				//update setStamina to enforce 0 to max stamina.
				//get rid of checks vs max stamina and 0
				
				if (animal.isMarkedAsType(Sleeper.class) && animal.getStamina() > 0) {
					//rester/dead
					animal.unmarkAsType(Sleeper.class);
					animal.markAsType(Waker.class);
					animal.setStamina(100);
				} 
			}
	
}
