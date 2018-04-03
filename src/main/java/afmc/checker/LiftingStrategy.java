package afmc.checker;

import afmc.data.Node;

import java.util.List;
import java.lang.Iterable;
import java.util.Iterator;

public abstract class LiftingStrategy implements Iterable<Node>
{
    protected List<Node> nodes;

    protected List<Node> sortedNodes;

    public void addNodes(List<Node> nodes) {
        this.nodes = nodes;
        this.sort(); // Rewind reinitializes the object
    }

    public Iterator<Node> iterator() {
       return this.sortedNodes.listIterator();
    }

    abstract public void sort();
}
