package restaurants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Json;
import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by kunal.agarwal on 16/05/15.
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menus")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu extends Model {

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @JsonIgnore
    @Column(name = "menu_json")
    private String menuDetails;

    @Transient
    private Json menu;

    @Transient
    private String restaurant_internal_id;

    public static Menu setMenu(String restaurant_id, Map<String, Object> details)
    {
        Restaurant rest = Restaurant.getRestaurant("internalId", restaurant_id, true);
        Menu temp;
        String menuJson = "";
        if(rest.getMenu()!=null)
            temp = rest.getMenu();
        else
            temp  = new Menu();
        try {
            menuJson = new ObjectMapper().writeValueAsString(details.get("menu"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(temp.getRestaurant()==null) {
            temp.setRestaurant(rest);
            temp.setMenuDetails(menuJson);
            temp.save();
        }
        else
        {
            temp.setMenuDetails(menuJson);
            temp.update();
        }
        temp.setRestaurant_internal_id(restaurant_id);
        temp.setMenu(new Json(menuJson));
        return temp;
    }

    public static Menu getMenuByRestaurantId(String restaurant_id){
        Restaurant rest = Restaurant.getRestaurant("internalId", restaurant_id, true);
                Menu temp = rest.getMenu();
        temp.setRestaurant_internal_id(rest.getInternalId());
        temp.setMenu(new Json(temp.getMenuDetails()));
        return temp;
    }

}
