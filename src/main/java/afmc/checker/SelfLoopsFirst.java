package afmc.checker;

import afmc.data.Node;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class SelfLoopsFirst extends LiftingStrategy {
    private boolean sorted = false;

    public void sort() {
        if (this.sorted) {
            return;
        }
        this.sorted = true;

        List<Node> selfLoopNodes = new ArrayList();
        List<Node> otherNodes = new ArrayList();

        for (Node node : this.nodes) {
            if (this.isSelfLoopNode(node)) {
                selfLoopNodes.add(node);
            } else {
                otherNodes.add(node);
            }
        }

        selfLoopNodes.addAll(otherNodes);

        this.sortedNodes = selfLoopNodes;
    }

    private boolean isSelfLoopNode(Node node) {
        return false;
    }
}
