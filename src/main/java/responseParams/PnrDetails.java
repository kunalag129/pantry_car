package responseParams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import railways.TrainStoppage;

import java.util.ArrayList;

/**
 * Created by kunal.agarwal on 06/05/15.
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PnrDetails extends ResponseParams{

    private String pnr;
    private String trainNum;
    private String trainName;
    private String srcStationCode;
    private String destStationCode;
    private String srcStationName;
    private String destStationName;
    private String date;
    private ArrayList<TrainStoppage> trainStoppages;

}
