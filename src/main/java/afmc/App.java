package afmc;

import afmc.data.GameGraph;
import afmc.parser.PGSolverParser;
import afmc.checker.Checker;
import afmc.checker.InOrder;
import afmc.checker.Random;
import afmc.checker.SelfLoopsFirst;
import afmc.checker.LiftingStrategy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class App {
    public static void main( String[] args )
    {
        String liftingTechnique = "self"; 
        String gameFilename = "";

        // Parse the program arguments
        switch(args.length) {
        case 1:
            gameFilename = args[0];
            break;
        case 3:
            liftingTechnique = args[1];
            gameFilename = args[2];
            break;
        default:
            System.out.println("Incorrect arguments provided.");
            System.out.println("usage: (-t technique)? <game>");
            return;
        }

        // Read the required data
        String pgsolver = "";
        try {
            pgsolver = App.readFile(gameFilename);
        } catch (IOException e) {
            System.out.println("Reading input files failed.");
            return;
        }

        // Parse the pgsolver file
        GameGraph game = (new PGSolverParser()).parse(pgsolver);
        Checker checker = new Checker(game);
        System.out.println(game.toString());

        LiftingStrategy liftingStrategy;
        switch(liftingTechnique) {
        case "order":
            liftingStrategy = new InOrder();
        case "self":
            liftingStrategy = new SelfLoopsFirst(); 
            break;
        case "random":
            liftingStrategy = new Random(); 
            break;
        default:
            System.out.println("Unknown lifting strategy provided.");
            return;
        }

        checker.check(liftingStrategy);

        System.out.println(game.toString());

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
