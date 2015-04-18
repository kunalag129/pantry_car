package requestParams;

import lombok.Getter;
import lombok.Setter;
import restaurants.Location;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

@Getter @Setter
public class LocationRequestParams {
    private String city;
    private String state;
    private String address;
    private int pincode;
    private int pincode1;
    private int pincode2;

    private String name;

    public Location buildObject(){
        return new Location(this.address, this.city, this.state, this.pincode);
    }
}
