package responseParams;

import common.ResponseError;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kunal.agarwal on 06/05/15.
 */

@Getter @Setter
public class ResponseParams {
    protected boolean status = true;
    protected int responseCode = 200;
    protected ResponseError error;
}
