package pos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
/**
 * This class handles the logging of errors in the entire system. The log file
 * is predefined as "pos-log.txt" which will reside in the same folder as this
 * source code. 
 */
public class LogHandler {
    private static final String LOG_FILE = "pos-log.txt";
    private PrintWriter logWriter;
    /**
     * Creates a new loghandler. 
     * @throws IOException
     */
    public LogHandler() throws IOException {
        logWriter = new PrintWriter(new FileWriter(LOG_FILE), true);
    }

    /** 
     * Logs the given exception with the current time.
     * 
     * @param exception the exception to be logged.
     */
    public void logError(Exception exception) {
        var logStringBuilder = new StringBuilder();
        logStringBuilder.append(getFormattedTime());
        logStringBuilder.append(", Exception was thrown: ");
        logStringBuilder.append(exception.getMessage());
        logWriter.println(logStringBuilder);
        exception.printStackTrace(logWriter);
    }

    private String getFormattedTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return currentTime.format(formatter);
    }
}