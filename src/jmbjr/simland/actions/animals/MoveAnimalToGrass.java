package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.isMarkedAsType;
import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.actions.MoveAnimals;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.state.Asleep;
import jmbjr.simland.entities.animals.state.MovingToGrass;
import jmbjr.simland.entities.animals.state.Peeking;
import jmbjr.simland.entities.plants.Grass;
import jmbjr.simland.entities.plants.Plant;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle moving animals
 * 
 * 
 */
public class MoveAnimalToGrass implements Action<Entity> {

    @Override
    public void perform(final ActionContext<Entity> context) throws InterruptedException {
	final Field field = context.getActor().asType(Field.class);
	final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
	final Set<Plant> plants = field.getEntitiesOfType(Plant.class);
	animals.stream()
		.filter(notMarkedAsType(Asleep.class))
		.filter(notMarkedAsType(Peeking.class))
		.filter(isMarkedAsType(MovingToGrass.class))
		.forEach(animal -> {
		
		double newDirection;
	    double dist = 0;
	    
	    Class<? extends Entity> target = Grass.class;
	    Class<? extends Entity> species = Grass.class;
	    
	    int targetRange = 1;
	    double followDistance = targetRange * animal.getSize();  // how many body lengths until stop following adult
	    
	    // Move towards adult of same type if far enough away and random number says OK
    	if (!(target == null)) 
    		dist = MoveAnimals.distanceToPlant(animal, MoveAnimals.getClosestPlantOfType(animal, plants, species, target));

    	if (dist == 0) 
    		newDirection = MoveAnimals.randomDirection(animal);
    	if (dist > followDistance) // move until within 2 sizes away from target
			newDirection = MoveAnimals.directionToPlant(animal, plants, species, target);
    	else
    		newDirection = MoveAnimals.randomDirection(animal);
    	
	    MoveAnimals.maybeSetNewPosition(animal, newDirection);

	});
    }
}
