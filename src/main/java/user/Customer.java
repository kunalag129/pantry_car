package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Model;
import common.ResponseError;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kunal.agarwal on 18/04/15.
 */

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Column(name = "remember_token")
    private String rememberToken;


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
        if(Customer.getCustomerDetails("emailId", customer.getEmailId())!=null)
            return new Customer().updateResponseAttributes(false, 409, "Record already exists");
        int custId = customer.updateSocialAuth().save();
        if(customer.loginPass!= null) {
            Password pass = new Password(customer.getLoginPass(), "Customer", Customer.getCustomerFromId(custId));
            pass.save();
        }
        return Customer.getCustomerFromId(custId);
    }

    public static Customer getCustomer(String parameter, String value, boolean detailView) {
        Customer customer = null;
        Session session = appContext.getSession();
        List<Customer> customers = session.createCriteria(Customer.class).add(Restrictions.eq(parameter, value)).list();
        if(!customers.isEmpty()) {
            customer = customers.get(0);
            customer.setLoginPass("");
            if (detailView == true) {
                Hibernate.initialize(customer.getGoogleLogin());
                Hibernate.initialize(customer.getFacebookLogin());
            }
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

    public static Customer getCustomerDetails(String parameter, String value) {
        return getCustomer(parameter, value, true);
    }

    @JsonIgnore
    public Password getCustomerPassword(){
        if(getPassword().getParentType()=="Customer")
            return getPassword();
        return null;
    }

    public static String validateLogin(Customer customer) {
        Customer temp = getCustomerDetails("emailId", customer.getEmailId());
        boolean isUserValid = false;

        if(temp!=null) {
            if (customer.getLoginPass() != null && temp.getPassword() != null)
                isUserValid = temp.getPassword().getPassword().equals(customer.getLoginPass());
            if (customer.getGoogleLogin() != null && temp.getGoogleLogin() != null)
                isUserValid = temp.getGoogleLogin().getToken().equals(customer.getGoogleLogin().getToken());
            if (customer.getFacebookLogin() != null && temp.getFacebookLogin() != null)
                isUserValid = temp.getFacebookLogin().getToken().equals(customer.getFacebookLogin().getToken());
        }
        JsonNodeFactory nodeFactory = appContext.getNodeFactory();
        ObjectNode node = nodeFactory.objectNode();
        node.put("emailId",customer.getEmailId());
        if(temp!=null && isUserValid == true) {
            node.put("name", temp.getName());
            node.put("responseCode", 200);
        }
        else {
            node.put("responseCode", 400);
            ObjectNode error = nodeFactory.objectNode();
            error.put("message", "Login failed");
            node.set("error", error);
        }

        node.put("status",isUserValid);

        return node.toString();
    }

    public void updateRememberToken(String token) {
        this.setRememberToken(token);
        this.update();
    }

    public Customer updateResponseAttributes(boolean status, int responseCode, String errorMessage) {
        this.setStatus(status);
        this.setResponseCode(responseCode);
        this.setError(new ResponseError());
        this.getError().setMessage(errorMessage);
        return this;
    }
}
