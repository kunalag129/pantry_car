package common;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by kunal.agarwal on 25/04/15.
 */
public class Json {

    private final String value;

    public Json(String value) {
        this.value = value;
    }

    @JsonValue
    @JsonRawValue
    public String value() {
        return value;
    }
}