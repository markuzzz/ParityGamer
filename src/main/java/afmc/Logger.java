package afmc;

public final class Logger
{
    private static boolean _debugEnabled;
    private static boolean _verboseEnabled;
    
    public static void enableDebug()
    {
        _debugEnabled = true;
    }

    public static void enableVerbose()
    {
        _verboseEnabled = true;
    }

    public static void debug(Object line)
    {
        if (_debugEnabled) {
            System.out.println("DEBUG: "+line);
        }
    }

    public static void info(Object line)
    {
        if (_verboseEnabled) {
            System.out.println("INFO: "+line);
        }
    }
}
