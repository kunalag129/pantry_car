package restaurants;

/**
 * Created by kunal.agarwal on 19/03/15.
 */
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")
public class Restaurant extends Model{

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
    private int openTime;

    @Column(name = "close_time")
    private int closeTime;

    @Column(name = "sla_details")
    private int slaDetails;

    @Column(name = "minimum_order_value")
    private double minimumOrder;

    @Column(name = "delivery_charges")
    private double deliveryCharges;

    @Column(name = "is_online")
    private boolean isOnline;

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
    private Set<BankDetail> bankDetails = new HashSet<BankDetail>();

    @OneToMany(mappedBy="restaurant", cascade={CascadeType.ALL})
    private Set<TaxDetail> taxDetails = new HashSet<TaxDetail>();

    public Restaurant(String name, double distance, String contactNo, int openTime, int closeTime, int slaDetails, double minimumOrder, double deliveryCharges, boolean isOnline) {
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

        BankDetail bd = new BankDetail("aaa", "aaaa", "123455", "dsds", "324dsd");
        bd.setLocation(restaurant.getLocation());

        TaxDetail td = new TaxDetail("1234", "1233", "3344", "23", "2343");

//        int loc1Id = (Integer) session.save(bd);



//        Location loc = new Location("abc", "delhi", "up", 123456);
////        loc.addAddress();
//        restaurant.setLocation(loc);


        int locId = (Integer) session.save(restaurant);
        Restaurant temp = null;
        List<Restaurant> results = session.createCriteria(Restaurant.class).add(Restrictions.eq("id", locId)).list();
        temp = results.get(0);
        temp.setInternalId("RST"+locId);
        bd.setRestaurant(temp);
        td.setRestaurant(temp);
        temp.bankDetails.add(bd);
        temp.taxDetails.add(td);
        System.out.println("=====================================");
        System.out.println(temp);
        System.out.println("-------------------------------------");
        session.update(temp);
//        for(Iterator<Restaurant> it = results.iterator();it.hasNext();) {
//            temp = it.next();
//            System.out.println(temp);
//        }

        appContext.closeSession();
        return temp;
    }

    public Restaurant addRestaurant() {
        return saveToDb(this);
    }

    public static Restaurant getRestaurant(String id, boolean detailView) {
        Session session = appContext.getSession();
        Restaurant rest = (Restaurant)session.createCriteria(Restaurant.class).add(Restrictions.eq("internalId", id)).list().get(0);
        if(detailView==true) {
            Hibernate.initialize(rest.getBankDetails());
            Hibernate.initialize(rest.getTaxDetails());
            Hibernate.initialize(rest.getLocation());
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
