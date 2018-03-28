package afmc;

import afmc.checker.Checker;
import afmc.data.GameGraph;
import afmc.parser.PGSolverParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main( String[] args )
    {
        String liftingTechnique = ""; //default algorithm
        String gameFilename = "";
        System.out.println(args);

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

        checker.check("arbitrary");

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
