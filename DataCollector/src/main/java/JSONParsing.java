import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JSONParsing {

    public List<StationJSON> parsingJSON(Path path) throws IOException {

        String jsonContent = Files.readString(path);
        ObjectMapper objectMapper = new JsonMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonContent);
        List<StationJSON> stationJSONList = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            String stationName = node.get("station_name").asText();
            String stationDepth = node.get("depth").asText();
            StationJSON stationJSON = new StationJSON(stationName, stationDepth);
            stationJSONList.add(stationJSON);
        }
        return stationJSONList;
    }
}
