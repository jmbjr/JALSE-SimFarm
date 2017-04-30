package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.isMarkedAsType;
import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.Utilities;
import jmbjr.simland.actions.MoveUtils;
import jmbjr.simland.entities.FarmObject;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.state.Asleep;
import jmbjr.simland.entities.animals.state.Mover;
import jmbjr.simland.entities.animals.state.Peeking;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle moving animals
 * 
 * 
 */
public class MoveAnimals implements Action<Entity> {

    @Override
    public void perform(final ActionContext<Entity> context) throws InterruptedException {
	final Field field = context.getActor().asType(Field.class);
	final Set<FarmObject> farmobjects = field.getEntitiesOfType(FarmObject.class);
	final Set<FarmObject> targets = field.getEntitiesOfType(FarmObject.class);
	farmobjects.stream()
		.filter(notMarkedAsType(Asleep.class))
		.filter(notMarkedAsType(Peeking.class))
		.filter(isMarkedAsType(Mover.class))
		.forEach(farmobject -> {
		
		double newDirection;
	    double dist = 0;
	    
	    Class<? extends Entity> target = farmobject.getTargetEntity();  //eventually get this from the farmobject
	    Class<? extends Entity> species = farmobject.getTargetEntitySpecies();
	    double followDistance = farmobject.getFollowDistance();  // how many body lengths until stop following adult
	    
	    // Move towards adult of same type if far enough away and random number says OK
    	if (!(target == null)) 
    		dist = MoveUtils.distanceToEntity(farmobject, MoveUtils.getClosestEntityOfType(farmobject, targets, species, target));

    	
    	if (dist > followDistance && Utilities.coinFlip())
			newDirection = MoveUtils.directionToEntity(farmobject, targets, species, target);
    	else
    		newDirection = MoveUtils.randomDirection(farmobject);
    	
	    MoveUtils.maybeSetNewPosition(farmobject, newDirection);

	});
    }
}
