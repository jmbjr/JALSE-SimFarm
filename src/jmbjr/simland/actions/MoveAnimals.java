package jmbjr.simland.actions;

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
import jmbjr.simland.FarmAnimalProperties;
import jmbjr.simland.FarmPanel;
import jmbjr.simland.entities.Adult;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Child;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Rester;



public class MoveAnimals implements Action<Entity> {

    private static int bounded(final int value, final int min, final int max) {
	return value < min ? min : value > max ? max : value;
    }

    public static Double directionAwayFromInfected(final Animal animal, final Set<Animal> animals) {
	// Find closest infected to hide from
	final Optional<Animal> closestInfected = getClosestPersonOfType(animal, animals, Animal.class);

	// Cannot see any
	if (!closestInfected.isPresent()) {
	    return randomDirection(animal);
	}

	final Point animalPos = animal.getPosition();
	final Point closestPos = closestInfected.get().getPosition();

	// Away
	final int dx = animalPos.x - closestPos.x;
	final int dy = animalPos.y - closestPos.y;

	// Convert
	return Math.atan2(dy, dx);
    }

    private static Double directionToAdult(final Animal child, final Set<Animal> people) {
	// Find closest healthy person in sight
	final Optional<Animal> closestAdult = getClosestPersonOfType(child, people, Adult.class);

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

    private static Optional<Animal> getClosestPersonOfType(final Animal person, final Set<Animal> people,
	    final Class<? extends Entity> type) {
	final Point personPos = person.getPosition();
	final Integer sightRange = person.getSightRange();
	// Stream other entities of type
	return people.stream().filter(not(person)).filter(isMarkedAsType(type)).filter(p -> {
	    final Point pPos = p.getPosition();
	    // Within range
	    return Math.abs(pPos.x - personPos.x) <= sightRange && Math.abs(pPos.y - personPos.y) <= sightRange;
	}).collect(Collectors.minBy((a, b) -> {
	    final Point aPos = a.getPosition();
	    final Point bPos = b.getPosition();
	    // Closest person
	    final int d1 = (aPos.x - personPos.x) * (aPos.x - personPos.x)
		    + (aPos.y - personPos.y) * (aPos.y - personPos.y);
	    final int d2 = (bPos.x - personPos.x) * (bPos.x - personPos.x)
		    + (bPos.y - personPos.y) * (bPos.y - personPos.y);
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
	animals.stream().filter(notMarkedAsType(Rester.class)).forEach(animal -> {
	    // Get correct move angle
	    double moveAngle;
		// Move randomly
	    if (animal.isMarkedAsType(Child.class)) {
	    	// Move towards adult
	    	moveAngle = directionToAdult(animal, animals);
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
	    final int size = FarmAnimalProperties.getSize();

	    // Apply bounded move delta
	    final int x = bounded(pos.x + moveDelta.x, 0, FarmPanel.WIDTH - size);
	    final int y = bounded(pos.y + moveDelta.y, 0, FarmPanel.HEIGHT - size);

	    if (pos.x != x || pos.y != y) {
		// Update if changed
	    Random rand = new Random();		
	    int randInt = rand.nextInt(1000);
	    if (randInt > 750) {
	    	animal.setPosition(new Point(x, y));
	    }
		
	    }
	});
    }
}
