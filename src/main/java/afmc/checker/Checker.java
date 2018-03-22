package afmc.checker;

import afmc.data.GameGraph;
import afmc.data.Node;

public class Checker {
    GameGraph game;
    Integer[] maxProgressMeasure;
    String liftingTechnique;
    
    public Checker(GameGraph game) {
        this.game = game;
        computeMaxProgressMeasure();
    }
    
    public void check(GameGraph game, String liftingTechnique) {
        this.liftingTechnique = liftingTechnique;
        boolean fixedPointReached = false;
        
        //Until no node can be lifted we do not stop
        while(!fixedPointReached) {
            fixedPointReached = true;
            
            //Check for every node if it can be lifted
            for(Integer nodeIndex: game.getAllNodes()) { //note: in future choose order, for now this suffices
                Node node = game.getNode(nodeIndex);
                boolean nodeChange = lift(node);
                if(nodeChange) {fixedPointReached = false;}
            }
        }
    }
    
    /* Repeatedly lifts the progress measure of a node until it can no longer be
     * lifted. Returns whether at least one lift was possible.
    */
    private boolean lift(Node node){
        boolean changed = false;
        boolean fixedPointReached = false;
        
        while(!fixedPointReached) {
            //do lift and check whether something changed
        }
        
        return changed;
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
        
        this.maxProgressMeasure = tempMaxProgressMeasure;
    }
}
