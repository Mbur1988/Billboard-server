package Tools;

public class Log {

    // declare text colors used by log methods
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    // declare log statement in purple
    private static final String log = ANSI_PURPLE + "log: " + ANSI_RESET;

    /**
     * Print log message to console
     * @param message
     */
    public static void Message(String message) {
        System.out.println(log + message);
    }

    /**
     * Print log confirmation to console in green
     * @param message
     */
    public static void Confirmation(String message) {
        System.out.println(log + ANSI_GREEN + message + ANSI_RESET);
    }

    /**
     * Print log warning to console in yellow
     * @param message
     */
    public static void Warning(String message) {
        System.out.println(log + ANSI_YELLOW + message + ANSI_RESET);
    }

    /**
     * Print log error to console in red
     * @param message
     */
    public static void Error(String message) {
        System.out.println(log + ANSI_RED + message + ANSI_RESET);
    }
}
