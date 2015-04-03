package common;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by kunal.agarwal on 31/03/15.
 */
public class Utils {

    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }
}
