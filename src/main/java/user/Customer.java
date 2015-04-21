package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 18/04/15.
 */

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends Model {
    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Column(name = "name")
    private String name;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email_id")
    private String emailId;

    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    private Password password = null;

    @OneToOne(mappedBy="customer", cascade={CascadeType.ALL})
    private GoogleLogin googleLogin = null;

    @OneToOne(mappedBy="customer", cascade={CascadeType.ALL})
    private FacebookLogin facebookLogin = null;

    @Transient
    private String loginPass = null;

    public Customer updateSocialAuth() {
        if(this.getGoogleLogin()!=null)
            ((GoogleLogin)this.getGoogleLogin().updateTimeStamps()).setCustomer(this);
        if(this.getFacebookLogin()!=null)
            ((FacebookLogin)this.getFacebookLogin().updateTimeStamps()).setCustomer(this);
        return this;
    }

    public static Customer register(Customer customer){
        int custId = customer.updateSocialAuth().save();
        if(customer.loginPass!= null) {
            Password pass = new Password(customer.getLoginPass(), "Customer", Customer.getCustomerFromId(custId));
            pass.save();
        }
        return Customer.getCustomerFromId(custId);
    }

    public static Customer getCustomer(String id, boolean detailView) {
        Session session = appContext.getSession();
        Customer customer = (Customer)session.createCriteria(Customer.class).add(Restrictions.eq("emailId", id)).list().get(0);
        if(detailView==true) {
            Hibernate.initialize(customer.getGoogleLogin());
            Hibernate.initialize(customer.getFacebookLogin());
        }
        appContext.closeSession();
        return customer;
    }

    public static Customer getCustomerFromId(int id) {
        Session session = appContext.getSession();
        Customer cust = (Customer)session.get(Customer.class,id);
        appContext.closeSession();
        return cust;
    }

    public static Customer getCustomerDetails(String emailId) {
        return getCustomer(emailId, true);
    }

    @JsonIgnore
    public Password getCustomerPassword(){
        if(getPassword().getParentType()=="Customer")
            return getPassword();
        return null;
    }

    public static boolean validateLogin(Customer customer) {
        Customer temp = getCustomerDetails(customer.getEmailId());
        if(customer.getLoginPass()!=null)
            return temp.getPassword().getPassword().equals(customer.getLoginPass());
        if(customer.getGoogleLogin().getToken()!=null)
            return temp.getGoogleLogin().getToken().equals(customer.getGoogleLogin().getToken());
        if(customer.getFacebookLogin().getToken()!=null)
            return temp.getFacebookLogin().getToken().equals(customer.getFacebookLogin().getToken());
        return false;
    }
}
