package jmbjr.simland.actions;

import static jalse.entities.Entities.isMarkedAsType;
import static jalse.misc.Identifiable.not;

import java.awt.Point;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import jalse.entities.Entity;
import jmbjr.simland.entities.FarmObject;
import jmbjr.simland.panels.FarmPanel;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle moving animals
 * 
 * 
 */
public class MoveUtils  {
	
    public static Double distanceToEntity(final FarmObject source, final Optional <FarmObject> target) {
    	if (target.equals(Optional.empty()) || target == null) 
    		return (double) 0;
    	else {
	    	double dx = target.get().getPosition().x - source.getPosition().x;
	    	double dy = target.get().getPosition().y - source.getPosition().y;
	    	return Math.sqrt(dx*dx + dy*dy);
    	}
    }

    public static Double directionToEntity(final FarmObject sourceEntity, final Set<FarmObject> targets, final Class<? extends Entity> species, final Class<? extends Entity> target) {
		// Find closest target in sight
		final Optional<FarmObject> closestTarget = MoveUtils.getClosestEntityOfType(sourceEntity, targets, species, target);
	
		// Check can see any
		if (!closestTarget.isPresent()) {
		    return MoveUtils.randomDirection(sourceEntity);
		}
	
		// Adult
		final FarmObject closestTargetPlant = closestTarget.get();
	
		// Towards
		final Point sourcePos = sourceEntity.getPosition();
		final int dx = closestTargetPlant.getPosition().x - sourcePos.x;
		final int dy = closestTargetPlant.getPosition().y - sourcePos.y;
	
		// Convert
		return Math.atan2(dy, dx);
    }    
    public static int bounded(final int value, final int min, final int max) {
    	return value < min ? min : value > max ? max : value;
    }
    
    public static Optional<FarmObject> getClosestEntityOfType(final FarmObject farmobject, final Set<FarmObject> targets,
    	    final Class<? extends Entity> species, final Class<? extends Entity> target) {
    		final Point objectPos = farmobject.getPosition();
    		final Integer sightRange = farmobject.getSightRange();
    		// Stream other entities of type
    		return targets.stream().filter(not(farmobject))
    							   .filter(isMarkedAsType(species))
    							   .filter(isMarkedAsType(target)).filter(p -> {
    		    final Point pPos = p.getPosition();
    		    // Within range
    		    return Math.abs(pPos.x - objectPos.x) <= sightRange && Math.abs(pPos.y - objectPos.y) <= sightRange;
    		}).collect(Collectors.minBy((a, b) -> {
    		    final Point aPos = a.getPosition();
    		    final Point bPos = b.getPosition();
    		    // Closest person
    		    final int d1 = (aPos.x - objectPos.x) * (aPos.x - objectPos.x)
    			    + (aPos.y - objectPos.y) * (aPos.y - objectPos.y);
    		    final int d2 = (bPos.x - objectPos.x) * (bPos.x - objectPos.x)
    			    + (bPos.y - objectPos.y) * (bPos.y - objectPos.y);
    		    return d1 - d2;
    		}));
        }
    
    public static Double randomDirection(final FarmObject farmobject) {
    	return farmobject.getAngle() + 2. * (ThreadLocalRandom.current().nextDouble() - 0.5);
    }
    
    //separate into new method to get x,y and a new method to setNewPosition (always)
	public static void maybeSetNewPosition(FarmObject farmobject, double moveAngle) {
	    Random rand = new Random();	
	    int randInt = rand.nextInt(1000);
	    
	    farmobject.setAngle(moveAngle);
	    
	    // Calculate move delta
	    final double moveDist = farmobject.getSpeed();
	    final Point moveDelta = new Point((int) (moveDist * Math.cos(moveAngle)),
		    (int) (moveDist * Math.sin(moveAngle)));

	    // Original values
	    final Point pos = farmobject.getPosition();
	    final int size = farmobject.getSize();

	    // Apply bounded move delta
	    final int x = bounded(pos.x + moveDelta.x, 0, FarmPanel.WIDTH - size);
	    final int y = bounded(pos.y + moveDelta.y, 0, FarmPanel.HEIGHT - size);

	    if (pos.x != x || pos.y != y) {
		// Update if changed
	    	
	    
	    if (randInt > 750) {
	    	farmobject.setPosition(new Point(x, y));
	    }
		
	    }
	}
}
