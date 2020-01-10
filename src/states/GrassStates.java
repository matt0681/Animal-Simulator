package states;

import entities.Entity;
import finiteStateMachines.FSM;
import finiteStateMachines.FSMStates;
import java.util.HashMap;
import java.util.Map;

/**
 * The GrassStates class holds all the information for managing a Grass AI.
 * @author Matthew
 */
public class GrassStates {

    private static FSM brain;
    private static Entity ent;
    
    // The constructor creates a Map of states and then 
    // adds it to a new Finite State Machine.
    public GrassStates(Entity ent){
        this.ent = ent;
        Map<Integer, Runnable> states = new HashMap<>();
        states.put(FSMStates.IDLE, () -> idle());
        states.put(FSMStates.DIE, () -> die());
        this.brain = new FSM(states);
        
        this.brain.pushState(FSMStates.IDLE);
        System.out.println("Plant Created");
    }
    
    // Grass sits and does nothing.
    public static void idle(){
        if((int)(ent.getComponent("Seeds").getValue()) <= 0){
            brain.popState();
            brain.pushState(FSMStates.DIE);
        }
    }
    
    // Grass dies.
    public static void die(){
        ent.getComponent("Dead").setvalue(true);
    }
    
    public void update(){
        brain.update();
    }
}
