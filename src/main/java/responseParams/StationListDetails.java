package responseParams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import railways.TrainStoppage;

import java.util.ArrayList;

/**
 * Created by kunal.agarwal on 08/05/15.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StationListDetails extends ResponseParams{
    private String srcStationName;
    private String destStationName;
    private String srcStationCode;
    private String destStationCode;
    private String date;
    private String trainNum;
    private String trainName;
    private ArrayList<TrainStoppage> trainStoppages;
}
