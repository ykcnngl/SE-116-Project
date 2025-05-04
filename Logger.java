package fsm.core.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private BufferedWriter writer;
    private boolean isLogging = false;

    public void startLogging(String filename) {       // Starts logging to the given filename.
        stopLogging(); // Close previous log if any
        try {
            writer = new BufferedWriter(new FileWriter(filename, false)); // overwrite mode
            isLogging = true;
            System.out.println("Started logging to '" + filename + "'");
        } catch (IOException e) {
            System.out.println("Error: Could not start logging. " + e.getMessage());
        }
    }

    public void stopLogging() {               // Stops logging (closes the file).
        if (isLogging) {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("Error: Could not stop logging properly. " + e.getMessage());
            }
            isLogging = false;
            writer = null;
            System.out.println("Stopped logging.");
        }
    }

    public void log(String message) {           // Logs a message to the file if logging is active.
        if (isLogging && writer != null) {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.out.println("Error: Could not write to log file. " + e.getMessage());
            }
        }
    }

    public boolean isLogging() {      // Checks if logging is active.
        return isLogging;
    }
}
