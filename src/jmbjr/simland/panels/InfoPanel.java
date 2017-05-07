package jmbjr.simland.panels;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jmbjr.simland.entities.FarmObject;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * Main class for displaying the farm entities
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel {

	 //private final JALSE jalse;
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
    
    private static JLabel newLabel(final String text) {
	final JLabel label = new JLabel(text, SwingConstants.CENTER);
	label.setAlignmentX(Component.CENTER_ALIGNMENT);
	return label;
    }
    public InfoPanel(final FarmPanel farmPanel) {
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    	
    	// Info
    	add(newLabel("Controls"));
    	final JButton addAnimalButton = new JButton("Add Random Animal");
    	addAnimalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    	addAnimalButton.addActionListener(e -> farmPanel.addAnimalAtSpecificPosition(farmPanel.randomPosition()));
    	add(addAnimalButton);
    	add(Box.createVerticalGlue());
    	// Reset
    	final JButton resetButton = new JButton("Reset Simulation");
    	resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    	resetButton.addActionListener(e -> farmPanel.reset());
    	add(resetButton);
    	
    }
    //InfoPanel needs to Observe whenever our stats change.
    //maybe use JALSE attribute listener?
	 /**
	 * @param farmobject
	 */
//	public InfoPanel(JALSE jalse, FarmObject farmobject) {
//		this.farmImage = ((Animal) farmobject).getImage();
//		this.jalse = jalse;
//		
//		createEntities();
//		// Start ticking and rendering (30 FPS)
//		new Timer(TICK_INTERVAL, this).start();
//		reset();
//		
//		//need to generalize this
//		this.farmobject = farmobject;
//		//this.farmobject.setAge(farmobject.getAge());
//		
//		setPreferredSize(getInfo().getSize());
//		
//		JLabel labelName = new JLabel(farmobject.getName());
//		this.add(labelName, BorderLayout.PAGE_END);
//
//		JLabel labelAge = new JLabel(String.valueOf(((Animal) farmobject).getAge()));
//		this.add(labelAge, BorderLayout.PAGE_END);
//		
//		// check if animal
//		JLabel ilabel = new JLabel(new ImageIcon(getScaledImage(farmImage,farmobject.getSize()*5,farmobject.getSize()*5)));
//		
//        this.add(ilabel);
//        this.setLocation(50,50);
//        this.setVisible(true);
//        
//	}
 
	 private Image getScaledImage(Image srcImg, int w, int h){
		    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2 = resizedImg.createGraphics();

		    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g2.drawImage(srcImg, 0, 0, w, h, null);
		    g2.dispose();

		    return resizedImg;
		}

	 
//    private Info getInfo() {
//    	return this.jalse.getEntityAsType(Info.ID, Info.class);
//    }
//    
//    private void createEntities() {
//		// Create field
//		final Info info = this.jalse.newEntity(Info.ID, Info.class);
//		info.setSize(new Dimension(WIDTH, HEIGHT));
//		info.scheduleForActor(new GetStats(), 0, TICK_INTERVAL, TimeUnit.MILLISECONDS);
//		
//    }
//
//    @Override
//    public void actionPerformed(final ActionEvent e) {
//	// Tick model
//	this.jalse.resume();
//	// Request repaint
//	repaint();
//    }
//    
//    public void reset() {
//	// Kill them all
//	getInfo().killEntities();
//
//    }
}
