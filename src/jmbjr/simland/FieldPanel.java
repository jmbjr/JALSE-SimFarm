package jmbjr.simland;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.Timer;

import jalse.DefaultJALSE;
import jalse.JALSE;
import jalse.entities.Entities;
import jalse.entities.Entity;
import jmbjr.simland.actions.MoveAnimals;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Animal;
import jmbjr.simland.entities.Plant;
import jmbjr.simland.entities.Rester;
import jmbjr.simland.entities.Roamer;
import jmbjr.simland.entities.TransformationListener;

@SuppressWarnings("serial")
public class FieldPanel extends JPanel implements ActionListener, MouseListener {

    public static final int TICK_INTERVAL = 1000 / 30;

    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    private static void drawElement(final Graphics g, final Animal person) {
	final Point position = person.getPosition();
	final int size = AnimalProperties.getSize();
	g.setColor(Color.BLACK);
	g.fillOval(position.x - 2, position.y - 2, size + 4, size + 4);
	g.setColor(person.getColour());
	g.fillOval(position.x, position.y, size, size);
    }

    private final JALSE jalse;

    public FieldPanel() {
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
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
	// Tick model
	jalse.resume();
	// Request repaint
	repaint();
    }

    private void addPersonAtRandomPosition() {
	final Animal person = getField().newEntity(Animal.class);
	person.setPosition(randomPosition());
	person.setAngle(randomAngle());
//	person.addAttributeListener(Carrier.INFECTION_PERCENTAGE_TYPE, new InfectionListener());
//	person.addAttributeListener(Infected.HUNGER_PERCENTAGE_TYPE, new StarvationListener());
	person.addEntityTypeListener(new TransformationListener());
	person.markAsType(Roamer.class);
    }

    public void adjustPopulation() {
	final int population = AnimalProperties.getPopulation();
	int count = getField().getEntityCount();
	// Increase population
	while (count < population) {
	    addPersonAtRandomPosition();
	    count++;
	}
	// Decrease population
	while (count > population) {
	    removeRandomPerson();
	    count--;
	}
    }

    public void adjustSightRange(final Class<? extends Animal> type) {
	final int sightRange = AnimalProperties.getSightRange(type);
	getField().streamEntitiesOfType(type).forEach(p -> p.setSightRange(sightRange));
    }

    private void createEntities() {
	// Create field
	final Field field = jalse.newEntity(Field.ID, Field.class);
	field.setSize(new Dimension(WIDTH, HEIGHT));
	field.scheduleForActor(new MoveAnimals(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);

	// Create randomly-placed healthy people
	reset();
    }

    private Field getField() {
	return jalse.getEntityAsType(Field.ID, Field.class);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
	// Add animal at random position
//	final Point point = e.getPoint();
//	final int size = AnimalProperties.getSize();
	addPersonAtRandomPosition();
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

    private void removeRandomPerson() {
	Entities.randomEntity(getField()).ifPresent(Entity::kill);
    }

    public void reset() {
	// Kill them all
	getField().killEntities();
	// Create randomly-placed healthy people
	final int population = AnimalProperties.getPopulation();
	for (int i = 0; i < population; i++) {
	    addPersonAtRandomPosition();
	}
    }
}

