package restaurants;

/**
 * Created by kunal.agarwal on 19/03/15.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import common.Model;
import common.Utils;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orders.Order;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import railways.Station;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restaurant extends Model {

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Column(name = "internal_id")
    private String internalId;

    @Column(name = "name")
    private String name;

    @Column(name = "distance")
    private double distance;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "open_time")
    private Time openTime;

    @Column(name = "close_time")
    private Time closeTime;

    @Column(name = "sla_details")
    private int slaDetails;

    @Column(name = "minimum_order_value")
    private double minimumOrder;

    @Column(name = "delivery_charges")
    private double deliveryCharges;

    @Column(name = "is_online")
    private boolean isOnline;

    @Column(name = "friendly_url")
    private String url;

//    @Column(name = "created_at")
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    private Timestamp updatedAt;

//    @Column(name = "location_id")
//    private int locationId;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="location_id")
    private Location location;



    @OneToMany(mappedBy="restaurant", cascade={CascadeType.ALL})
    private List<BankDetail> bankDetails = new ArrayList<BankDetail>();

    @OneToMany(mappedBy="restaurant", cascade={CascadeType.ALL})
    private List<TaxDetail> taxDetails = new ArrayList<TaxDetail>();

    @JsonIgnore
    @OneToOne(mappedBy="restaurant")
    private Menu menu;

    @JsonIgnore
    @OneToMany(mappedBy="restaurant", cascade={CascadeType.ALL})
    private List<Order> orders;

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="station_id")
    private Station station;


    public Restaurant(String name, double distance, String contactNo, Time openTime, Time closeTime, int slaDetails, double minimumOrder, double deliveryCharges, boolean isOnline) {
        this.name = name;
        this.distance = distance;
        this.contactNo = contactNo;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.slaDetails = slaDetails;
        this.minimumOrder = minimumOrder;
        this.deliveryCharges = deliveryCharges;
        this.isOnline = isOnline;
    }

    public Restaurant saveToDb(Restaurant restaurant){
        Session session = appContext.getSession();
        restaurant.updateTimeStamps();
        restaurant.setLocation(Location.findOrCreate(restaurant.getLocation()));
        int locId = (Integer) session.save(restaurant);
        Restaurant temp = null;
        List<Restaurant> results = session.createCriteria(Restaurant.class).add(Restrictions.eq("id", locId)).list();
        temp = results.get(0);
        temp.setInternalId("RST"+locId);
        temp.setUrl(Utils.toPrettyURL(temp.getName()+" "+temp.getId()));
        session.update(temp);
        appContext.closeSession();
        return temp;
    }

    public Restaurant addRestaurant() {
        return saveToDb(this);
    }

    public static Restaurant getRestaurant(String criteria, String id, boolean detailView) {
        Session session = appContext.getSession();
        Restaurant rest = (Restaurant)session.createCriteria(Restaurant.class).add(Restrictions.eq(criteria, id)).list().get(0);
        if(detailView==true) {
            Hibernate.initialize(rest.getBankDetails());
            Hibernate.initialize(rest.getTaxDetails());
            Hibernate.initialize(rest.getLocation());
            Hibernate.initialize(rest.getStation());
        }
        appContext.closeSession();
        return rest;
    }

    public void updateTaxDetails(TaxDetail taxDetail) {
        taxDetail.setRestaurant(this);
        TaxDetail.findOrCreate(taxDetail);
    }

    public void updateBankDetails(BankDetail bankDetail) {
        bankDetail.setRestaurant(this);
        BankDetail.findOrCreate(bankDetail);
    }
}
