package orders;

import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import railways.Station;
import restaurants.Restaurant;
import users.Customer;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by kunal.agarwal on 21/05/15.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order extends Model {
    static ApplicationContext appContext = ApplicationContext.getInstance();


    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="station_id")
    private Station station;

    @Column(name = "order_date")
    private Timestamp orderDate;

    @Column(name = "delivery_date")
    private Timestamp deliveryDate;

    @Column(name = "internal_id")
    private String internalId;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "customer_instruction")
    private String customerInstructions;

    @Column(name = "mode_of_payment")
    private String modeOfPayment;

    @Column(name = "amount_billed")
    private double amountBilled;

    @Column(name = "amount_received")
    private double amountReceived;

    @Column(name = "pnr")
    private String pnr;

    @Column(name = "train_no")
    private String trainNo;

    @Column(name = "seat_no")
    private String seatNo;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="offer_id")
    private Offer offer;

    @Column(name = "discount_amount")
    private double discountAmount;

    @Column(name = "discount_reason")
    private String discountReason;

    @OneToMany(mappedBy="order", cascade={CascadeType.ALL})
    private List<OrderItem> orderItems;

    @Transient
    String customerId;

    @Transient
    String restaurantId;

    @Transient
    String stationCode;

    public static Order createNewOrder(Order order) {
        order.setCustomer(Customer.getCustomer("emailId", order.getCustomerId(), true));
        order.setRestaurant(Restaurant.getRestaurant("internalId", order.getRestaurantId(), true));
                order.setStation(Station.getStation(order.getStationCode()));
//        List<OrderItem> orderItems = order.getOrderItems();
//        order.setOrderItems(null);
//        int id = order.save();
//        order.setOrderItems(orderItems);
        for(int i=0;i<order.orderItems.size();i++)
            order.orderItems.get(i).setOrder(order);
        order.save();
        order.setInternalId("PCO"+order.getId());
        order.update();
        return order;
    }

    public static Order getOrderFromId(int id) {
        Session session = appContext.getSession();
        Order order = (Order)session.get(Order.class,id);
        appContext.closeSession();
        return order;
    }
}
