package afmc.data;

import java.util.*;

/**
 *
 * @author Mark
 */
public class Node
{
    List<Transition> transitions;
    public boolean even;
    public Integer priority;
    public String name;

    public Node()
    {
        this.transitions = new ArrayList<Transition>();
    }
    
    public void addTransition(Integer to)
    {
        this.transitions.add(new Transition(to));
    }
    
    public void clearTransitions()
    {
        this.transitions.clear();
    }
    
    Set getSuccessors(String label)
    {
        Set<Integer> result = new HashSet<Integer>();
        
        for (Transition t: this.transitions) {
            result.add(t.getTo());
        }
        
        return result;
    }
    
    @Override
    public String toString()
    {
        String result = "";
        if(this.even) {
            result += " even ";
        } else {
            result += " odd ";
        }
        result += " priority: " +this.priority;
        result += " successors ";
        for (Transition t: this.transitions) {
            result = result +t.getTo() +",";
        }

        return result; 
    }

}
