package controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import railways.Station;
import responseParams.RestaurantListDetails;
import restaurants.BankDetail;
import restaurants.Restaurant;
import restaurants.TaxDetail;

import java.util.List;

/**
* Created by kunal.agarwal on 24/03/15.
*/

@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    Restaurant post_call(@RequestBody Restaurant restaurant, BindingResult result) {
        return restaurant.addRestaurant();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Restaurant getRestaurant(@RequestParam("url") String url) {
        logger.debug("getRestaurant is called");
        return Restaurant.getRestaurant("url", url, true);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Restaurant getRestaurantDetails(@PathVariable("id") String id) {
        return Restaurant.getRestaurntById(id, true);
    }

    @RequestMapping(value = "/{id}/update_bank_details", method = RequestMethod.POST)
    public @ResponseBody
    String updateBankDetails(@PathVariable("id") String id, @RequestBody BankDetail bankDetail) {
        Restaurant restaurant = Restaurant.getRestaurntById(id, false);
        restaurant.updateBankDetails(bankDetail);
        return "Updated bank details";
    }

    @RequestMapping(value = "/{id}/update_tax_details", method = RequestMethod.POST)
    public @ResponseBody
    String updateTaxDetails(@PathVariable("id") String id, @RequestBody TaxDetail taxDetail) {
        Restaurant restaurant = Restaurant.getRestaurntById(id,false);
        restaurant.updateTaxDetails(taxDetail);
        return "Updated tax details";
    }

    @RequestMapping(value = "/test_app", method = RequestMethod.GET)
    public @ResponseBody
    String test_app() {
        return "Yo Yo, app is up!! :)";
    }

//    @RequestMapping(value="/{id}/menu", method = RequestMethod.POST)
//    public @ResponseBody
//    Menu setMenu(@PathVariable("id") String id,@RequestBody Map<String, Object> menu){
//
////        String abc="";
////        try {
////
////
////            abc = new ObjectMapper().writeValueAsString(menu.get("menu"));
////            System.out.println("=====================================");
////            System.out.println(abc);
////            System.out.println(menu.get("menu").toString());
////            System.out.println(menu.get("menu"));
////            System.out.println("-------------------------------------");
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////        }
////        Menu menu1 = new Menu();
////        menu1.setRestaurant(null);
////        menu1.setRestaurant_internal_id(id);
////        menu1.setMenuDetails(abc);
////        menu1.setMenu(new Json(abc));
////        return menu1;
//        return Menu.setMenu(id,menu);
//    }
//
//    @RequestMapping(value="/{id}/menu", method = RequestMethod.GET)
//    public @ResponseBody
//    Menu getMenu(@PathVariable("id") String id) {
//        return Menu.getMenuByRestaurantId(id);
//    }

    @RequestMapping(value = "/get_restaurants_by_station/{station_code}", method = RequestMethod.GET)
    public @ResponseBody
    RestaurantListDetails getRestaurantsByStation(@PathVariable("station_code") String stationCode) {
        Station station = Station.getStation(stationCode);
        List<Restaurant> restaurants = station.getRestaurants();
        RestaurantListDetails restaurntListDetails = new RestaurantListDetails(stationCode, restaurants);
        return restaurntListDetails;
    }
}
