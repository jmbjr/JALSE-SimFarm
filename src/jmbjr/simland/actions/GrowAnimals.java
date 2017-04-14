package jmbjr.simland.actions;

import static jalse.entities.Entities.notMarkedAsType;

import java.awt.Point;
import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.AnimalProperties;
import jmbjr.simland.FarmPanel;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Rester;

import java.util.Random;

public class GrowAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Animal> animals = field.getEntitiesOfType(Animal.class);
		animals.stream().filter(notMarkedAsType(Rester.class)).forEach(animal -> {
			Random rand = new Random();
			int randInt = rand.nextInt(1000);
			System.out.println(animal.getSize() + ":" + randInt);
			int newSize = (randInt > 850) ? animal.getSize()+1:animal.getSize();	
			animal.setSize(Math.min(newSize,AnimalProperties.getSize()));
		    
		});
	    }
	
}
