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
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.plants.Plant;
import jmbjr.simland.panels.FarmPanel;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle moving animals
 * 
 * 
 */
public class MoveAnimals  {
	
    public static Double distanceToTarget(final Animal source, final Optional <Animal> target) {
    	if (target.equals(Optional.empty()) || target == null) 
    		return (double) 0;
    	else {
	    	double dx = target.get().getPosition().x - source.getPosition().x;
	    	double dy = target.get().getPosition().y - source.getPosition().y;
	    	return Math.sqrt(dx*dx + dy*dy);
    	}
    }
    
    public static Double distanceToPlant(final Animal source, final Optional <Plant> target) {
    	if (target.equals(Optional.empty()) || target == null) 
    		return (double) 0;
    	else {
	    	double dx = target.get().getPosition().x - source.getPosition().x;
	    	double dy = target.get().getPosition().y - source.getPosition().y;
	    	return Math.sqrt(dx*dx + dy*dy);
    	}
    }
    public static Double directionToTarget(final Animal sourceEntity, final Set<Animal> animals, final Class<? extends Entity> species, final Class<? extends Entity> target) {
		// Find closest target in sight
		final Optional<Animal> closestTarget = MoveAnimals.getClosestAnimalOfType(sourceEntity, animals, species, target);
	
		// Check can see any
		if (!closestTarget.isPresent()) {
		    return MoveAnimals.randomDirection(sourceEntity);
		}
	
		// Adult
		final Animal closestTargetAnimal = closestTarget.get();
	
		// Towards
		final Point sourcePos = sourceEntity.getPosition();
		final int dx = closestTargetAnimal.getPosition().x - sourcePos.x;
		final int dy = closestTargetAnimal.getPosition().y - sourcePos.y;
	
		// Convert
		return Math.atan2(dy, dx);
    }
    
    public static Double directionToPlant(final Animal sourceEntity, final Set<Plant> plants, final Class<? extends Entity> species, final Class<? extends Entity> target) {
		// Find closest target in sight
		final Optional<Plant> closestTarget = MoveAnimals.getClosestPlantOfType(sourceEntity, plants, species, target);
	
		// Check can see any
		if (!closestTarget.isPresent()) {
		    return MoveAnimals.randomDirection(sourceEntity);
		}
	
		// Adult
		final Plant closestTargetPlant = closestTarget.get();
	
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
    
    public static Optional<Animal> getClosestAnimalOfType(final Animal animal, final Set<Animal> animals,
	    final Class<? extends Entity> species, final Class<? extends Entity> target) {
		final Point animalPos = animal.getPosition();
		final Integer sightRange = animal.getSightRange();
		// Stream other entities of type
		return animals.stream().filter(not(animal))
							   .filter(isMarkedAsType(species))
							   .filter(isMarkedAsType(target)).filter(p -> {
		    final Point pPos = p.getPosition();
		    // Within range
		    return Math.abs(pPos.x - animalPos.x) <= sightRange && Math.abs(pPos.y - animalPos.y) <= sightRange;
		}).collect(Collectors.minBy((a, b) -> {
		    final Point aPos = a.getPosition();
		    final Point bPos = b.getPosition();
		    // Closest person
		    final int d1 = (aPos.x - animalPos.x) * (aPos.x - animalPos.x)
			    + (aPos.y - animalPos.y) * (aPos.y - animalPos.y);
		    final int d2 = (bPos.x - animalPos.x) * (bPos.x - animalPos.x)
			    + (bPos.y - animalPos.y) * (bPos.y - animalPos.y);
		    return d1 - d2;
		}));
    }

    public static Optional<Plant> getClosestPlantOfType(final Animal animal, final Set<Plant> plants,
    	    final Class<? extends Entity> species, final Class<? extends Entity> target) {
    		final Point animalPos = animal.getPosition();
    		final Integer sightRange = animal.getSightRange();
    		// Stream other entities of type
    		return plants.stream().filter(not(animal))
    							   .filter(isMarkedAsType(species))
    							   .filter(isMarkedAsType(target)).filter(p -> {
    		    final Point pPos = p.getPosition();
    		    // Within range
    		    return Math.abs(pPos.x - animalPos.x) <= sightRange && Math.abs(pPos.y - animalPos.y) <= sightRange;
    		}).collect(Collectors.minBy((a, b) -> {
    		    final Point aPos = a.getPosition();
    		    final Point bPos = b.getPosition();
    		    // Closest person
    		    final int d1 = (aPos.x - animalPos.x) * (aPos.x - animalPos.x)
    			    + (aPos.y - animalPos.y) * (aPos.y - animalPos.y);
    		    final int d2 = (bPos.x - animalPos.x) * (bPos.x - animalPos.x)
    			    + (bPos.y - animalPos.y) * (bPos.y - animalPos.y);
    		    return d1 - d2;
    		}));
        }
    
    public static Double randomDirection(final Animal animal) {
    	return animal.getAngle() + 2. * (ThreadLocalRandom.current().nextDouble() - 0.5);
    }
    
    //separate into new method to get x,y and a new method to setNewPosition (always)
	public static void maybeSetNewPosition(Animal animal, double moveAngle) {
	    Random rand = new Random();	
	    int randInt = rand.nextInt(1000);
	    
	    animal.setAngle(moveAngle);
	    
	    // Calculate move delta
	    final double moveDist = animal.getSpeed();
	    final Point moveDelta = new Point((int) (moveDist * Math.cos(moveAngle)),
		    (int) (moveDist * Math.sin(moveAngle)));

	    // Original values
	    final Point pos = animal.getPosition();
	    final int size = animal.getSizeAdult();

	    // Apply bounded move delta
	    final int x = bounded(pos.x + moveDelta.x, 0, FarmPanel.WIDTH - size);
	    final int y = bounded(pos.y + moveDelta.y, 0, FarmPanel.HEIGHT - size);

	    if (pos.x != x || pos.y != y) {
		// Update if changed
	    	
	    
	    if (randInt > 750) {
	    	animal.setPosition(new Point(x, y));
	    }
		
	    }
	}
}
