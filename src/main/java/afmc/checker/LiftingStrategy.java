package afmc.checker;

import afmc.data.Node;

import java.util.List;
import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;

public abstract class LiftingStrategy implements Iterable<Node>
{
    public abstract String name();

    protected List<Node> nodes;

    protected List<Node> sortedNodes;

    protected boolean sorted;

    public void addNodes(List<Node> nodes) {
        this.nodes = nodes;
        this.sortedNodes = new ArrayList();
        this.sorted = false;
    }

    public Iterator<Node> iterator() {
       return this.sortedNodes.listIterator();
    }

    abstract public void sort();

    public List<Node> getSortedNodes() {
        return this.sortedNodes;
    }
}
