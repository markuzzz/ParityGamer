package afmc;

import java.util.function.Supplier;

public final class Logger
{
    private static boolean _debugEnabled;
    private static boolean _verboseEnabled;
    
    public static void enableDebug()
    {
        _debugEnabled = true;
    }

    public static boolean debugEnabled()
    {
        return _debugEnabled;
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

    public static void debugLazy(Object line, Supplier val)
    {
        if (_debugEnabled) {
            System.out.println("DEBUG: "+line+val.get());
        }
    }

    public static void info(Object line)
    {
        if (_verboseEnabled) {
            System.out.println("INFO: "+line);
        }
    }
}
