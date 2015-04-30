package common;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import configs.ApplicationContext;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by kunal.agarwal on 31/03/15.
 */
public class Utils {
    static ApplicationContext appContext = ApplicationContext.getInstance();

    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static String giveResponse(int responseCode) {
        JsonNodeFactory nodeFactory = appContext.getNodeFactory();
        ObjectNode node = nodeFactory.objectNode();
        node.put("status",true);
        node.put("responseCode",responseCode);
        return node.toString();
    }
}
