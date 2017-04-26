package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.notMarkedAsType;

import java.util.Random;
import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.actions.MoveAnimals;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.state.Adult;
import jmbjr.simland.entities.animals.state.Asleep;
import jmbjr.simland.entities.animals.state.Peeking;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle moving animals
 * 
 * 
 */
public class MoveChildToAdult implements Action<Entity> {

    @Override
    public void perform(final ActionContext<Entity> context) throws InterruptedException {
	final Field field = context.getActor().asType(Field.class);
	final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
	animals.stream()
		.filter(notMarkedAsType(Asleep.class))
		.filter(notMarkedAsType(Peeking.class))
		.forEach(animal -> {
		
		double newDirection;
	    Random rand = new Random();	
	    int randInt = rand.nextInt(1000);
	    double dist = 0;
	    
	    Class<? extends Entity> target = Adult.class;
	    Class<? extends Entity> species = animal.getSpecies();
	    
	    // Move towards adult of same type if far enough away and random number says OK
    	if (!(target == null)) 
    		dist = MoveAnimals.distanceToTarget(animal, MoveAnimals.getClosestAnimalOfType(animal, animals, species, target));

    	//newDirection = 
    	if (dist > 4*animal.getSizeAdult() && randInt > 100 )  //almost certaintly run to momma
    		newDirection = MoveAnimals.directionToTarget(animal, animals, species, target);
    	else if (dist > 2*animal.getSizeAdult() && randInt > 900) //if closer, less of a chance to keep running
    		newDirection = MoveAnimals.directionToTarget(animal, animals, species, target);
    	else
    		newDirection = MoveAnimals.randomDirection(animal);
    	
	    MoveAnimals.maybeSetNewPosition(animal, newDirection);

	});
    }
}
