package configs;

import org.springframework.context.annotation.*;
import restaurants.Location;

/**
 * Created by kunal.agarwal on 14/03/15.
 */
@Configuration
public class CommonConfig {
    @Bean
    public Location location() {
        return new Location();
    } }
