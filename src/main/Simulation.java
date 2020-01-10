package main;

import javax.swing.JFrame;

/**
 * The Driver or Main class of the entire Simulation. 
 * This class, Simulation, creates a JFrame and does some
 * initialization in it.
 * @author Matthew
 */
public class Simulation {

    public static void main(String[] args) {
        JFrame window = new JFrame("Chickens");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // This command, "new SimulationPanel()", starts the simulation.
        window.setContentPane(new SimulationPanel());
        
        window.pack();
        window.setVisible(true);
        window.setResizable(false);
        window.setSize(804, 634);
    }
}
