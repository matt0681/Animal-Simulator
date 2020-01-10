package states;

import entities.Entity;
import finiteStateMachines.FSM;
import finiteStateMachines.FSMStates;
import java.util.HashMap;
import java.util.Map;
import maps.MapManager;
import utilities.RandomNumber;

/**
 * The BirdStates class holds all the information for managing a Bird AI.
 * @author Matthew
 */
public class BirdStates {

    private static FSM brain;
    private static Entity ent;
    private static MapManager manager;
    private static int entPos;
    
    // The constructor creates a Map of states and then 
    // adds it to a new Finite State Machine.
    public BirdStates(Entity ent){
        this.ent = ent;
        Map<Integer, Runnable> states = new HashMap<>();
        states.put(FSMStates.IDLE, () -> idle());
        states.put(FSMStates.FORAGE, () -> forage());
        states.put(FSMStates.DIE, () -> die());
        this.brain = new FSM(states);
        
        brain.pushState(FSMStates.IDLE);
    }
    
    // Bird sits and does nothing.
    public static void idle(){
        System.out.println("Bird is Idling");
        // the bird's hunger is reduced.
        int hunger = ((int) ent.getComponent("Hunger").getValue()) - RandomNumber.getRandomNumberInRange(1, 5);
        ent.getComponent("Hunger").setvalue(hunger);
        
        if(hunger <= 50){
            brain.pushState(FSMStates.FORAGE);
        }
        if(hunger <= 0){
            brain.popState();
            brain.pushState(FSMStates.DIE);
        }
    }
    
    public static void forage() {
        System.out.println("Bird Foraging");
        
         
        
        // find nearest plant
        // Make a node graph with all tiles in a 5 tile radius.
        // Find shortest path to a plant entity, check for weights.
        // Take 3 seeds from the plant.
        
        
//        // If its on top of a plant.
//        int newSeeds = (int) manager.currentMap().getEntityLayer().get(5).get(0).getComponent("Seeds").getValue() - 1;
//        manager.currentMap().getEntityLayer().get(5).get(0).getComponent("Seeds").setvalue(newSeeds);
//        int hunger = ((int) ent.getComponent("Hunger").getValue()) + RandomNumber.getRandomNumberInRange(20, 40);
//        ent.getComponent("Hunger").setvalue(hunger);
//        
//        
//        if(hunger > 50){
//            brain.popState();
//        }
    }
    
    // Bird dies.
    public static void die(){
        ent.getComponent("Dead").setvalue(true);
    }
    
    public void update(int pos, MapManager man){
        this.entPos = pos;
        this.manager = man;
        brain.update();
    }
}
