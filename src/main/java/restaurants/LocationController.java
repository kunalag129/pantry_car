package restaurants;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/locations")
@RestController
public class LocationController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody int post_call(@RequestBody LocationRequestParams loc, BindingResult result) {
        Location l = new Location(0,loc.getAddress(), loc.getCity(), loc.getState(), loc.getPincode());
        return Location.addAddress(l);
    }
}
