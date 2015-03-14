package restaurants;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

import configs.ApplicationContext;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
public class Location {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private int pincode;

    public static int addAddress(Location loc) {
        Session session = ApplicationContext.getInstance().getDbFactory().openSession();
        Transaction tx = session.beginTransaction();
        int locId = (Integer) session.save(loc);
        List locations = session.createQuery("FROM Location").list();
        tx.commit();
        session.close();
        for (Iterator iterator =
                     locations.iterator(); iterator.hasNext(); ) {
            System.out.println("Location.addAddress");
            Location loc1 = (Location) iterator.next();
            System.out.println(loc1.getId());
        }
        return locId;
    }
}