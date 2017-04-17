package jmbjr.simland.actions.animals;

import static jalse.entities.Entities.isMarkedAsType;
import static jalse.entities.Entities.notMarkedAsType;
import static jalse.misc.Identifiable.not;

import java.awt.Point;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Adult;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Child;
import jmbjr.simland.entities.animals.Sleeper;
import jmbjr.simland.panels.FarmPanel;
import jmbjr.simland.properties.FarmAnimalProperties;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle moving animals
 * 
 * 
 */
public class MoveAnimals implements Action<Entity> {

    private static int bounded(final int value, final int min, final int max) {
	return value < min ? min : value > max ? max : value;
    }
    
    private static Double distanceToAdult(final Animal child, final Optional <Animal> adult) {
    	if (adult.equals(Optional.empty())) 
    		return (double) 0;
    	else {
	    	double dx = adult.get().getPosition().x - child.getPosition().x;
	    	double dy = adult.get().getPosition().y - child.getPosition().y;
	    	return Math.sqrt(dx*dx + dy*dy);
    	}
    }

    private static Double directionToAdult(final Animal child, final Set<Animal> people) {
	// Find closest healthy person in sight
	final Optional<Animal> closestAdult = getClosestAnimalOfType(child, people, Adult.class);

	// Check can see any
	if (!closestAdult.isPresent()) {
	    return randomDirection(child);
	}

	// Healthy person
	final Animal adult = closestAdult.get();

	// Towards
	final Point childPos = child.getPosition();
	final int dx = adult.getPosition().x - childPos.x;
	final int dy = adult.getPosition().y - childPos.y;

	// Convert
	return Math.atan2(dy, dx);
    }

    private static Optional<Animal> getClosestAnimalOfType(final Animal animal, final Set<Animal> people,
	    final Class<? extends Entity> type) {
	final Point animalPos = animal.getPosition();
	final Integer sightRange = animal.getSightRange();
	// Stream other entities of type
	return people.stream().filter(not(animal)).filter(isMarkedAsType(type)).filter(p -> {
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

    private static Double randomDirection(final Animal person) {
	return person.getAngle() + 2. * (ThreadLocalRandom.current().nextDouble() - 0.5);
    }

    @Override
    public void perform(final ActionContext<Entity> context) throws InterruptedException {
	final Field field = context.getActor().asType(Field.class);
	final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
	animals.stream().filter(notMarkedAsType(Sleeper.class)).forEach(animal -> {
	    // Get correct move angle
	    double moveAngle;
	    Random rand = new Random();	
	    int randInt = rand.nextInt(1000);
	    
		// Move randomly
	    if (animal.isMarkedAsType(Child.class)) {
	    	// Move towards adult if far enough away and random number says OK
	    	double dist = distanceToAdult(animal, getClosestAnimalOfType(animal, animals, Adult.class));
	    	
	    	if (dist > 4*FarmAnimalProperties.getSizeAdult() && randInt > 100 )  //almost certaintly run to momma
	    		moveAngle = directionToAdult(animal, animals);
	    	else if (dist > 2*FarmAnimalProperties.getSizeAdult() && randInt > 900) //if closer, less of a chance to keep running
	    		moveAngle = directionToAdult(animal, animals);
	    	else
	    		moveAngle = randomDirection(animal);
	    } else {
	    	moveAngle = randomDirection(animal);
	    }
	    
	    animal.setAngle(moveAngle);

	    // Calculate move delta
	    final double moveDist = animal.getSpeed();
	    final Point moveDelta = new Point((int) (moveDist * Math.cos(moveAngle)),
		    (int) (moveDist * Math.sin(moveAngle)));

	    // Original values
	    final Point pos = animal.getPosition();
	    final int size = FarmAnimalProperties.getSizeAdult();

	    // Apply bounded move delta
	    final int x = bounded(pos.x + moveDelta.x, 0, FarmPanel.WIDTH - size);
	    final int y = bounded(pos.y + moveDelta.y, 0, FarmPanel.HEIGHT - size);

	    if (pos.x != x || pos.y != y) {
		// Update if changed
	    	
	    
	    if (randInt > 750) {
	    	animal.setPosition(new Point(x, y));
	    }
		
	    }
	});
    }
}
