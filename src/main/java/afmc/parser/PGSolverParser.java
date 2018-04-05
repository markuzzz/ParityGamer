package afmc.parser;

import afmc.data.GameGraph;
import afmc.data.Node;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/* 
    PGSolver format contains header line followed by a line per node.
    Header line: parity <numNodes>
    Line per node: <id> <priority> <owner> <successors> <"name">
    Owner: 0 for even, 1 for odd

    Example:
    parity 4;
    1 3 0 1,3,4 "Europe";
*/
public class PGSolverParser {
    
    public GameGraph parse(String pgsolver)
    {
        Scanner scanner = new Scanner(pgsolver);
        String firstLine = scanner.nextLine();
        String[] values;

        values = this.parseLine(firstLine);
        int numberOfStates = Integer.parseInt(values[1]);

        GameGraph graph = new GameGraph(numberOfStates);
        int highestPrioritySeen = 0;

        //go through file line by line
        while (scanner.hasNextLine()) {
            //extract information and set the values in the nodes
            values = this.parseLine(scanner.nextLine());
            int identifier = Integer.parseInt(values[0]);
            int priority = Integer.parseInt(values[1]);
            int parity = Integer.parseInt(values[2]);
            boolean isEven;
            if (parity == 0) {isEven = true;} else {isEven = false;}
            List<Integer> successors = parseSuccessors(values[3]);
            Node node = graph.getNode(identifier);
            node.even = isEven;
            node.priority = priority;
            
            node.clearTransitions();
            for(Integer suc: successors) {
                node.addTransition(suc);
            }
            
            if(values.length == 5) {
                String name = values[4];
                // Strip the quotes from the name
                name = name.substring(1, name.length()-1);
                node.name = name;
            } else {
                // If no name is provided, use the index as name
                node.name = Integer.toString(identifier);
            }
            
            if(priority > highestPrioritySeen) {
                highestPrioritySeen = priority;
            }
        }
        scanner.close();
        graph.initializeProgressMeasures(highestPrioritySeen);
        return graph;
    }

    private String[] parseLine(String line)
    {
        return (line.substring(0, line.length() - 1)).split(" ");
    }
    
    private List<Integer> parseSuccessors(String line)
    {
        String[] splitLine = line.split(",");
        List<Integer> successors = new ArrayList();
        for(String suc: splitLine) {
            successors.add(Integer.parseInt(suc));
        }
        return successors;
    }
}
