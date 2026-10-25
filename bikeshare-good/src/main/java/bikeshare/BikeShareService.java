package bikeshare;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BikeShareService {
    private final Map<String, Station> stations = new HashMap<>();
    private final Map<String, Rental> activeRentals = new HashMap<>();
    private final RideRepository rideRepository;

    public BikeShareService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
        stations.put("Library", new Station("Library", List.of("L1", "L2")));
        stations.put("Canteen", new Station("Canteen", List.of("C1", "C2")));
        stations.put("Dorm", new Station("Dorm", List.of("D1", "D2")));
    }

    public String rent(String memberId, MemberType memberType, String stationName) {
        if (activeRentals.containsKey(memberId)) {
            throw new IllegalStateException("Member already has a bike");
        }
        String bike = station(stationName).rentBike();
        activeRentals.put(memberId, new Rental(memberId, memberType, bike));
        return bike;
    }

    public RideReceipt returnBike(String memberId, String stationName, int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        Rental rental = activeRentals.remove(memberId);
        if (rental == null) {
            throw new IllegalStateException("Member has no active rental");
        }
        station(stationName).returnBike(rental.bikeId());
        RideReceipt receipt = new RideReceipt(rental.bikeId(), minutes, rental.memberType().feeFor(minutes));
        rideRepository.save(receipt);
        return receipt;
    }

    private Station station(String name) {
        Station station = stations.get(name);
        if (station == null) {
            throw new IllegalArgumentException("Unknown station: " + name);
        }
        return station;
    }
}
