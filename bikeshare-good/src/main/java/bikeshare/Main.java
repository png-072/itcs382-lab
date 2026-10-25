package bikeshare;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private final BikeShareService service;

    public Main(BikeShareService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        BikeShareService service = new BikeShareService(new CsvRideRepository(Path.of("rides.csv")));
        new Main(service).run();
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <student|staff> <station>, return <memberId> <station> <minutes>, quit");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equals("quit")) {
                    return;
                }
                handle(line);
            }
        }
    }

    private void handle(String line) {
        String[] words = line.split("\\s+");
        try {
            if (words[0].equals("rent") && words.length == 4) {
                String bike = service.rent(words[1], MemberType.from(words[2]), words[3]);
                System.out.println("Rented bike " + bike);
            } else if (words[0].equals("return") && words.length == 4) {
                RideReceipt receipt = service.returnBike(words[1], words[2], Integer.parseInt(words[3]));
                System.out.println("Receipt: bike=" + receipt.bikeId() + ", minutes=" + receipt.minutes()
                        + ", fee=" + receipt.fee() + " baht");
            } else {
                System.out.println("Invalid command");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
