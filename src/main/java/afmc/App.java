package afmc;

import afmc.data.Node;
import afmc.data.GameGraph;
import afmc.data.ProgressMeasure;
import afmc.parser.PGSolverParser;
import afmc.checker.Checker;
import afmc.checker.InOrder;
import afmc.checker.Random;
import afmc.checker.OddFirst;
import afmc.checker.SelfLoopsFirst;
import afmc.checker.LiftingStrategy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class App
{
    public static void main(String[] args)
    {
        boolean latexOutput = false;
        String liftingTechnique = "self"; 

        // Parse the program arguments
        for (int i = 0; i < args.length-1; i++) {
            if ( args[i].equals("-v") ) {
                Logger.enableVerbose();
            } else if (args[i].equals("-d")) {
                Logger.enableDebug();
            } else if (args[i].equals("-l")) { // Enable latex output
                latexOutput = true;
            } else if (args[i].equals("-t")) {
                i++;
                liftingTechnique = args[i];
            } else {
                System.out.println("Incorrect arguments provided.");
                System.out.println("usage: -v -d (-t technique)? <game>");
                return;
            }
        }

        // The game is always the last argument.
        String gameFilename = args[args.length-1];

        // Read the required data
        String pgsolver = "";
        try {
            pgsolver = App.readFile(gameFilename);
        } catch (IOException e) {
            System.out.println("Reading input files failed.");
            return;
        }

        List<LiftingStrategy> liftingStrategies = new ArrayList();

        switch(liftingTechnique) {
        case "order":
            liftingStrategies.add(new InOrder());
        case "self":
            liftingStrategies.add(new SelfLoopsFirst()); 
            break;
        case "random":
            liftingStrategies.add(new Random());
            break;
        case "odd":
            liftingStrategies.add(new OddFirst());
            break;
        case "all":
            liftingStrategies.add(new Random());
            liftingStrategies.add(new InOrder());
            liftingStrategies.add(new OddFirst());
            liftingStrategies.add(new SelfLoopsFirst()); 
            break;
        default:
            System.out.println("Unknown lifting strategy provided.");
            return;
        }

        if (latexOutput) {
            String strategyNames = liftingStrategies.stream().map(s -> s.name()).reduce("", (a,b) -> a+" & "+b);
            System.out.println("Strategies "+strategyNames+" \\");
            System.out.print("n");
        }

        String winner = "";
        for (LiftingStrategy liftingStrategy : liftingStrategies) {
            // Parse the pgsolver file
            long parseStartTime = System.currentTimeMillis();
            GameGraph game = (new PGSolverParser()).parse(pgsolver);
            Logger.info("Parsing took: "+(System.currentTimeMillis() - parseStartTime)/1000.0);

            Checker checker = new Checker(game);
            Logger.debug(game);

            checker.check(liftingStrategy);

            Logger.debug(game);

            // Output the winner of the initial state
            Node initialNode = game.getNode(0);
            if (initialNode.progressMeasure.isTop()) {
                winner = "odd";
            } else {
                winner = "even";
            }

            if (latexOutput) {
                System.out.print(" & "+checker.getElapsedTime()/1000.0+" s/ "+checker.getIterationsDone());
            } else {
                System.out.println("Time taken "+checker.getElapsedTime()/1000.0+" s");
                System.out.println("Iterations performed: "+checker.getIterationsDone());
                System.out.println("Lifts performed: "+checker.getLiftsPerformed());
                System.out.println("Early max returns: "+ProgressMeasure.earlyMaxReturns);
                System.out.println("Early min returns: "+ProgressMeasure.earlyMinReturns);
                System.out.println(winner+" wins the inital state.");
            }
        }
        
        if (latexOutput) {
            System.out.println(" \\\\");
            System.out.println(liftingTechnique+" & "+winner+" \\");
        }
        return;
    }

    private static String readFile(String filename) throws IOException
    {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            File file = new File(filename);
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } finally {
            reader.close();
        }

        return stringBuilder.toString();
    }
}
