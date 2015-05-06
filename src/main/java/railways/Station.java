package railways;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by kunal.agarwal on 05/05/15.
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Station {

    private String arrivalTime;
    private String departureTime;
    private String stoppageTime;
    private String day;
    private String stationCode;
    private String stationName;
}
