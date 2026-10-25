package bikeshare;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CsvRideRepository implements RideRepository {
    private final Path path;

    public CsvRideRepository(Path path) {
        this.path = path;
    }

    @Override
    public void save(RideReceipt receipt) {
        String row = receipt.bikeId() + "," + receipt.minutes() + "," + receipt.fee() + System.lineSeparator();
        try {
            Files.writeString(path, row, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save ride", e);
        }
    }
}
