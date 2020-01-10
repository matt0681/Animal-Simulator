package finiteStateMachines;

import java.util.Map;
import java.util.Stack;

/**
 * This class is a general class for a Finite State Machine.
 * This is used for the entity's AI.
 * System was adapted from pseudo code from an online tutorial. 
 * See link for in depth details.
 * https://gamedevelopment.tutsplus.com/tutorials/finite-state-machines-theory-and-implementation--gamedev-11867
 * @author Matthew
 */
public class FSM {
    
    private Map<Integer, Runnable> states;
    private Stack<Integer> stack;
    
    /**
     * Basic Constructor, receives a Map<Integer, Runnable> of the different states.
     * @param statesMap 
     */
    public FSM(Map statesMap){
        this.states = statesMap;
        this.stack = new Stack();
    }
    
    // Updates the current state and runs the current function.
    public void update(){
        int currentState = getCurrentState();
        
        if(currentState != -1){
            states.get(currentState).run();
        }
    }
    
    // Pops the current state. Aka. removes the top item from the stack.
    public int popState(){
        return stack.pop();
    }
    
    // Pushes the received state. Aka. adds it to the top of the stack.
    public void pushState(int state){
        if(getCurrentState() != state){
            stack.push(state);
        }
    }
    
    // Returns the current state.
    // This is the top of the stack.
    public int getCurrentState(){
        if(stack.size() > 0){
            return stack.get(stack.size()-1);
        } else {
            return -1;
        }
    }
}
