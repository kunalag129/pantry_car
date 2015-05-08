package railways;

/**
 * Created by kunal.agarwal on 08/05/15.
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by kunal.agarwal on 06/05/15.
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Train {

    private String trainNum;
    private String trainName;
    private String srcArrivalTime;
    private String destArrivalTime;
    private String srcDepartureTime;
    private String destDepartureTime;
    private String source;
    private String destination;
}

