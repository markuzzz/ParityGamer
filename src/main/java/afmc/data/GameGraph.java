/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afmc.data;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class GameGraph {
    private Node[] nodes;

    public GameGraph(Integer numNodes)
    {
        nodes = new Node[numNodes+1];
        
        for(Integer i = 0; i < numNodes+1; i++) {
            nodes[i] = new Node();
        }
    }
    
    public Node getNode(int nodeNumber)
    {
        return this.nodes[nodeNumber];
    }
    
    public Integer getNumberOfNodes()
    {
        return this.nodes.length;
    }
    
    public Set<Integer> getAllNodes()
    {
        Set<Integer> result = new HashSet();
        
        for(Integer i = 0; i < nodes.length; i++) {
            result.add(i);
        }
        
        return result;
    }

    public List<Node> getListOfNodes()
    {
        return new ArrayList<Node>(Arrays.asList(this.nodes));
    }
    
    //Sets progress measure to all 0's for all nodes
    public void initializeProgressMeasures(Integer maxPriority)
    {
        for(Integer i = 0; i < nodes.length; i++) {
            this.nodes[i].progressMeasure = new ProgressMeasure(maxPriority + 1);
        }
    }
    
    @Override
    public String toString()
    {
        String result = "";
        for(Integer i = 0; i < this.nodes.length; i++) {
            result = result+" Node("+i+")"+this.nodes[i].toString()+"\n";
        }

        return result;
    }

}
