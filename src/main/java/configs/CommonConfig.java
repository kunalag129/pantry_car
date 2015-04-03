package configs;

import org.springframework.context.annotation.*;
import restaurants.BankDetail;
import restaurants.Location;
import restaurants.Restaurant;
import restaurants.TaxDetail;

/**
 * Created by kunal.agarwal on 14/03/15.
 */
@Configuration
public class CommonConfig {
    @Bean
    public Location location() {
        return new Location();
    }

    @Bean
    public Restaurant restaurant() {
        return new Restaurant();
    }

    @Bean
    public BankDetail bankDetail() {
        return new BankDetail();
    }

    @Bean
    public TaxDetail taxDetail() {
        return new TaxDetail();
    }
}
