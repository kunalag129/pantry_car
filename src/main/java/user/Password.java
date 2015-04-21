package user;

import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 21/04/15.
 */

@Entity
@Table(name="passwords")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Password extends Model {
    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Column(name = "password")
    private String password;

    @Column(name = "parent_type")
    private String parentType;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Customer customer;

    public static void getDetails(){
        Session session = appContext.getSession();
        Password password = (Password)session.get(Password.class,1);
        appContext.closeSession();
    }

}
