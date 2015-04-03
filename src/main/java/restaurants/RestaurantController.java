package restaurants;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
* Created by kunal.agarwal on 24/03/15.
*/

@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    Restaurant post_call(@RequestBody Restaurant restaurant, BindingResult result) {
//        Location l = new Location(loc.getAddress(), loc.getCity(), loc.getState(), loc.getPincode());
//        JsonObjectBuilder stringBuilder = Json.createObjectBuilder();
//        stringBuilder.add("id",restaurant.addRestaurant());
////        return Location.addAddress(l);
//        return stringBuilder.build().toString();

        System.out.println("=====================================");
        System.out.println(restaurant.getLocation().getCity());
        System.out.println("-------------------------------------");
        return restaurant.addRestaurant();
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public @ResponseBody

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Restaurant getRestaurantDetails(@PathVariable("id") String id) {
        return Restaurant.getRestaurant(id, true);
    }

    @RequestMapping(value = "/{id}/update_bank_details", method = RequestMethod.POST)
    public @ResponseBody
    String updateBankDetails(@PathVariable("id") String id, @RequestBody BankDetail bankDetail) {
        Restaurant restaurant = Restaurant.getRestaurant(id,false);
        restaurant.updateBankDetails(bankDetail);
        return "Updated bank details";
    }

    @RequestMapping(value = "/{id}/update_tax_details", method = RequestMethod.POST)
    public @ResponseBody
    String updateTaxDetails(@PathVariable("id") String id, @RequestBody TaxDetail taxDetail) {
        Restaurant restaurant = Restaurant.getRestaurant(id,false);
        restaurant.updateTaxDetails(taxDetail);
        return "Updated tax details";
    }

}
