package jmbjr.simland.panels;

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
import jmbjr.simland.actions.animals.AgeAnimals;
import jmbjr.simland.actions.animals.GrowAnimals;
import jmbjr.simland.actions.animals.MoveAnimals;
import jmbjr.simland.actions.animals.SleepAnimals;
import jmbjr.simland.actions.plants.GrowPlants;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.animals.Adult;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.animals.Child;
import jmbjr.simland.entities.listeners.TransformationListener;
import jmbjr.simland.entities.plants.Grass;
import jmbjr.simland.entities.plants.Plant;
import jmbjr.simland.properties.FarmAnimalProperties;
import jmbjr.simland.properties.FarmPlantProperties;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * Main class for displaying the farm entities
 */
@SuppressWarnings("serial")
public class FarmPanel extends JPanel implements ActionListener, MouseListener {

    /**
     * TICK_INTERVAL should always be divided by 30 (fps)
     */
    public static final int TICK_INTERVAL = 1000 / 30;

    /**
     * WIDTH = farm width in pixels
     */
    public static final int WIDTH = 700;
    /**
     * HEIGHT = farm height in pixels
     */
    public static final int HEIGHT = 500;

    
    //should there just be a single drawElement method? 
    private static void drawElement(final Graphics g, final Plant plant) {
	final Point position = plant.getPosition();
	int size = plant.getSize(); 
	int age = plant.getAge();

	g.drawImage(imgGrass[age], position.x - 2, position.y - 2, size,size, null);
    }

    private static void drawElement(final Graphics g, final Animal animal) {
	final Point position = animal.getPosition();
	int size = animal.getSize();    

	g.drawImage(animal.getImage(), position.x - 2, position.y - 2, size,size, null);
	
    }

    private final JALSE jalse;
    private static BufferedImage[] imgGrass = new BufferedImage[10];

    /**
     * FarmPanel initialization. 
     * adds jalse instance, GUI listeners, sets background color,
     * creates the timer, and loads up animal and plant images
     * 
     */
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
		for (int g = 0; g < FarmPlantProperties.getAgeGrassMax();g++) {
			System.out.println(g + " C:\\dev\\JALSE\\JALSE-SimLand\\img\\plants\\grass"+g+".png" );
			imgGrass[g] = ImageIO.read(new File("C:\\dev\\JALSE\\JALSE-SimLand\\img\\plants\\grass"+g+".png"));
		}
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
    
    private void addGrassAtRandomPosition() {
    	addPlantAtPosition(Grass.class, randomPosition(), "Grass");
    }    
    
    private void addAnimalAtRandomPosition(String name) {
    	addAnimalAtPosition(Child.class, randomPosition(), name);
    }
    
    private void addAnimalAtSpecificPosition(Point pos) {
    	addAnimalAtPosition(Child.class, pos, "Cow");
    }
        
    private void addAnimalAtPosition(Class<? extends Animal> maturity, Point position, String name) {
		final Animal animal = getField().newEntity(Animal.class);
		animal.setPosition(position);
		animal.setAngle(randomAngle());
		animal.addEntityTypeListener(new TransformationListener());
		animal.markAsType(maturity);
		animal.setName(name);
    }

    private void addPlantAtPosition(Class<? extends Plant> type, Point position, String name) {
		final Plant plant = getField().newEntity(Plant.class);
		plant.setPosition(position);
		plant.addEntityTypeListener(new TransformationListener());
		plant.markAsType(type);
		plant.setAge(0);
		plant.setSize(FarmPlantProperties.getSizeGrass());
		plant.setName(name);
    }

    private void createEntities() {
	// Create field
	final Field field = jalse.newEntity(Field.ID, Field.class);
	field.setSize(new Dimension(WIDTH, HEIGHT));
	field.scheduleForActor(new MoveAnimals(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
	field.scheduleForActor(new GrowAnimals(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
	field.scheduleForActor(new SleepAnimals(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
	field.scheduleForActor(new AgeAnimals(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
	field.scheduleForActor(new GrowPlants(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
	reset();
    }

    private Field getField() {
	return jalse.getEntityAsType(Field.ID, Field.class);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
	// Add animal at random position
	final Point point = e.getPoint();

	addAnimalAtSpecificPosition(point);
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

	//Draw Plants
	getField().streamPlants().forEach(a ->  drawElement(g, a));
	
	// Draw Animals
	getField().streamAnimals().forEach(a ->  drawElement(g, a));

	// Sync (Linux fix)
	Toolkit.getDefaultToolkit().sync();
    }

    private Double randomAngle() {
	final Random rand = ThreadLocalRandom.current();
	return rand.nextDouble() * Math.PI * 2;
    }

    private Point randomPosition() {
	final int size = FarmAnimalProperties.getMaxSize();
	final Random rand = ThreadLocalRandom.current();
	return new Point(size + rand.nextInt(WIDTH), size + rand.nextInt(HEIGHT));
    }

    /**
     * resets simulation and creates all animal and plant entities
     */
    public void reset() {
	// Kill them all
	getField().killEntities();
	// Create randomly-placed healthy people
	final int animalPopulation = FarmAnimalProperties.getPopulation();
	final int plantPopulation = FarmPlantProperties.getPopulation();
	for (int i = 0; i < animalPopulation; i++) {
	    if (i < 2)  //create two full grown animals
	    	addAnimalAtPosition(Adult.class, randomPosition(),"Cow" + i);
	    else
	    	addAnimalAtRandomPosition("Kid");
	}
	for (int j = 0; j < plantPopulation; j++) {
		addGrassAtRandomPosition();
	}
    }
    
}

