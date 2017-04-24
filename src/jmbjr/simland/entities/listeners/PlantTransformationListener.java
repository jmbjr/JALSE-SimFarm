package jmbjr.simland.entities.listeners;

import jalse.entities.Entity;
import jalse.entities.EntityTypeEvent;
import jalse.entities.EntityTypeListener;
import jmbjr.simland.entities.drawlayer.PlantLayer;
import jmbjr.simland.entities.plants.Grass;
import jmbjr.simland.entities.plants.Plant;
import jmbjr.simland.properties.FarmPlantProperties;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * attached to all animals. triggers when there's a type transformation
 * used to set animal properties when creating animals or (d)evolving child/adult animals
 */
public class PlantTransformationListener implements EntityTypeListener {

    @Override
    public void entityMarkedAsType(final EntityTypeEvent event) {
	final Plant plant = event.getEntity().asType(Plant.class);
	final Class<? extends Entity> type = event.getTypeChange();
	
	if (type.equals(Grass.class)) { 
		plant.setImage(FarmPlantProperties.getImage(type));
	}
	
	//plant subtypes
	if (type.equals(Grass.class)) {
		plant.markAsType(PlantLayer.class);
	}
	}

}
