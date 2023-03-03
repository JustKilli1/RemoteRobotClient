package shared.logging.type;

import shared.logging.LogCategory;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class ConsoleLogger {
    protected LogCategory logCategory;

    public ConsoleLogger(LogCategory logCategory) {
        this.logCategory = logCategory;

    }

    /**
     * Writes A LogMessage to a File
     * @param message The Message that gets written to the File
     * */
    public void logToConsole(List<String> message) {
        message.forEach(System.out::println);
    }

    /**
     * Writes A LogMessage to a File
     * @param message The Message that gets written to the File
     * */
    public void logToConsole(String message) {
        logToConsole(Arrays.asList(message));
    }

}
