package html;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HTMLParsing {
    @Getter
    private static List<LineHTML> lines;
    @Getter
    private static List<StationHTML> stations;

    public static void findHTML(String url) {

        try {
            Document document = Jsoup.connect(url).get();

            lines = getLineInfo(document);
            for (LineHTML line : lines) {
                System.out.println(line);
            }

            stations = getStationInfo(document);
            for (StationHTML station : stations) {
                System.out.println(station);
            }


        } catch (IOException e) {
        }
    }


    private static List<LineHTML> getLineInfo(Document document) {
        Elements selectedLines = document.select("span.js-metro-line");
        List<LineHTML> lines = new ArrayList<>();
        for (Element element : selectedLines) {
            String lineName = element.select("span.js-metro-line").text();
            String lineNumber = element.attr("data-line");
            LineHTML lineHTML = new LineHTML(lineName, lineNumber);
            lines.add(lineHTML);
        }
        return lines;
    }

    private static List<StationHTML> getStationInfo(Document document) {
        Elements selectedStations = document.select("p.single-station"); //p.single-station
        List<StationHTML> stations = new ArrayList<>();
        for (Element element : selectedStations) {
            String stationName = element.select("span.name").text();
            String lineNumber = element.parent().attr("data-line");
            boolean hasConnection = element.select("span.t-icon-metroln").hasAttr("title");
            StationHTML stationHTML = new StationHTML(stationName, lineNumber, hasConnection);
            stations.add(stationHTML);
        }
        return stations;
    }
}
