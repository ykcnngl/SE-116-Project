import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        // Sürüm numarası
        String versionNo = "FSM v1.0.0";

        // Şu anki tarih ve saat
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm");
        String formattedDateTime = now.format(formatter);

        System.out.println("FSM VERSION" + versionNo + " " + formattedDateTime);


    }
}
