package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.state.Sleeping;
import jmbjr.simland.entities.animals.state.NotSleeping;

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
		animals.stream().filter(notMarkedAsType(Sleeping.class)).forEach(animal -> {

			int newStamina = (new Random().nextInt(1000) > 500) ? animal.getStamina()-1:animal.getStamina();	
			animal.setStamina(Math.max(newStamina,0));
		    checkIfSleeping(animal);
		});

		animals.stream().filter(notMarkedAsType(NotSleeping.class)).forEach(animal -> {
		if (animal.isMarkedAsType(Sleeping.class)) {
			int newEnergy= (new Random().nextInt(1000) > 500) ? animal.getStamina()+1:animal.getStamina();	
			animal.setStamina(Math.min(newEnergy,100));
			
			checkIfWaking(animal);
		}}
		);
	    
	}
	/**
	 * @param animal
	 * simple check to see if stamina is less than 0. if so, animal is asleep.
	 * if animal goes to sleep, stamina is set to -50. Assume that stamina is increased while sleeping.
	 * 
	 * future plan: set sleeping threshold. maybe 30 or so. after this point, chance of sleeping is high.
	 * then while sleeping, set a waking threshold. maybe 60 or so, after this point, chance of waking is high
	 * then use constants to set threshold. 
	 * update setStamina to enforce 0 to max stamina.
	 * get rid of checks vs max stamina and 0
	 */
	public static void checkIfSleeping(Animal animal) {
		
		if (!animal.isMarkedAsType(Sleeping.class) && animal.getStamina() <= 0) {
			//rester/dead
			animal.markAsType(Sleeping.class);
			animal.unmarkAsType(NotSleeping.class);
			animal.setStamina(-50);
		} 
	}
	/**
	 * @param animal
	 * simple check to see if animal should wake up. will wake up if stamina > 0.
	 * for now, stamina is immediately set to 100 (full recharge). should be variablized
	 */
	public static void checkIfWaking(Animal animal) {				
				if (animal.isMarkedAsType(Sleeping.class) && animal.getStamina() > 0) {
					//rester/dead
					animal.unmarkAsType(Sleeping.class);
					animal.markAsType(NotSleeping.class);
					animal.setStamina(100);
				} 
			}
}
