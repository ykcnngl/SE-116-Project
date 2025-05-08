
import fsm.core.FSM;
import fsm.core.io.CommandParser;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String versionNo = "FSM DESIGNER 3.0";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm");
        String formattedDateTime = now.format(formatter);

        System.out.println(versionNo + " " + formattedDateTime);

        FSM fsm = new FSM();
        CommandParser parser = new CommandParser(fsm);

        if (args.length >= 1) {
            String filename = args[0];
            File file = new File(filename);
            if (!file.exists() || !file.isFile() || !file.canRead()) {
                System.out.println("Error: Cannot read file " + filename);
                System.exit(1);
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder commandBuffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith(";") || line.isEmpty()) {
                        continue;
                    }
                    commandBuffer.append(line).append(" ");
                    if (line.contains(";")) {
                        String fullCommand = commandBuffer.toString();
                        parser.parse(fullCommand);
                        commandBuffer.setLength(0);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("? ");
                String line = scanner.nextLine();
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                parser.parse(line);
            }
        }
    }
}
