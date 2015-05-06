package external.railway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Utils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;

/**
 * Created by kunal.agarwal on 01/05/15.
 */
public class RailwayService {

    final static private String[] getPnrParams = new String[]{"pnr", "format", "pbapikey", "pbapisign"};

    final static private String[] getTrainRouteParams = new String[]{"train", "format", "pbapikey", "pbapisign"};

    final static private String erailApiKey = "cca37432-1450-46f8-99f8-b00ff66f09e8";

    final static private String railwayApiKey = "66385";

    final static private String railPnrApiPublicKey = "059da6313f8a4f63fc3b3edfd6fe38c8";

    final static private String railPnrApiPrivateKey = "9d41fbb42d4486f6680b9d5b459b7cb1";

    final static private RestTemplate rest = new RestTemplate();

    final static private String railPnrApiUrl = "http://railpnrapi.com/test/";


    static {

    }

    public static JsonNode getPnrDetails(String pnr) {
        HashMap<String,String> details = new HashMap<String, String>();
        details.put("pnr", pnr);
        details = modifyAndGetHmac(details);
        String url = railPnrApiUrl + "check_pnr" + getUrlString(getPnrParams, details);
        String result = rest.getForObject(url,String.class);
        return getJsonNodeFromJson(result);
    }

    public static JsonNode getTrainRouteDetails(String trainNo) {
        HashMap<String,String> details = new HashMap<String, String>();
        details.put("train", trainNo);
        details = modifyAndGetHmac(details);
        String url = railPnrApiUrl + "route" + getUrlString(getTrainRouteParams, details);
        String result = rest.getForObject(url,String.class);
        return getJsonNodeFromJson(result);
    }

    private static JsonNode getJsonNodeFromJson(String result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getUrlString(String[] getParams, HashMap<String, String> details) {
        String paramsUrl = "";
        for(int i = 0; i<getParams.length;i++) {
            paramsUrl+= "/" + getParams[i] + "/" + details.get(getParams[i]);
        }
        return paramsUrl;
    }

    private static HashMap<String, String> modifyAndGetHmac(HashMap<String, String> details) {
        details.put("format", "json");
        details.put("pbapikey", railPnrApiPublicKey);
        try {
            details.put("pbapisign", Utils.getHmacSignature(details,railPnrApiPrivateKey));
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return details;
    }

}
