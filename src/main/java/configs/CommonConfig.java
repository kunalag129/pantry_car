package configs;

import org.springframework.context.annotation.*;
import restaurants.BankDetail;
import restaurants.Location;
import restaurants.Restaurant;
import restaurants.TaxDetail;
import user.*;

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

    @Bean
    public Customer customer() { return  new Customer();}

    @Bean
    public SocialAuth socialAuth() { return new SocialAuth();}

    @Bean
    public GoogleLogin googleLogin() { return  new GoogleLogin();}

    @Bean
    public FacebookLogin facebookLogin() { return new FacebookLogin();}

    @Bean
    public Password password() { return new Password();}
}
