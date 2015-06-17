package responseParams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurants.Restaurant;

import java.util.List;

/**
 * Created by kunal.agarwal on 13/06/15.
 */

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class RestaurantListDetails extends ResponseParams{
        private String stationCode;
        private String url;
        private List<Restaurant> restaurants;

    public RestaurantListDetails(String stationCode, List<Restaurant> restaurants) {
        this.setStationCode(stationCode);
        for(int i = 0; i< restaurants.size();i++)
        {
            Restaurant restaurant = restaurants.get(i);
            restaurant.setBankDetails(null);
            restaurant.setTaxDetails(null);
        }
        this.setRestaurants(restaurants);
    }

    }
