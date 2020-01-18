package states;

import entities.Entity;
import finiteStateMachines.FSM;
import finiteStateMachines.FSMStates;
import graphics.Sprites;
import java.util.HashMap;
import java.util.Map;
import maps.MapManager;
import utilities.RandomNumber;

/**
 * The BirdStates class holds all the information for managing a Bird AI.
 *
 * @author Matthew
 */
public class BirdStates {

    private static FSM brain;
    private static Entity ent;
    private static MapManager manager;
    private static int entPos;
    private static int entSubPos;

    // The constructor creates a Map of states and then 
    // adds it to a new Finite State Machine.
    public BirdStates(Entity ent) {
        this.ent = ent;
        Map<Integer, Runnable> states = new HashMap<>();
        states.put(FSMStates.IDLE, () -> idle());
        states.put(FSMStates.FORAGE, () -> forage());
        states.put(FSMStates.EAT, () -> eat());
        states.put(FSMStates.DIE, () -> die());
        this.brain = new FSM(states);

        brain.pushState(FSMStates.IDLE);
    }

    // Bird sits and does nothing.
    public static void idle() {
        System.out.println("Bird is Idling");
        // the bird's hunger is reduced.
        int hunger = ((int) ent.getComponent("Hunger").getValue()) - RandomNumber.getRandomNumberInRange(1, 5);
        ent.getComponent("Hunger").setvalue(hunger);

        if (hunger <= 50) {
            brain.pushState(FSMStates.FORAGE);
        }
        if (hunger <= 0) {
            brain.popState();
            brain.pushState(FSMStates.DIE);
        }
    }

    public static void forage() {
        System.out.println("Bird Foraging");

        int xB = entPos % manager.width_X;
        int yB = entPos / manager.height_Y;
        int[][] locations = new int[manager.height_Y][manager.width_X];
        int smallestPos = -2;
        int smallestSubPos = -2;
        int smallest = 999;

        for (int pos = 0; pos < manager.currentMap().getEntityLayer().size(); pos++) {
            if (!manager.currentMap().getEntityLayer().get(pos).isEmpty()) {
                for (int subPos = 0; subPos < manager.currentMap().getEntityLayer().get(pos).size(); subPos++) {
                    if ((int) manager.currentMap().getEntityLayer().get(pos).get(subPos).getComponent("Sprite").getValue() == Sprites.GRASS) {
                        int x = pos % manager.width_X;
                        int y = pos / manager.height_Y;

                        int delta_X = Math.abs(xB - x);
                        int delta_Y = Math.abs(yB - y);
                        int delta = delta_X + delta_Y;

                        if (delta < smallest) {
                            smallest = delta;
                            smallestPos = pos;
                            smallestSubPos = subPos;
                        }

                        locations[y][x] = delta;
                    }
                }
            }
        }

        Entity temp = manager.currentMap().getEntityLayer().get(entPos).get(entSubPos);
        manager.currentMap().getEntityLayer().get(entPos).remove(entSubPos);
        manager.currentMap().getEntityLayer().get(smallestPos).add(temp);

        brain.pushState(FSMStates.EAT);
    }

    public static void eat() {
        System.out.println("Bird is Eating");

        int hunger = (int) ent.getComponent("Hunger").getValue();
        
        for (int subPos = 0; subPos < manager.currentMap().getEntityLayer().get(entPos).size(); subPos++) {
            if ((int) manager.currentMap().getEntityLayer().get(entPos).get(subPos).getComponent("Sprite").getValue() == Sprites.GRASS) {
                if((int) manager.currentMap().getEntityLayer().get(entPos).get(subPos).getComponent("Seeds").getValue() > 0){
                    int newSeeds = (int) manager.currentMap().getEntityLayer().get(entPos).get(subPos).getComponent("Seeds").getValue() - 1;
                    manager.currentMap().getEntityLayer().get(entPos).get(subPos).getComponent("Seeds").setvalue(newSeeds);
                    hunger += RandomNumber.getRandomNumberInRange(20, 40);
                    ent.getComponent("Hunger").setvalue(hunger);
                }
            }
        }

        if (hunger > 50) {
            brain.popState();
            brain.popState();
        }
    }

    // Bird dies.
    public static void die() {
        ent.getComponent("Dead").setvalue(true);

    }

    public void update(int pos, int subPos, MapManager man) {
        this.entPos = pos;
        this.entSubPos = subPos;
        this.manager = man;
        brain.update();
    }
}
