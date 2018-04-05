package afmc.checker;

import java.util.Collections;

public class Random extends LiftingStrategy
{
    public String name() { return "Random";}

    public void sort() {
        this.sortedNodes = this.nodes;

        Collections.shuffle(this.sortedNodes);
    }
}
