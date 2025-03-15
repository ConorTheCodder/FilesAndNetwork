import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class CSVParsing {

    @SneakyThrows
    public List<StationCSV> parseStationsCSV(Path path) {

        List<StationCSV> stations = new ArrayList<>();
        List<String> strings = Files.readAllLines(path);
        for (int i = 1; i < strings.size(); i++) {
            String[] nameDateArray = strings.get(i).split(",");
            StationCSV stationCSV = new StationCSV(nameDateArray[0], nameDateArray[1]);
            stations.add(stationCSV);
        }

        return stations;
    }
}
