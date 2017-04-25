package jmbjr.simland.entities.listeners;

import jalse.entities.Entity;
import jalse.entities.EntityTypeEvent;
import jalse.entities.EntityTypeListener;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Chicken;
import jmbjr.simland.entities.animals.Cow;
import jmbjr.simland.entities.animals.Pig;
import jmbjr.simland.entities.animals.Worm;
import jmbjr.simland.entities.animals.ability.Ager;
import jmbjr.simland.entities.animals.ability.Disappearer;
import jmbjr.simland.entities.animals.ability.Grower;
import jmbjr.simland.entities.animals.ability.Sleeper;
import jmbjr.simland.entities.animals.ability.Tunneller;
import jmbjr.simland.entities.animals.state.Adult;
import jmbjr.simland.entities.animals.state.Awake;
import jmbjr.simland.entities.animals.state.Child;
import jmbjr.simland.entities.animals.state.Tunnelling;
import jmbjr.simland.entities.drawlayer.AnimalLayer;
import jmbjr.simland.entities.drawlayer.GroundLayer;
import jmbjr.simland.properties.FarmAnimalProperties;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * attached to all animals. triggers when there's a type transformation
 * used to set animal properties when creating animals or (d)evolving child/adult animals
 */
public class AnimalTransformationListener implements EntityTypeListener {

    @Override
    public void entityMarkedAsType(final EntityTypeEvent event) {
	final Animal animal = event.getEntity().asType(Animal.class);
	final Class<? extends Entity> type = event.getTypeChange();
	
	if (	type.equals(Cow.class) || 
			type.equals(Worm.class) || 
			type.equals(Chicken.class)||
			type.equals(Pig.class)) { //maybe add a new type Alive.class ? or something else to bucket these things so Rester and Grazer don't need defined
		animal.setVisibility(true); 
		animal.setSpecies(type);
		
		//it's kind of silly to set sightrange, speed, and size 3 times, but that's the current state of the art
		//note that the variables without child/adult are the ones that are used by the rest of the code
		//child and adult versions are just for storage and recall.
		animal.setSightRangeChild(FarmAnimalProperties.getSightRangeChild(type));
		animal.setSightRangeAdult(FarmAnimalProperties.getSightRangeAdult(type));
		animal.setSightRange(FarmAnimalProperties.getSightRangeAdult(type));
		
		animal.setSpeedChild(FarmAnimalProperties.getSpeedChild(type));
		animal.setSpeedAdult(FarmAnimalProperties.getSpeedAdult(type));
		animal.setSpeed(FarmAnimalProperties.getSpeedAdult(type));
		
		animal.setSizeChild(FarmAnimalProperties.getSizeChild(type));
		animal.setSizeAdult(FarmAnimalProperties.getSizeAdult(type));
		animal.setSize(FarmAnimalProperties.getSizeAdult(type));
		animal.setSizeMaturity(FarmAnimalProperties.getSizeMaturity(type));
		
		animal.setImageChild(FarmAnimalProperties.getImageChild(type));
		animal.setImageAdult(FarmAnimalProperties.getImageAdult(type));
		animal.setImage(FarmAnimalProperties.getImage(type));
		
		animal.setDrowsiness(FarmAnimalProperties.getDrowsiness(type));
		animal.setDrowsinessDelta(FarmAnimalProperties.getDrowsinessDelta(type));
		animal.setDrowsinessLimit(FarmAnimalProperties.getDrowsinessLimit(type));
		animal.setAlertnessDelta(FarmAnimalProperties.getAlertnessDelta(type));
		animal.setAlertnessLimit(FarmAnimalProperties.getAlertnessLimit(type));
		animal.setAge(FarmAnimalProperties.getAge(type));
		animal.setAgeMaturity(FarmAnimalProperties.getAgeMaturity(type));

		//set abilities and state
		animal.markAsType(Ager.class);
		animal.markAsType(Grower.class);
		animal.markAsType(Sleeper.class);
		animal.markAsType(AnimalLayer.class);
		animal.markAsType(Awake.class);
	}
	
	//handle special animals
	if (type.equals(Worm.class)) {
		animal.markAsType(Disappearer.class);
		animal.markAsType(Tunneller.class);
		animal.setVisibility(false);
		animal.markAsType(Tunnelling.class);
		animal.markAsType(GroundLayer.class);
	}

	//set to the saved animal/child values for the animal
	if (type.equals(Adult.class)) { 
		animal.setSightRange(animal.getSightRangeAdult());
		animal.setSpeed(animal.getSpeedAdult());
		animal.setSize(animal.getSizeAdult());	
		animal.setImage(animal.getImageAdult());
	} else if ( type.equals(Child.class)) {
		animal.setSightRange(animal.getSightRangeChild());
		animal.setSpeed(animal.getSpeedChild());
		animal.setSize(animal.getSizeChild());
		animal.setImage(animal.getImageChild());
	}
    }
}
