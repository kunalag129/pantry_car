package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import common.ResponseError;
import configs.ApplicationContext;
import external.railway.RailwayService;
import helpers.RailwayServiceHelper;
import org.springframework.web.bind.annotation.*;
import railways.Train;
import railways.TrainStoppage;
import responseParams.PassengerDetails;
import responseParams.PnrDetails;
import responseParams.StationListDetails;
import responseParams.TrainListDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        String srcStationCode = pnrDetails.get("from_station").get("code").textValue().trim();
        String destStationCode = pnrDetails.get("to_station").get("code").textValue().trim();
        String srcStationName = pnrDetails.get("from_station").get("name").textValue();
        String destStationName = pnrDetails.get("to_station").get("name").textValue();
        String trainNum = pnrDetails.get("train_num").textValue();
        String trainName = pnrDetails.get("train_name").textValue();
        String doj = pnrDetails.get("doj").textValue();
        String boardingClass = pnrDetails.get("class").textValue();
        String chartedPrepared = pnrDetails.get("chart_prepared").textValue();
        List<PassengerDetails> passengers = new ArrayList<PassengerDetails>();
        for(int i =0; i< pnrDetails.get("passengers").size(); i++)
        {
            JsonNode temp =  pnrDetails.get("passengers").get(i);
            passengers.add(new PassengerDetails(temp.get("booking_status").textValue(),temp.get("current_status").textValue()));
        }
        JsonNode trainRoute = RailwayService.getTrainRouteDetails(trainNum);
        ArrayList<TrainStoppage> trainStoppages = RailwayServiceHelper.getStationBetweenSourceAndDestinaton(trainRoute, srcStationCode, destStationCode);
        return new PnrDetails(pnr, trainNum, trainName, srcStationCode, destStationCode, srcStationName, destStationName, doj, trainStoppages, boardingClass, chartedPrepared, passengers);
    }

    @RequestMapping(value = "/get_trains_between_locations", method = RequestMethod.GET)
    public @ResponseBody
    TrainListDetails getTrainsBetweenLocations(@RequestParam("src") String sourceCode, @RequestParam("dest") String destinationCode, @RequestParam("date") String date) {
        sourceCode = sourceCode.toUpperCase();
        destinationCode = destinationCode.toUpperCase();

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("fscode", sourceCode);
        details.put("tscode", destinationCode);
        details.put("date", date);
        JsonNode trainList = RailwayService.getTrainsBetweenLocations(details);
        String src = trainList.get("from_station").get("name").textValue();
        String dest = trainList.get("to_station").get("name").textValue();
        return new TrainListDetails(src, dest, sourceCode, destinationCode, date, RailwayServiceHelper.fetchTrainDetails(trainList));
    }

    @RequestMapping(value = "/get_stations_between_locations", method = RequestMethod.GET)
    public @ResponseBody
    StationListDetails getStationsBetweenLocations(@RequestParam("src") String sourceCode, @RequestParam("dest") String destinationCode, @RequestParam("date") String date, @RequestParam("train") String trainNum) {
        sourceCode = sourceCode.toUpperCase();
        destinationCode = destinationCode.toUpperCase();

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("fscode", sourceCode);
        details.put("tscode", destinationCode);
        details.put("date", date);
        JsonNode trainList = RailwayService.getTrainsBetweenLocations(details);
        String src = trainList.get("from_station").get("name").textValue();
        String dest = trainList.get("to_station").get("name").textValue();
        ArrayList<Train> trains = RailwayServiceHelper.fetchTrainDetails(trainList);
        boolean isTrainPresent = RailwayServiceHelper.isTrainPresent(trains, trainNum);
        if(isTrainPresent == false){
            StationListDetails error = new StationListDetails();
            error.setStatus(false);
            error.setResponseCode(400);
            error.setError(new ResponseError(404, "No such Train number present"));
            return error;
        }
        JsonNode trainRoute = RailwayService.getTrainRouteDetails(trainNum);
        String trainName = trainRoute.get("train").get("name").textValue();
        ArrayList<TrainStoppage> trainStoppages = RailwayServiceHelper.getStationBetweenSourceAndDestinaton(trainRoute, sourceCode, destinationCode);
        return new StationListDetails(src, dest, sourceCode, destinationCode, date, trainNum, trainName, trainStoppages);
    }
}
