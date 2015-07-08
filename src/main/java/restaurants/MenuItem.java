package restaurants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 08/07/15.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItem extends Model{

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id")
    @Where(clause = "is_active = 1")
    private MenuCategory category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "preference_order")
    private short preferenceOrder;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Transient
    private int categoryId;

    public static MenuItem addNewItem(MenuItem item) {
        item.setCategory(MenuCategory.getCategoryById(item.getCategoryId(), false));
        item.save();
        return item;
    }

    public static MenuItem getItemById(int id) {
        Session session = appContext.getSession();
        MenuItem item = (MenuItem) session.get(MenuItem.class, new Integer(id));
        appContext.closeSession();
        return item;
    }

    public static void toggleItem(int id) {
        MenuItem item = getItemById(id);
        item.setActive(!item.isActive);
        item.update();
    }
}
