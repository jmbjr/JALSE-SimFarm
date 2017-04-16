package jmbjr.simland.actions;

import static jalse.entities.Entities.notMarkedAsType;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.FarmAnimalProperties;
import jmbjr.simland.FarmPlantProperties;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Plant;
import jmbjr.simland.entities.Sleeper;

import java.util.Random;

public class GrowPlants implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<Plant> plants = field.getEntitiesOfType(Plant.class);
		plants.stream().filter(notMarkedAsType(Sleeper.class)).forEach(plant -> {

			int newAge = (new Random().nextInt(1000) > 970) ? plant.getAge()+1:plant.getAge();	
			//ensure that we don't set animal size past max size
			plant.setAge(Math.min(newAge,FarmPlantProperties.getAgeGrassMax()-1)); //need to either wrap this in a specific block for grass, or make this generic for plants
		});
	    }
}

