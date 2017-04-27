package jmbjr.simland.entities.plants;

import java.awt.image.BufferedImage;

import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;
import jmbjr.simland.entities.FarmObject;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * generic Plant entity
 */
public interface Plant extends FarmObject {

	@GetAttribute
	BufferedImage[] getImage();
    
    @SetAttribute
    void setImage(BufferedImage[] image);

}
