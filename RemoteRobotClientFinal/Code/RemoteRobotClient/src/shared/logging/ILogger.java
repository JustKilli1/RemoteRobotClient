package shared.logging;

public interface ILogger {

    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param message A Custom Message
     * @param ex The occurring Exception
     * @see LogLevel
     * */
    void log(LogLevel logLevel, String message, Exception ex);
    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param message A Custom Message
     * @see LogLevel
     * */
    void log(LogLevel logLevel, String message);
    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param ex The occurring Exception
     * @see LogLevel
     * */
    void log(LogLevel logLevel, Exception ex);
    /**
     * Returns the Name of the Given Logger
     * @return Name of the Logger
     * */
    String getName();



}
