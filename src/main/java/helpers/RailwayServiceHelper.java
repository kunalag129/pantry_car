package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import railways.Station;
import railways.Train;

import java.util.ArrayList;

/**
 * Created by kunal.agarwal on 05/05/15.
 */
public class RailwayServiceHelper {

    private static ArrayList<Station> stationsBetweenAandB(ArrayList<Station> allStations, String a, String b) {

        int i = 0;
        int startIndex = 0;
        int endIndex = allStations.size()-1;
        while(!allStations.get(i).getStationCode().equals(a))
            i++;
        startIndex = i;
        while (!allStations.get(i).getStationCode().equals(b))
            i++;
        endIndex = i+1;
        return new ArrayList<Station>(allStations.subList(startIndex, endIndex));
    }

    public static ArrayList<Station> getStationList(JsonNode trainDetails) {
        ArrayNode routeStations = (ArrayNode)trainDetails.get("train").get("route");
        ArrayList<Station> stations = new ArrayList<Station>();
        for(int i = 0; i < routeStations.size(); i++) {
            JsonNode station = routeStations.get(i);
            stations.add(getStation(station));
        }
        return stations;
    }

    private static Station getStation(JsonNode station) {
        Station temp = new Station();
        temp.setArrivalTime(station.get("arrival_time").textValue());
        temp.setDepartureTime(station.get("departure_time").textValue());
        temp.setStoppageTime(station.get("stop_time").textValue());
        temp.setDay(station.get("day").textValue());
        temp.setStationCode(station.get("station").get("code").textValue().trim());
        temp.setStationName(station.get("station").get("name").textValue());
        return temp;
    }

    public static ArrayList<Station> getStationBetweenSourceAndDestinaton(JsonNode trainDetails, String source, String destination) {
        return stationsBetweenAandB(getStationList(trainDetails), source, destination);
    }


    public static ArrayList<Train> fetchTrainDetails(JsonNode details) {
        ArrayList<Train> trains = new ArrayList<Train>();
        ArrayNode trainDetails = (ArrayNode)details.get("trains");
        for(int i = 0; i<trainDetails.size(); i++) {
            JsonNode train = trainDetails.get(i);
            trains.add(getTrain(train));
        }
        return trains;
    }

    private static Train getTrain(JsonNode train) {
        Train temp = new Train();
        temp.setTrainNum(train.get("number").textValue().trim());
        temp.setTrainName(train.get("name").textValue());
        temp.setSrcArrivalTime(train.get("src_arrival_time").textValue());
        temp.setSrcDepartureTime(train.get("src_departure_time").textValue());
        temp.setDestArrivalTime(train.get("dest_arrival_time").textValue());
        temp.setDestDepartureTime(train.get("dest_departure_time").textValue());
        return temp;
    }

    public static boolean isTrainPresent(ArrayList<Train> trains, String trainNum) {
        for(int i = 0; i<trains.size(); i++)
            if(trains.get(i).getTrainNum().equals(trainNum))
                return true;
        return false;
    }
}
