package requestParams;

import lombok.Getter;
import lombok.Setter;
import orders.OrderItem;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by kunal.agarwal on 31/05/15.
 */
@Getter
@Setter
public class OrderParams {

    String customerId;
    String restaurantId;
    String stationCode;
    Timestamp orderDate;
    Timestamp deliveryDate;
    String customerInstructions;
    String modeOfPayment;
    double amountBilled;
    double amountReceived;
    String pnr;
    String trainNo;
    String seatNo;
    String offerId;
    String discountAmount;
    List<OrderItem> orderItems;
}
