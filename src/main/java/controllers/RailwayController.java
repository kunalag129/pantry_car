package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import configs.ApplicationContext;
import external.railway.RailwayService;
import helpers.RailwayServiceHelper;
import org.springframework.web.bind.annotation.*;
import railways.Station;
import responseParams.PnrDetails;

import java.util.ArrayList;

/**
 * Created by kunal.agarwal on 05/05/15.
 */

@RequestMapping("/railways")
@RestController
public class RailwayController {

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @RequestMapping(value = "/get_stations_from_pnr/{pnr}", method = RequestMethod.GET)
    public @ResponseBody
    PnrDetails getStationsFromPnr(@PathVariable("pnr") String pnr) {
        JsonNode pnrDetails = RailwayService.getPnrDetails(pnr);
//        String fromStation = pnrDetails.get("from_station").get("code").textValue();
//        String toStation = pnrDetails.get("to_station").get("code").textValue();
        String trainNum = pnrDetails.get("train_num").textValue();
        String fromStationCode = "TDL";
        String toStationCode = "CNB";
        String fromStationName = "Tundla Junction";
        String toStationName = "Kanpur Central";
        String trainName = pnrDetails.get("train_name").textValue();
        JsonNode trainRoute = RailwayService.getTrainRouteDetails("12004");
        ArrayList<Station> stations = RailwayServiceHelper.getStationBetweenFromAndTo(trainRoute, fromStationCode, toStationCode);
        return new PnrDetails(pnr, trainNum, trainName, fromStationCode, toStationCode, fromStationName, toStationName, stations);

//        JsonNodeFactory nodeFactory = appContext.getNodeFactory();
//        ObjectWriter ow = new ObjectMapper().writer();
//        ArrayNode stns = nodeFactory.arrayNode();
//        for(int i = 0; i<stations.size(); i++) {
//            String node = null;
//            try {
//                node = ow.writeValueAsString(stations.get(i));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            stns.add(node);
//        }
//        ObjectNode result = nodeFactory.objectNode();
//        result.put("pnr", pnr);
//        result.put("trainNo", trainNum);
//        result.put("trainName", pnrDetails.get("train_name").textValue());
//        result.put("fromStationCode", fromStationCode);
//        result.put("toStationCode", toStationCode);
//        result.put("fromStationName", fromStationName);
//        result.put("toStationName", toStationName);
//        result.set("stations", stns);
//        ArrayNode an = nodeFactory.arrayNode();
//
//        for(int i = 0; i<stations.size(); i++)
//            an.add(stations.get(i));
//
//        return result.toString();
    }
}
