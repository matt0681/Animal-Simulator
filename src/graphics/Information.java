package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;
import main.SimulationPanel;
import maps.MapManager;

/**
 * The Information class creates the large panel at the bottom of the GUI
 * and displays information based on where the user cursor is located in
 * the simulation.
 * @author Matthew
 */
public class Information {
    
    // Variables for the position of the user cursor and a queue for what to display
    // in the information panel.
    // Here the x and y represent the tile coordinates, not the actual GUI (x,y) coords.
    public static int x = 0;
    public static int y = 0;
    private static Queue<String> queue = new LinkedList<String>();
    
    
    // The update method does some calculations for x and y and then updates
    // the queue with a series of lines of text to display in order of the queue
    // to the user in the GUI.
    public static void update(){
        x = (UserEntity.userX-MapManager.offset)/SpriteHandler.tileSize;
        y = (UserEntity.userY-MapManager.offset)/SpriteHandler.tileSize;
        // Shows The (X,Y) and the name of the ground.
        String line1, line2, line3, line4;
        
        line1 = "Coordinates: (" + x + "," + y + ")";
        line2 = "Ground: " + MapManager.getGroundAt(x, y).getComponent("Name").getValue();
        line3 = "Tile Weight: " + MapManager.getWeightAt(x, y);
        
        line4 = "Entities: ";
        if(MapManager.getEntitiesAt(x, y) == null){
            line4 += "None";
        } else {
            for (int i = 0; i < MapManager.getEntitiesAt(x, y).size(); i++) {
                if(i == MapManager.getEntitiesAt(x, y).size()-1){
                    line4 += MapManager.getEntitiesAt(x, y).get(i).getComponent("Name").getValue();
                } else {
                    line4 += MapManager.getEntitiesAt(x, y).get(i).getComponent("Name").getValue() + ", ";
                }
            }
        }
        
        queue.add(line1);
        queue.add(line2);
        queue.add(line3);
        queue.add(line4);
    }
    
    // The draw method calculates where to put each queue item in order and
    // completes some visual organizing to make the GUI look nicer.
    public static Graphics2D draw(Graphics2D g){
        /*
         Designed for an 800x600 panel. The calculations are horrendous so
         change at your own risk.
        */
        
        // Creates a green/turquoise rectangle on the bottom of the screen
        g.setColor(new Color(80, 182, 179));
        g.fillRect(0, SimulationPanel.HEIGHT * 2 / 3,
                SimulationPanel.WIDTH, SimulationPanel.HEIGHT * 1 / 3);

        // Creates an area for text to go.
        g.setColor(new Color(255, 255, 255));
        g.fillRect(SimulationPanel.WIDTH * 1 / 32, SimulationPanel.HEIGHT * 67 / 96,
                SimulationPanel.WIDTH - (SimulationPanel.WIDTH * 2 / 32),
                (SimulationPanel.HEIGHT * 1 / 3) - (SimulationPanel.HEIGHT * 2 / 32));
        
        
        // Creates the text
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.drawString("Information Panel:", SimulationPanel.WIDTH * 5/128, SimulationPanel.HEIGHT * 35/48);
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, 17));
        
        int count = 1;
        while(!queue.isEmpty()) {
            g.drawString(queue.remove(), SimulationPanel.WIDTH * 5/128, (SimulationPanel.HEIGHT * 35/48) + (25*count));
            count++;
        }
        
        return g;
    }
}
