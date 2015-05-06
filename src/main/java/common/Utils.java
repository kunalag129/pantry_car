package common;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import configs.ApplicationContext;

import java.security.SignatureException;
import java.sql.Timestamp;
import java.util.*;

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

    public static String getHmacSignature(HashMap<String, String> params, String key) throws SignatureException {

        return HMACSignature.getSignature(getDataString(params),key);
    }

    private static String getDataString(HashMap<String, String> params) {
        SortedSet<String> set = new TreeSet<String>();
        set.addAll(params.keySet());
        String dataString = "";

        for(Iterator it = set.iterator(); it.hasNext();)
        {
            dataString += params.get(it.next()).toLowerCase();
        }
        return dataString;
    }
}
