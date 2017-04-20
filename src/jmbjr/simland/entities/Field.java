package jmbjr.simland.entities;

import java.awt.Dimension;
import java.util.UUID;
import java.util.stream.Stream;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;
import jalse.entities.annotations.StreamEntities;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.drawlayer.GroundLayer;
import jmbjr.simland.entities.plants.Plant;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * field class for main farm area
 */
public interface Field extends Entity {

    UUID ID = UUID.randomUUID();

    @GetAttribute
    Dimension getSize();

    @SetAttribute
    void setSize(Dimension size);

    @StreamEntities
    Stream<Animal> streamAnimals();
    
    @StreamEntities
    Stream<Plant> streamPlants();   
    
    @StreamEntities
    Stream<GroundLayer> streamGrounders();   
}