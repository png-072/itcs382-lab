package bikeshare;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class Station {
    private final String name;
    private final Deque<String> availableBikes;

    public Station(String name, Collection<String> bikes) {
        this.name = name;
        this.availableBikes = new ArrayDeque<>(bikes);
    }

    public String rentBike() {
        String bike = availableBikes.pollFirst();
        if (bike == null) {
            throw new IllegalStateException("No bike available at " + name);
        }
        return bike;
    }

    public void returnBike(String bike) {
        availableBikes.addLast(bike);
    }
}
