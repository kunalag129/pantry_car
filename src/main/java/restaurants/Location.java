package restaurants;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter @Setter
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

}