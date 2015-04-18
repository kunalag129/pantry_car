package restaurants;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
public class Location extends Model {

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private int pincode;

    public static Location findOrCreate(Location loc)
    {
        Session session = appContext.getSession();
        List<Location> results = session.createCriteria(Location.class)
                .add(Restrictions.eq("address", loc.getAddress()))
                .add(Restrictions.eq("city", loc.getCity()))
                .add(Restrictions.eq("state", loc.getState()))
                .add(Restrictions.eq("pincode", loc.getPincode()))
                .list();
        appContext.closeSession();
        if(results.isEmpty())
            return loc.addAddress();
        else
            return results.get(0);
    }

    public Location addAddress() {

//        List locations = session.createQuery("FROM Location").list();

//        for (Iterator iterator =
//                     locations.iterator(); iterator.hasNext(); ) {
//            System.out.println("Location.addAddress");
//            Location loc1 = (Location) iterator.next();
////            System.out.println(loc1.getId());
//        }
        return saveToDb(this);
    }

    public Location saveToDb(Location loc){
        loc.updateTimeStamps();
        Session session = appContext.getSession();
        int locId = (Integer) session.save(loc);
        Location temp = null;
        List<Location> results = session.createCriteria(Location.class).add(Restrictions.eq("id", locId)).list();
        for(Iterator<Location> it = results.iterator();it.hasNext();) {
            temp = it.next();
            System.out.println(temp);
        }
        appContext.closeSession();
        return temp;
    }
}