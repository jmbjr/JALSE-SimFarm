package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.isMarkedAsType;
import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.actions.MoveAnimals;
import jmbjr.simland.entities.FarmObject;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.state.Asleep;
import jmbjr.simland.entities.animals.state.MovingRandomly;
import jmbjr.simland.entities.animals.state.Peeking;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle moving animals
 * 
 * 
 */
public class MoveRandomly implements Action<Entity> {

    @Override
    public void perform(final ActionContext<Entity> context) throws InterruptedException {
	final Field field = context.getActor().asType(Field.class);
	final Set<FarmObject> farmobjects = field.getEntitiesOfType(FarmObject.class);
	farmobjects.stream()
		.filter(notMarkedAsType(Asleep.class))
		.filter(notMarkedAsType(Peeking.class))
		.filter(isMarkedAsType(MovingRandomly.class))
		.forEach(farmobject -> {
		double newDirection;
		
    	newDirection = MoveAnimals.randomDirection(farmobject);	
	    MoveAnimals.maybeSetNewPosition(farmobject, newDirection);

	});
    }

}
