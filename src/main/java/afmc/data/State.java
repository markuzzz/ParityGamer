package afmc.data;

import java.util.*;

/**
 *
 * @author Mark
 */
public class State
{
    private List<Transition> transitions;
    boolean even;
    Integer priority;

    public State()
    {
        this.transitions = new ArrayList<Transition>();
    }
    
    public void addTransition(Integer to)
    {
        this.transitions.add(new Transition(to));
    }
    
    Set getSuccessors(String label)
    {
        Set<Integer> result = new HashSet<Integer>();
        
        for (Transition t: this.transitions) {
            result.add(t.getTo());
        }
        
        return result;
    }

}
