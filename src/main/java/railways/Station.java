package railways;

import common.Model;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import restaurants.Restaurant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal.agarwal on 21/05/15.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="stations")
public class Station extends Model {
    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Column(name = "station_full_name")
    private String stationFullName;

    @Column(name = "state")
    private String state;

    @Column(name = "station_short_name")
    private String stationShortName;

    @Column(name = "station_code")
    private String stationCode;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy="station", cascade={CascadeType.ALL})
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();

    public static Station getStation(String code) {
        Session session = appContext.getSession();
        Station station = (Station)session.createCriteria(Station.class).add(Restrictions.eq("stationCode", code)).list().get(0);
        Hibernate.initialize(station.getRestaurants());
        appContext.closeSession();
        return station;
    }
}
