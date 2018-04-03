package afmc.checker;

import java.util.Collections;

public class Random extends LiftingStrategy
{
    public void sort() {
        this.sortedNodes = this.nodes;

        Collections.shuffle(this.sortedNodes);
    }
}
