package responseParams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import railways.Station;

import java.util.ArrayList;

/**
 * Created by kunal.agarwal on 06/05/15.
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PnrDetails {

    private String pnr;
    private String trainNum;
    private String trainName;
    private String fromStationCode;
    private String toStationCode;
    private String fromStationName;
    private String toStationName;
    private ArrayList<Station> stations;

}
