package jmbjr.simland;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class SimFarm {

    public static void main(final String[] args) {
	// Create window and panel
	final JFrame frame = new JFrame("JALSE-SimLand");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	final FarmPanel fieldPanel = new FarmPanel();
	frame.add(fieldPanel, BorderLayout.CENTER);
	//frame.add(new ControlPanel(zombiesPanel), BorderLayout.EAST);
	frame.pack();
	frame.setResizable(false);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }
}
