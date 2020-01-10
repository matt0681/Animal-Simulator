package entities;

import java.util.HashMap;


/**
 * Entity class is the main container for the Entity component system.
 * It stores all the possible data an entity can have using Components.
 * Uses a HashMap<String, Component<?>> to store components.
 * @author Matthew
 */
public class Entity {
    
    private HashMap<String, Component<?>> components = new HashMap<>();
    
    /**
     * This method adds a Component to the entity.
     * @param id The string value used to identify the Component stored.
     * @param component The component actually stored
     * @return this (Entity)
     */
    public Entity addComponent(String id, Component component){
        components.put(id, component);
        return this;
    }
    
    /**
     * This method finds and returns the Component requested.
     * @param id The string value of the requested value.
     * @return 
     */
    public Component getComponent(String id){
        return components.get(id);
    }
}
