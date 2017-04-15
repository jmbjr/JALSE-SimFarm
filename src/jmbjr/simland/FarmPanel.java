package jmbjr.simland;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.Timer;

import jalse.DefaultJALSE;
import jalse.JALSE;
import jalse.entities.Entities;
import jalse.entities.Entity;
import jmbjr.simland.actions.GrowAnimals;
import jmbjr.simland.actions.MoveAnimals;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Rester;
import jmbjr.simland.entities.Grazer;
import jmbjr.simland.entities.TransformationListener;

@SuppressWarnings("serial")
public class FarmPanel extends JPanel implements ActionListener, MouseListener {

    public static final int TICK_INTERVAL = 1000 / 30;

    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    private static void drawElement(final Graphics g, final Animal animal) {
	final Point position = animal.getPosition();
	int size = animal.getSize();    

	g.drawImage(image, position.x - 2, position.y - 2, size,size, null);
    }

    private final JALSE jalse;
    private static BufferedImage image;
    

    public FarmPanel() {
	// Manually ticked JALSE
	jalse = new DefaultJALSE.Builder().setManualEngine().build();
	// Create data model
	createEntities();
	// Size to field size
	setPreferredSize(getField().getSize());
	// Set black background
	setBackground(new Color(20,100,40));
	// Listener for key events
	setFocusable(true);
	addMouseListener(this);
	// Start ticking and rendering (30 FPS)
	new Timer(TICK_INTERVAL, this).start();
	try {
		image = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\animals\\cow.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
	// Tick model
	jalse.resume();
	// Request repaint
	repaint();
    }

    
    private void addAnimalAtRandomPosition() {
    	addAnimalAtRandomPosition(AnimalProperties.getSizeChild());
    }
    
    private void addAnimalAtRandomPosition(int animalSize) {
		final Animal animal = getField().newEntity(Animal.class);
		animal.setPosition(randomPosition());
		animal.setAngle(randomAngle());
		animal.addEntityTypeListener(new TransformationListener());
		animal.markAsType(Grazer.class);
		animal.setSize(animalSize);
		GrowAnimals.checkIfAdult(animal);
    }

    public void adjustPopulation() {
	final int population = AnimalProperties.getPopulation();
	int count = getField().getEntityCount();
	// Increase population
	while (count < population) {
	    addAnimalAtRandomPosition();
	    count++;
	}
	// Decrease population
	while (count > population) {
	    removeRandomAnimal();
	    count--;
	}
    }

    public void adjustSightRange(final Class<? extends Animal> type) {
	final int sightRange = AnimalProperties.getSightRange(type);
	getField().streamEntitiesOfType(type).forEach(a -> a.setSightRange(sightRange));
    }

    private void createEntities() {
	// Create field
	final Field field = jalse.newEntity(Field.ID, Field.class);
	field.setSize(new Dimension(WIDTH, HEIGHT));
	field.scheduleForActor(new MoveAnimals(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
	field.scheduleForActor(new GrowAnimals(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
	reset();
    }

    private Field getField() {
	return jalse.getEntityAsType(Field.ID, Field.class);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
	// Add animal at random position
	final Point point = e.getPoint();
	//final int size = AnimalProperties.getSize();
	
	getField().streamAnimals().filter(a -> {
	    final Point pos = a.getPosition();
	    int size = a.getSize();
	    return pos.x - 5 <= point.x && pos.x + size + 5 >= point.x && pos.y - 5 <= point.y
		    && pos.y + size + 5 >= point.y;
	}).forEach(a -> {
	    // toggle rest state
	    if (!a.isMarkedAsType(Rester.class)) {
	    	a.markAsType(Rester.class);
	    } else {
	    	a.markAsType(Grazer.class);
	    	a.unmarkAsType(Rester.class);
	    }
	});
	
	addAnimalAtRandomPosition();
    }

    @Override
    public void mouseEntered(final MouseEvent e) {}

    @Override
    public void mouseExited(final MouseEvent e) {}

    @Override
    public void mousePressed(final MouseEvent e) {}

    @Override
    public void mouseReleased(final MouseEvent e) {}

    @Override
    protected void paintComponent(final Graphics g) {
	// Draw component as before
	super.paintComponent(g);

	// Draw people
	getField().streamAnimals().forEach(a ->  drawElement(g, a));

	// Sync (Linux fix)
	Toolkit.getDefaultToolkit().sync();
    }

    private Double randomAngle() {
	final Random rand = ThreadLocalRandom.current();
	return rand.nextDouble() * Math.PI * 2;
    }

    private Point randomPosition() {
	final int size = AnimalProperties.getSize();
	final Random rand = ThreadLocalRandom.current();
	return new Point(size + rand.nextInt(WIDTH), size + rand.nextInt(HEIGHT));
    }

    private void removeRandomAnimal() {
	Entities.randomEntity(getField()).ifPresent(Entity::kill);
    }

    public void reset() {
	// Kill them all
	getField().killEntities();
	// Create randomly-placed healthy people
	final int population = AnimalProperties.getPopulation();
	for (int i = 0; i < population; i++) {
	    if (i < 2)  //create two full grown animals
	    	addAnimalAtRandomPosition(AnimalProperties.getSize());
	    else
	    	addAnimalAtRandomPosition();
	}
    }
    
}

