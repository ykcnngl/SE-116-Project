package fsm.core.io;
import fsm.core.FSM;

import java.io.*;
public class CommandParser {
    private final FSM fsm;
    private final Logger logger;

    public CommandParser(FSM fsm) {
        this.fsm = fsm;
        this.logger = new Logger();
    }

    public void parse(String commandLine) {
        if (commandLine == null || commandLine.trim().isEmpty()) {
            return;
        }

        int semicolonIndex = commandLine.indexOf(';');
        if (semicolonIndex != -1) {
            commandLine = commandLine.substring(0, semicolonIndex);
        }
        commandLine = commandLine.trim();
        if (commandLine.isEmpty()) {
            return;
        }

        log(commandLine);

        String[] tokens = commandLine.split("\\s+");
        String command = tokens[0].toUpperCase();

        try {
            switch (command) {
                case "EXIT":
                    log("TERMINATED BY USER");
                    System.out.println("TERMINATED BY USER");
                    System.exit(0);
                    break;
                case "SYMBOLS":
                    handleSymbols(tokens);
                    break;
                case "STATES":
                    handleStates(tokens);
                    break;
                case "INITIAL-STATE":
                    handleInitialState(tokens);
                    break;
                case "FINAL-STATES":
                    handleFinalStates(tokens);
                    break;
                case "TRANSITIONS":
                    handleTransitions(tokens);
                    break;
                case "PRINT":
                    handlePrint(tokens);
                    break;
                case "EXECUTE":
                    handleExecute(tokens);
                    break;
                case "CLEAR":
                    fsm.clear();
                    log("FSM cleared.");
                    System.out.println("FSM cleared.");
                    break;
                case "LOG":
                    handleLog(tokens);
                    break;
                case "COMPILE":
                    handleCompile(tokens);
                    break;
                case "LOAD":
                    handleLoad(tokens);
                    break;
                default:
                    System.out.println("Warning: Invalid command '" + command + "'");
                    log("Warning: Invalid command '" + command + "'");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
            log("Error processing command: " + e.getMessage());
        }
    }

    private void handleSymbols(String[] tokens) {
        if (tokens.length == 1) {
            System.out.println("Declared symbols: " + fsm.getSymbols());
            log("Declared symbols: " + fsm.getSymbols());
            return;
        }
        for (int i = 1; i < tokens.length; i++) {
            fsm.addSymbol(tokens[i]);
        }
    }

    private void handleStates(String[] tokens) {
        if (tokens.length == 1) {
            System.out.println("Declared states: " + fsm.getStates());
            log("Declared states: " + fsm.getStates());
            return;
        }
        for (int i = 1; i < tokens.length; i++) {
            fsm.addState(tokens[i]);
        }
    }
        private void handleInitialState(String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Error: No initial state specified.");
            log("Error: No initial state specified.");
            return;
        }
        fsm.setInitialState(tokens[1]);
    }

    private void handleFinalStates(String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Error: No final states specified.");
            log("Error: No final states specified.");
            return;
        }
        for (int i = 1; i < tokens.length; i++) {
            fsm.addFinalState(tokens[i]);
        }
    }
}
private void handleTransitions(String[] tokens) {
        String combined = String.join(" ", tokens);
        combined = combined.substring("TRANSITIONS".length()).trim();
        String[] transitions = combined.split(",");

        for (String transition : transitions) {
            String[] parts = transition.trim().split("\\s+");
            if (parts.length != 3) {
                System.out.println("Error: Invalid transition format.");
                log("Error: Invalid transition format.");
                continue;
            }
            String symbol = parts[0];
            String fromState = parts[1];
            String toState = parts[2];
            fsm.addTransition(symbol, fromState, toState);
        }

    private void handleExecute(String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Error: No input string provided for execution.");
            log("Error: No input string provided for execution.");
            return;
        }
        fsm.execute(tokens[1]);
    }

     private void handlePrint(String[] tokens) {
        if (tokens.length == 1) {
            fsm.print();
        } else {
            fsm.printToFile(tokens[1]);
        }
    }

    }
private void handleLog(String[] tokens) {
        if (tokens.length == 1) {
            if (logger.isLogging()) {
                logger.stopLogging();
                System.out.println("STOPPED LOGGING");
            } else {
                System.out.println("LOGGING was not enabled");
            }
        } else {
            logger.startLogging(tokens[1]);
        }
    }

    private void handleCompile(String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Error: No filename provided for compile.");
            log("Error: No filename provided for compile.");
            return;
        }
        fsm.compileToFile(tokens[1]);
    }
private void handleLoad(String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Error: No filename provided for load.");
            log("Error: No filename provided for load.");
            return;
        }

        String filename = tokens[1].trim().toLowerCase();

        // Binary dosyaları .ser veya .fs uzantılarına göre ayırt et
        if (filename.endsWith(".ser") || filename.endsWith(".fs")) {
            fsm.loadFromFile(filename);  // FSM içindeki ObjectInputStream ile okuma
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                StringBuilder commandBuffer = new StringBuilder();
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    line = line.trim();
                    if (line.startsWith(";") || line.isEmpty()) {
                        continue;
                    }
                    commandBuffer.append(line).append(" ");
                    if (line.contains(";")) {
                        String fullCommand = commandBuffer.toString();
                        try {
                            parse(fullCommand);
                        } catch (Exception e) {
                            System.out.println("Error processing command at line " + lineNumber + ": " + e.getMessage());
                            log("Error processing command at line " + lineNumber + ": " + e.getMessage());
                        }
                        commandBuffer.setLength(0);
                    }
                }
                System.out.println("Load successful: Commands loaded from '" + filename + "'");
            } catch (IOException e) {
                System.out.println("Error: Could not load commands from text file. " + e.getMessage());
            }
        }
   }

    private void log(String message) {
        if (logger.isLogging()) {
            logger.log(message);
        }
    }
}
