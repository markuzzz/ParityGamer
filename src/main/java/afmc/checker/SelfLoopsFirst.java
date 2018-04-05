package afmc.checker;

import afmc.data.Node;
import afmc.data.Transition;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.stream.IntStream;

public class SelfLoopsFirst extends LiftingStrategy
{
    public String name() { return "Selfloops first";}

    public void sort() {
        if (this.sorted) {
            return;
        }
        this.sorted = true;

        List<Node> selfLoopNodes = new ArrayList();
        List<Node> otherNodes = new ArrayList();

        // I accidentally used some java 8 features :o
        IntStream.range(0, this.nodes.size()).forEach(i -> {
            Node node = this.nodes.get(i);
            if (node.transitions.stream().map(n -> n.getTo() == i).reduce(false, (a,b) -> a || b)) {
                selfLoopNodes.add(node);
            } else {
                otherNodes.add(node);
            }
        });

        selfLoopNodes.addAll(otherNodes);

        this.sortedNodes = selfLoopNodes;
    }
}
