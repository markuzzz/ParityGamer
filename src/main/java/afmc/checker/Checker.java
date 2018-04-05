package afmc.checker;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import afmc.Logger;
import afmc.data.Node;
import afmc.data.GameGraph;
import afmc.data.Transition;
import afmc.data.ProgressMeasure;

public class Checker {
    GameGraph game;
    ProgressMeasure maxProgressMeasure;
    LiftingStrategy liftingStrategy;

    private int iterationsDone = 0;
    private long elapsedTime = 0;
    
    public Checker(GameGraph game) {
        this.game = game;
        computeMaxProgressMeasure();
    }
    
    public void check(LiftingStrategy liftingStrategy)
    {
        long startTime = System.currentTimeMillis();
        this.iterationsDone = 0;

        this.liftingStrategy = liftingStrategy;
        this.liftingStrategy.addNodes(game.getListOfNodes());
        boolean fixedPointReached = false;


        //Until no node can be lifted we do not stop
        while(!fixedPointReached) {
            fixedPointReached = true;

            //System.out.println(this.game);
            //System.out.println("Iteration: "+iteration);
            this.iterationsDone++;
            
            liftingStrategy.sort();
            List<String> nodeNames = liftingStrategy.getSortedNodes().stream().map(n -> n.name).collect(Collectors.toList());
            Logger.debug("Sorted nodes: "+nodeNames);
            //Check for every node if it can be lifted
            for(Node node: liftingStrategy) { //note: in future choose order, for now this suffices
                //Node node = this.game.getNode(nodeIndex);
                boolean nodeChange = lift(node);
                if (nodeChange) {
                    fixedPointReached = false;
                }
            }
        }

        this.elapsedTime = System.currentTimeMillis() - startTime;
    }
    
    /* Repeatedly lifts the progress measure of a node until it can no longer be
     * lifted. Returns whether at least one lift was possible.
    */
    private boolean lift(Node node) {
        boolean changed = false;
        boolean fixedPointReached = false;
        
        while(!fixedPointReached) {
            fixedPointReached = true;
            if (node.even) {
                ProgressMeasure min = ProgressMeasure.min(progSuccesors(node));
                if (!ProgressMeasure.isEqual(min, node.progressMeasure)) {
                    fixedPointReached = false;
                    node.updateProgressMeasure(min);
                    changed = true;
                }
            } else {
                ProgressMeasure max = ProgressMeasure.max(progSuccesors(node));
                if (!ProgressMeasure.isEqual(max, node.progressMeasure)) {
                    fixedPointReached = false;
                    node.updateProgressMeasure(max);
                    changed = true;
                }
            }
        }
        
        return changed;
    }
    
    private ProgressMeasure[] progSuccesors(Node node) {
        ProgressMeasure[] result = new ProgressMeasure[node.transitions.size()];
        for (int i = 0; i < node.transitions.size(); i++) {
            Node succNode = game.getNode(node.transitions.get(i).getTo());
            if (node.priority % 2 == 0) {
                result[i] = ProgressMeasure.leastEqual(succNode.progressMeasure, node.priority);
            } else {
                result[i] = ProgressMeasure.leastGreater(succNode.progressMeasure, node.priority, this.maxProgressMeasure);
            }
        }

        return result;
    }
    
    //Computes and sets the max possible progress measure (counts occurence of priorities in graph)
    private void computeMaxProgressMeasure() {
        Integer[] tempMaxProgressMeasure = new Integer[game.getNode(0).progressMeasure.length];
        
        for(int i = 0; i < tempMaxProgressMeasure.length; i++){
            tempMaxProgressMeasure[i] = 0;
        }
        
        for(Integer nodeIndex: game.getAllNodes()) {
            Node node = game.getNode(nodeIndex);
            if(node.priority % 2 == 1) {
                tempMaxProgressMeasure[node.priority]++;
            }
        }
        
        this.maxProgressMeasure = new ProgressMeasure(tempMaxProgressMeasure);
    }

    public int getIterationsDone() {
        return this.iterationsDone;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }
}
