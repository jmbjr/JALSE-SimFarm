package jmbjr.simland.panels;

import java.awt.Dimension;

import javax.swing.JPanel;

import jalse.DefaultJALSE;
import jalse.JALSE;

import jmbjr.simland.entities.FarmObject;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Info;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * Main class for displaying the farm entities
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel  {

	 private final JALSE jalse;
    /**
     * WIDTH = farm width in pixels
     */
    public static final int WIDTH = 200;
    /**
     * HEIGHT = farm height in pixels
     */
    public static final int HEIGHT = 200;
    
	 public InfoPanel(FarmObject farmobject) {
		jalse = new DefaultJALSE.Builder().setManualEngine().build();
		createEntities();
		System.out.println("hi");
		setPreferredSize(getInfo().getSize());
	}
	
    private Info getInfo() {
    	return jalse.getEntityAsType(Info.ID, Info.class);
    }
    
    private void createEntities() {
		// Create field
		final Info info = jalse.newEntity(Info.ID, Info.class);
		info.setSize(new Dimension(WIDTH, HEIGHT));
    }
}
