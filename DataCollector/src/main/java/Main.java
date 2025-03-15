import com.fasterxml.jackson.databind.json.JsonMapper;
import html.HTMLParsing;
import html.LineHTML;
import html.StationHTML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String metroURL = "https://skillbox-java.github.io/";
        HTMLParsing.findHTML(metroURL);

        JSONParsing jsonParsing = new JSONParsing();
        String root = "C:\\Users\\rush7\\IdeaProjects\\java_basics\\data";
        List<Path> paths = FilesFinder.findFileExtension(Path.of(root), "json");
        paths.forEach(System.out::println);
        List<StationJSON> stationsJSON = new ArrayList<>();
        for (Path path : paths) {
            List<StationJSON> stationJSONs = jsonParsing.parsingJSON(path);
            for (StationJSON stationJSON : stationJSONs) {
                System.out.println(stationJSON);
                stationsJSON.add(stationJSON);
            }
        }
        List<Path> listCSVPath = FilesFinder.findFileExtension(Path.of(root), "csv");
        CSVParsing csvParsing = new CSVParsing();
        List<StationCSV> stationsCSV = new ArrayList<>();
        for (Path path : listCSVPath) {
            List<StationCSV> stations = csvParsing.parseStationsCSV(path);
            for (StationCSV station : stations) {
                System.out.println(station);
                stationsCSV.add(station);
            }
        }

        List<StationHTML> stations = HTMLParsing.getStations();
        List<LineHTML> lines = HTMLParsing.getLines();

//        createStationInfoJSON(stations, lines, stationsCSV, stationsJSON);

        Map<String, List<String>> stationsMap = new TreeMap<>();
        for (StationHTML station : stations) {
            String lineNumber = station.getLineNumber();
            String stationName = station.getStationName();
            stationsMap.putIfAbsent(lineNumber, new ArrayList<>());
            stationsMap.get(lineNumber).add(stationName);
        }
        JSONObject jsonStationObject = new JSONObject();
        for (Map.Entry<String, List<String>> stringListEntry : stationsMap.entrySet()) {
            JSONArray jsonArray = new JSONArray();
            for (String string : stringListEntry.getValue()) {
                jsonArray.add(string);
            }
            jsonStationObject.put(stringListEntry.getKey(), jsonArray);
        }
        JSONArray linesArray = new JSONArray();
        for (LineHTML line : lines) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number", line.getLineNumber());
            jsonObject.put("name", line.getLineName());
            linesArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stations", jsonStationObject);
        jsonObject.put("lines", linesArray);

        JsonMapper jsonMapper = new JsonMapper();
        Writer writer = new FileWriter("C:\\Users\\rush7\\IdeaProjects\\java_basics\\" +
                "FilesAndNetwork\\DataCollector\\src\\main\\resources\\map.json");

        jsonMapper.writeValue(writer, jsonObject);
    }

    private static void createStationInfoJSON(List<StationHTML> stations, List<LineHTML> lines, List<StationCSV> stationsCSV, List<StationJSON> stationsJSON) throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (StationHTML stationHTML : stations) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", stationHTML.getStationName());

            for (LineHTML lineHTML : lines) {
                if (stationHTML.getLineNumber().equals(lineHTML.getLineNumber())) {
                    jsonObject.put("line", lineHTML.getLineName());
                }
            }

            for (StationCSV stationCSV : stationsCSV) {
                if (stationHTML.getStationName().equals(stationCSV.getName())) {
                    jsonObject.put("date", stationCSV.getDate());
                }
            }

            for (StationJSON stationJSON : stationsJSON) {
                if (stationHTML.getStationName().equals(stationJSON.getStationName())) {
                    jsonObject.put("depth", stationJSON.getStationDepth());
                }
            }

            jsonObject.put("hasConnection", stationHTML.getHasConnection());
            jsonArray.add(jsonObject);
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("stations", jsonArray);
        JsonMapper jsonMapper = new JsonMapper();
        Writer writer = new FileWriter("C:\\Users\\rush7\\IdeaProjects\\java_basics\\" +
                "FilesAndNetwork\\DataCollector\\src\\main\\resources\\stations.json");

        jsonMapper.writeValue(writer, jsonObj);
    }
}
