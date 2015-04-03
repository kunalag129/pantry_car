package restaurants;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/locations")
@RestController
public class LocationController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody Location post_call(@RequestBody Location loc, BindingResult result) {
//        Location l = loc.buildObject();
//        JsonObjectBuilder stringBuilder = Json.createObjectBuilder();
//        stringBuilder.add("Location", Json.createArrayBuilder().add(loc.addAddress()));
//        return Location.addAddress(l);
//        return stringBuilder.build().toString();
        return  loc.addAddress();
    }
}
