package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Map<String, List<String>> stationBikes = new HashMap<>();
    public static Map<String, String> memberBikes = new HashMap<>();
    public static Map<String, Integer> memberTypes = new HashMap<>();

    public static void main(String[] args) {
        stationBikes.put("Library", new ArrayList<>(List.of("L1", "L2")));
        stationBikes.put("Canteen", new ArrayList<>(List.of("C1", "C2")));
        stationBikes.put("Dorm", new ArrayList<>(List.of("D1", "D2")));

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <1=student|2=staff> <station>, return <memberId> <station> <minutes>, quit");
            while (scanner.hasNextLine()) {
                String[] words = scanner.nextLine().trim().split("\\s+");
                if (words[0].equals("quit")) {
                    break;
                }
                try {
                    if (words[0].equals("rent")) {
                        process(false, words);
                    } else if (words[0].equals("return")) {
                        process(true, words);
                    } else {
                        System.out.println("Unknown command");
                    }
                } catch (RuntimeException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public static void process(boolean returning, String[] words) {
        if (!returning) {
            String memberId = words[1];
            int memberType = Integer.parseInt(words[2]);
            List<String> bikes = stationBikes.get(words[3]);
            if (memberBikes.containsKey(memberId) || bikes == null || bikes.isEmpty()) {
                throw new IllegalArgumentException("Cannot rent bike");
            }
            String bike = bikes.remove(0);
            memberBikes.put(memberId, bike);
            memberTypes.put(memberId, memberType);
            print(false, bike, 0, 0);
            return;
        }

        String memberId = words[1];
        List<String> bikes = stationBikes.get(words[2]);
        int minutes = Integer.parseInt(words[3]);
        String bike = memberBikes.remove(memberId);
        Integer memberType = memberTypes.remove(memberId);
        if (bike == null || memberType == null || bikes == null || minutes < 0) {
            throw new IllegalArgumentException("Cannot return bike");
        }
        bikes.add(bike);
        int fee = minutes * (memberType == 1 ? 2 : 3);
        try (FileWriter writer = new FileWriter("rides.csv", true)) {
            writer.write(bike + "," + minutes + "," + fee + System.lineSeparator());
        } catch (IOException e) {
            throw new IllegalStateException("Could not save ride", e);
        }
        print(true, bike, minutes, fee);
    }

    public static void print(boolean receipt, String bike, int minutes, int fee) {
        if (receipt) {
            System.out.println("Receipt: bike=" + bike + ", minutes=" + minutes + ", fee=" + fee + " baht");
        } else {
            System.out.println("Rented bike " + bike);
        }
    }
}
