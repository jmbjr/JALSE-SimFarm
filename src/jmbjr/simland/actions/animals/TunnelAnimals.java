package jmbjr.simland.actions.animals;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Adult;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Child;
import jmbjr.simland.entities.animals.Peeker;
import jmbjr.simland.entities.animals.SoilWalker;
import jmbjr.simland.properties.FarmAnimalProperties;

import java.util.Random;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * handle aging animals and converting from child->adult type
 */
public class TunnelAnimals implements Action<Entity> {

	@Override
	public void perform(ActionContext<Entity> context) throws InterruptedException {
		final Field field = context.getActor().asType(Field.class);
		final Set<SoilWalker> tunnellers = field.getEntitiesOfType(SoilWalker.class);
		tunnellers.stream().forEach(tunneller -> {

			if (tunneller.getVisibility() && new Random().nextInt(1000) > 950) {
				tunneller.setVisibility(false); //resume tunnelling
				tunneller.unmarkAsType(Peeker.class);
			} else if (!tunneller.getVisibility() && new Random().nextInt(1000) > 990) {
				tunneller.setVisibility(true); //pop up head for a while
				tunneller.markAsType(Peeker.class);
			}
			
		});
	    }
	
}
