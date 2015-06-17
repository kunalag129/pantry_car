package configs;

import orders.Offer;
import orders.Order;
import orders.OrderItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import railways.Station;
import restaurants.*;
import users.*;

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

    @Bean
    public Menu menu() { return  new Menu();}

    @Bean
    public Offer offer() { return new Offer();}

    @Bean
    public Order order() { return new Order();}

    @Bean
    public OrderItem orderItem() { return new OrderItem();}

    @Bean
    public Station station() { return new Station();}
}
