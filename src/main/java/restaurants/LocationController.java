package restaurants;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.json.JsonObjectBuilder;
import javax.json.Json;

@RequestMapping("/locations")
@RestController
public class LocationController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String post_call(@RequestBody LocationRequestParams loc, BindingResult result) {
        Location l = new Location(0,loc.getAddress(), loc.getCity(), loc.getState(), loc.getPincode());
        JsonObjectBuilder stringBuilder = Json.createObjectBuilder();
        stringBuilder.add("id",Location.addAddress(l));
//        return Location.addAddress(l);
        return stringBuilder.build().toString();
    }
}
