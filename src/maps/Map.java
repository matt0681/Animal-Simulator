package maps;

import entities.Entity;
import java.util.ArrayList;

/**
 * Map Class simply contains data and getters for the map of tiles.
 * 
 * Each position on the map has 
 *  a. The weight of all the entities occupying that position added together.
 *  b. A ground Entity. (not included in the weight).
 *  c. A list of Entities occupying that position.
 * 
 * @author Matthew
 */
public class Map {
    /*
     * groundLayer: a 1D list of ground Entities (Entities with their ground field set to true)
     * weights: a 1D list of weights.
     * entitityLayer: a 2D Entity array. Each position holds a list of entities on that tile.
     */
    private Entity[] groundLayer;
    private ArrayList<ArrayList<Entity>> entityLayer;
    private double[] weights;
    
    public Map() {
    }
    
    public Map(double[] weightLayer, Entity[] grndLayer, ArrayList<ArrayList<Entity>> entLayer) {
        this.groundLayer = grndLayer;
        this.weights = weightLayer;
        this.entityLayer = entLayer;
    }
    
    
    /*
     * Getters
     */
    public Entity[] getGroundLayer() {
        return this.groundLayer;
    }
    
    public ArrayList<ArrayList<Entity>> getEntityLayer() {
        return this.entityLayer;
    }
    
    public double[] getWeights(){
        return this.weights;
    }
    
    public int getSize(){
        return (int) Math.sqrt(weights.length);
    }
    
    
    /*
     * Setters
     */
    public void setGroundLayer(Entity[] layer) {
        this.groundLayer = layer;
    }
    
    public void setEntityLayer(ArrayList<ArrayList<Entity>> layer) {
        this.entityLayer = layer;
    }
    
    public void setWeights(double[] layer){
        this.weights = layer;
    }
}
