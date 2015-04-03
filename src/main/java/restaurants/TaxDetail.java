package restaurants;

/**
 * Created by kunal.agarwal on 03/04/15.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tax_details")
public class TaxDetail extends Model{

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "service_tax_number")
    private String serviceTaxNumber;

    @Column(name = "vat_no")
    private String vatNo;

    @Column(name = "food_license")
    private String foodLicense;

    @Column(name = "FSSAI")
    private String fssai;

    @Column(name = "mou_acceptance")
    private String mouAcceptance;

    @Column(name = "is_valid")
    private Boolean isValid;


    @Column(name = "created_at")
    public Timestamp createdAt;

    @Column(name = "updated_at")
    public Timestamp updatedAt;



    public TaxDetail(String serviceTaxNumber, String vatNo, String foodLicense, String fssai, String mouAcceptance) {
        this.serviceTaxNumber = serviceTaxNumber;
        this.vatNo = vatNo;
        this.foodLicense = foodLicense;
        this.fssai = fssai;
        this.mouAcceptance = mouAcceptance;
    }

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @Transient
    @JsonIgnore
    private String restaurant_internal_id;


    public static void findOrCreate(TaxDetail taxDetail) {
        Session session = appContext.getSession();
        List<Location> results = session.createCriteria(TaxDetail.class)
                .add(Restrictions.eq("serviceTaxNumber", taxDetail.getServiceTaxNumber()))
                .add(Restrictions.eq("vatNo", taxDetail.getVatNo()))
                .add(Restrictions.eq("restaurant", taxDetail.getRestaurant()))
                .list();
        appContext.closeSession();
        if(results.isEmpty())
            taxDetail.save();
    }


}
