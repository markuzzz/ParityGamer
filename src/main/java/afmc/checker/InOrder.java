package afmc.checker;

public class InOrder extends LiftingStrategy
{
    public String name() { return "In order";}

    public void sort() {
        this.sortedNodes = this.nodes;
    }
}
