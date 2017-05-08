package jmbjr.simland;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import jmbjr.simland.panels.FarmPanel;
import jmbjr.simland.panels.InfoPanel;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * SimFarm entry point
 */
public class SimFarm {

    /**
     * @param args
     * standard main method
     */
    public static void main(final String[] args) {
	// Create window and panel
	final JFrame frame = new JFrame("JALSE-SimLand");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	final FarmPanel fieldPanel = new FarmPanel();
	//super dumb way to tell fieldPanel about infoPanel
	final InfoPanel infoPanel = new InfoPanel(fieldPanel);
	fieldPanel.setInfoPanel(infoPanel);
	frame.add(fieldPanel, BorderLayout.CENTER);
	frame.add(infoPanel, BorderLayout.EAST);
	frame.pack();
	frame.setResizable(false);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }
}
