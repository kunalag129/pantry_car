package orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import common.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 21/05/15.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="order_items")
@JsonIgnoreProperties({ "responseCode", "error", "status" })
public class OrderItem extends Model {


    @JsonIgnore
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="order_id")
    private Order order;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "per_item_cost")
    private double perItemCost;

    @Column(name = "quantity")
    private int quantity;

}
