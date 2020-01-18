package maps;

import entities.Entity;
import states.BirdStates;
import states.GrassStates;
import graphics.SpriteHandler;
import graphics.UserEntity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * MapManager manages the contents of the Map it contains.
 * @author Matthew
 */
public class MapManager {

    // mapLength: the number of tiles in the map.
    // width_X: the number of tiles from left to right.
    // height_Y: the number of tiles from top to bottom.
    private static Map map;
    public static int mapLength;
    public static int width_X;
    public static int height_Y;
    public static int offset = 20;
    private static SpriteHandler sprites;
    
    
    public MapManager(Map map){
        this.map = map;
        this.mapLength = map.getWeights().length;
        this.width_X = (int) Math.sqrt(map.getWeights().length);
        this.height_Y = (int) Math.sqrt(map.getWeights().length);
        sprites = new SpriteHandler();
    }
    
    // updates g with all the ground tiles.
    public static Graphics2D drawGround(Graphics2D g) {
        
        g.setColor(Color.BLACK);
        g.fillRect(offset/2, offset/2, (width_X+1) * SpriteHandler.tileSize, (height_Y+1) * SpriteHandler.tileSize);
        
        // Calculates where each tile should go.
        int x = offset;
        int y = offset;
        for (int pos = 0; pos < map.getGroundLayer().length; pos++) {
            if (((pos % width_X) == 0) && (pos != 0)) {
                y += SpriteHandler.tileSize + 0;
                x = offset;
            }
            g.drawImage(sprites.getSpriteImage((int) map.getGroundLayer()[pos].getComponent("Sprite").getValue()), x, y, null);
            x += SpriteHandler.tileSize + 0;
        }
        
        return g;
    }
    
    
    // Updates the states and positions of the entities.. idk how yet lol.
    public static void updateEntities(MapManager m){
        for (int i = 0; i < map.getWeights().length; i++) {
            if(!map.getEntityLayer().get(i).isEmpty()){
                for (int j = 0; j < map.getEntityLayer().get(i).size(); j++) {
                    String text = (String) map.getEntityLayer().get(i).get(j).getComponent("Sprite Text").getValue();
                    boolean isDead = (boolean) map.getEntityLayer().get(i).get(j).getComponent("Dead").getValue();
                    if(!isDead){
                        if(text.equals("b")) {
                            BirdStates temp = (BirdStates) map.getEntityLayer().get(i).get(j).getComponent("FSM").getValue();
                            temp.update(i, j, m);
                        } else if (text.equals(";")){
                            GrassStates temp = (GrassStates) map.getEntityLayer().get(i).get(j).getComponent("FSM").getValue();
                            temp.update();
                        }
                    }
                }
            }
        }
    }
    
    
    // Draws the current list of entities to g.
    public static Graphics2D drawEntities(Graphics2D g){
        // Calculates where each tile should go.
        int x = offset;
        int y = offset;
        for (int pos = 0; pos < map.getEntityLayer().size(); pos++) {
            if (((pos % width_X) == 0) && (pos != 0)) {
                y += SpriteHandler.tileSize + 0;
                x = offset;
            }
            if(map.getEntityLayer().get(pos).size() > 0) {
                for (int i = 0; i < map.getEntityLayer().get(pos).size(); i++) {
                    boolean isDead = (boolean) map.getEntityLayer().get(pos).get(i).getComponent("Dead").getValue();
                    if(!isDead){
                        g.drawImage(sprites.getSpriteImage((int) map.getEntityLayer().get(pos).get(i).getComponent("Sprite").getValue()), x, y, null);
                    }
                }
            }
            x += SpriteHandler.tileSize + 0;
        }
        
        return g;
    }
    
    
    // Returns the current map
    public static Map currentMap(){
        return map;
    }
    
    public int getMapRootSize() {
        return (int)Math.sqrt(map.getWeights().length);
    }
    
    public int getMapSize() {
        return this.mapLength;
    }
    
    // returns the list of entities at an x,y position instead of an index.
    public static ArrayList<Entity> getEntitiesAt(int x, int y){
        int i = y * width_X + x;
        if(!currentMap().getEntityLayer().get(i).isEmpty()){
            return currentMap().getEntityLayer().get(i);
        } else {
            return null;
        }
    }
    
    public static Entity getGroundAt(int x, int y){
        int i = y * width_X + x;
        return currentMap().getGroundLayer()[i];
    }
    
    public static double getWeightAt(int x, int y) {
        int i = y * width_X + x;
        return currentMap().getWeights()[i];
    }
}
