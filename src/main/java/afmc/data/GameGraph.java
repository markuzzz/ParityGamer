/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afmc.data;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mark
 */
public class GameGraph {
    private State[] states;

    public GameGraph(Integer numStates)
    {
        states = new State[numStates];
        
        for(Integer i = 0; i < numStates; i++) {
            states[i] = new State();
        }
    }
    
    public State getState(int stateNumber)
    {
        return this.states[stateNumber];
    }
    
    public Integer getNumberOfStates()
    {
        return this.states.length;
    }
    
    public Set<Integer> getAllStates()
    {
        Set<Integer> result = new HashSet();
        
        for(Integer i = 0; i < states.length; i++) {
            result.add(i);
        }
        
        return result;
    }

}
