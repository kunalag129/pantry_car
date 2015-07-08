package restaurants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kunal.agarwal on 08/07/15.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuCategory extends Model
{
    static ApplicationContext appContext = ApplicationContext.getInstance();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "preference_order")
    private short preferenceOrder;

    @Column(name = "is_active")
    private boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<MenuItem> menuItems;

    @Transient
    private String restaurantId;

    public static MenuCategory addNewCategory(MenuCategory category) {
        category.setRestaurant(Restaurant.getRestaurntById(category.getRestaurantId(), true));
        category.save();
        return category;
    }

    public static MenuCategory getCategoryById(int id, boolean getItems) {
        Session session = appContext.getSession();
        MenuCategory category = (MenuCategory) session.get(MenuCategory.class, new Integer(id));
        if(getItems == true)
            Hibernate.initialize(category.getMenuItems());
        appContext.closeSession();
        return category;
    }

    public static void toggleCategory(int id) {
        MenuCategory category = getCategoryById(id, false);
        category.setActive(!category.isActive);
        category.update();
    }

    public static void editCategory(int id, String name) {
        MenuCategory category = getCategoryById(id, false);
        category.setName(name);
        category.update();
    }

    public void loadItems(){
        this.setMenuItems(getCategoryById(this.getId(), true).getMenuItems());
    }
}
