package pos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LogHandler {
    private static final String LOG_FILE = "pos-log.txt";
    private PrintWriter logWriter;
    
    public LogHandler() throws IOException {
        logWriter = new PrintWriter(new FileWriter(LOG_FILE), true);
    }

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