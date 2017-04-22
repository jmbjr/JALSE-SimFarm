package jmbjr.simland.actions.animals;

import java.util.Set;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.ability.Tunneller;
import jmbjr.simland.entities.animals.age.Adult;
import jmbjr.simland.entities.animals.age.Child;
import jmbjr.simland.entities.animals.state.Peeking;
import jmbjr.simland.entities.animals.state.Tunnelling;
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
		final Set<Tunneller> tunnellers = field.getEntitiesOfType(Tunneller.class);
		tunnellers.stream().forEach(tunneller -> {

			if (tunneller.isMarkedAsType(Peeking.class) && new Random().nextInt(1000) > 950) {
				tunneller.setVisibility(false); //resume tunnelling
				tunneller.unmarkAsType(Peeking.class);
				tunneller.markAsType(Tunnelling.class);
			} else if (tunneller.isMarkedAsType(Tunnelling.class) && new Random().nextInt(1000) > 990) {
				tunneller.setVisibility(true); //pop up head for a while
				tunneller.markAsType(Peeking.class);
				tunneller.unmarkAsType(Tunnelling.class);
			}
			
		});
	    }
	
}
