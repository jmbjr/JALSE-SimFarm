package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.isMarkedAsType;
import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.actions.MoveEntities;
import jmbjr.simland.entities.FarmObject;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.state.Asleep;
import jmbjr.simland.entities.animals.state.MovingToGrass;
import jmbjr.simland.entities.animals.state.Peeking;
import jmbjr.simland.entities.plants.Grass;

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
	final Set<FarmObject> farmobjects = field.getEntitiesOfType(FarmObject.class);
	final Set<FarmObject> targets = field.getEntitiesOfType(FarmObject.class);
	farmobjects.stream()
		.filter(notMarkedAsType(Asleep.class))
		.filter(notMarkedAsType(Peeking.class))
		.filter(isMarkedAsType(MovingToGrass.class))
		.forEach(farmobject -> {
		
		double newDirection;
	    double dist = 0;
	    
	    Class<? extends Entity> target = Grass.class;  //eventually get this from the farmobject
	    Class<? extends Entity> species = Grass.class;
	    
	    int targetRange = 1;
	    double followDistance = targetRange * farmobject.getSize();  // how many body lengths until stop following adult
	    
	    // Move towards adult of same type if far enough away and random number says OK
    	if (!(target == null)) 
    		dist = MoveEntities.distanceToEntity(farmobject, MoveEntities.getClosestEntityOfType(farmobject, targets, species, target));

    	if (dist == 0) 
    		newDirection = MoveEntities.randomDirection(farmobject);
    	if (dist > followDistance) // move until within 2 sizes away from target
			newDirection = MoveEntities.directionToEntity(farmobject, targets, species, target);
    	else
    		newDirection = MoveEntities.randomDirection(farmobject);
    	
	    MoveEntities.maybeSetNewPosition(farmobject, newDirection);

	});
    }
}
