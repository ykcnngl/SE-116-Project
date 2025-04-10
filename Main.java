import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {

        // VERSION NO
        String versionNo = "FSM v1.0.0";

        // DATE TIME
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm");
        String formattedDateTime = now.format(formatter);

        System.out.println("FSM VERSION: " + versionNo + " " + formattedDateTime);
        System.out.println("?");

    }
    

}
