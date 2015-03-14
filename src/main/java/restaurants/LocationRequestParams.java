package restaurants;

import lombok.Getter;
import lombok.Setter;

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
}
