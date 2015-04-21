package restaurants;

/**
 * Created by kunal.agarwal on 03/04/15.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_details")
public class BankDetail extends Model {

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "bank_account_email")
    private String bankAccountEmail;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "is_valid")
    private Boolean isValid;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="bank_location_id")
    private Location location;

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

//    @Column(name = "created_at")
//    public Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    public Timestamp updatedAt;

    @Transient
    @JsonIgnore
    private String restaurant_internal_id;

    public BankDetail(String bankAccountName, String bankAccountEmail, String bankAccountNumber, String bankName, String ifscCode) {
        this.bankAccountName = bankAccountName;
        this.bankAccountEmail = bankAccountEmail;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
    }


    public static void findOrCreate(BankDetail bankDetail) {
        Session session = appContext.getSession();
        List<Location> results = session.createCriteria(BankDetail.class)
                .add(Restrictions.eq("bankAccountName", bankDetail.getBankAccountName()))
                .add(Restrictions.eq("bankAccountNumber", bankDetail.getBankAccountNumber()))
                .add(Restrictions.eq("restaurant", bankDetail.getRestaurant()))
                .list();
        appContext.closeSession();
        if(results.isEmpty()) {
            bankDetail.location = Location.findOrCreate(bankDetail.location);
            bankDetail.save();
        }
    }
}
