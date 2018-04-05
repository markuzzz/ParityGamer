package afmc.checker;

import afmc.data.Node;
import java.util.Collections;

public class OddFirst extends LiftingStrategy
{
    public String name() { return "Odd First";}

    public void sort() {
        if (true == this.sorted) {
            return;
        }
        this.sorted = true;

        this.sortedNodes = this.nodes;
        this.sortedNodes.sort((Node n1, Node n2) -> n1.isEven().compareTo(n2.isEven()));
    }
}
