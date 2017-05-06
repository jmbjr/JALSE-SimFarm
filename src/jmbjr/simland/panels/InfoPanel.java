package jmbjr.simland.panels;

import static jalse.entities.Entities.isMarkedAsType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import jalse.DefaultJALSE;
import jalse.JALSE;

import jmbjr.simland.entities.FarmObject;
import jmbjr.simland.entities.Field;
import jmbjr.simland.entities.Info;
import jmbjr.simland.entities.animals.Animal;
import jmbjr.simland.entities.drawlayer.AnimalLayer;
import jmbjr.simland.entities.drawlayer.GroundLayer;
import jmbjr.simland.entities.drawlayer.PlantLayer;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * Main class for displaying the farm entities
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel {

	 private final JALSE jalse;
    /**
     * TICK_INTERVAL should always be divided by 30 (fps)
     */
    public static final int TICK_INTERVAL = 1000 / 30;
	    
    /**
     * WIDTH = farm width in pixels
     */
    public static final int WIDTH = 200;
    /**
     * HEIGHT = farm height in pixels
     */
    public static final int HEIGHT = 200;
    
    private FarmObject farmobject = null;
    private Image farmImage = null;
    
	 public InfoPanel(FarmObject farmobject) {
		this.farmImage = ((Animal) farmobject).getImage();
		this.farmobject = farmobject;
		
		jalse = new DefaultJALSE.Builder().setManualEngine().build();
		createEntities();
		getInfo().newEntity(farmobject);
		setPreferredSize(getInfo().getSize());
		
		JLabel labelName = new JLabel(farmobject.getName());
		this.add(labelName, BorderLayout.PAGE_END);

		JLabel labelAge = new JLabel(String.valueOf(((Animal) farmobject).getAge()));
		this.add(labelAge, BorderLayout.PAGE_END);
		
		// check if animal
		JLabel ilabel = new JLabel(new ImageIcon(getScaledImage(farmImage,farmobject.getSize()*5,farmobject.getSize()*5)));
		
        this.add(ilabel);
        this.setLocation(50,50);
        this.setVisible(true);
        
	}
 
	 private Image getScaledImage(Image srcImg, int w, int h){
		    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2 = resizedImg.createGraphics();

		    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g2.drawImage(srcImg, 0, 0, w, h, null);
		    g2.dispose();

		    return resizedImg;
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
