package shared.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggingUtils {

    /**
     * Utils Method for Logger to get the StackTrace of an Exception as String
     * @param ex Exception that gets turned into a String
     * @return StackTrace from Exception as String
     * */
    public static String getStackTraceAsStr(Exception ex) {
        if(ex == null) return null;
        StringWriter strWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(strWriter);
        ex.printStackTrace(printWriter);
        return strWriter.toString();
    }

    /**
     * Utils Method for Logger to Format a LogMessage
     * @param logLevel LogLevel
     * @param loggerName Name of the calling logger
     * @param message Custom Message
     * @param ex occurring Exception
     * @return Formatted LogMessage
     * @see LogLevel
     * */
    public static String formatMessage(LogLevel logLevel, String loggerName, String message, Exception ex) {
        String exceptionMSG = ex == null ? "" : "\nException: " + getStackTraceAsStr(ex);
        String messageMSG = message == null ? "" : "\nMessage: " + message;
        return "Level: " + logLevel.getName() + "\n" +
                "Logger Name: " + loggerName +
                messageMSG +
                exceptionMSG;
    }

}
