package common;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import configs.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by kunal.agarwal on 01/05/15.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ResponseError {
    static ApplicationContext appContext = ApplicationContext.getInstance();

    protected int code;
    protected String message;

    public static String notFound() {
        JsonNodeFactory nodeFactory = appContext.getNodeFactory();
        ObjectNode node = nodeFactory.objectNode();
        node.put("status",false);
        node.put("responseCode",404);
        ObjectNode error = nodeFactory.objectNode();
        error.put("message", "Record not found");
        node.set("error", error);
        return node.toString();
    }
}
