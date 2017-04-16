package jmbjr.simland.entities;

import java.awt.Dimension;
import java.util.UUID;
import java.util.stream.Stream;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;
import jalse.entities.annotations.StreamEntities;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.plants.Plant;

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
}