package responseParams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import railways.Train;

import java.util.ArrayList;

/**
 * Created by kunal.agarwal on 06/05/15.
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainListDetails extends ResponseParams{

    private String srcStationName;
    private String destStationName;
    private String srcStationCode;
    private String destStationCode;
    private String date;
    private ArrayList<Train> trains;

}
