package controllers;

import orders.Order;
import orders.OrderItem;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kunal.agarwal on 30/05/15.
 */

@RequestMapping("/orders")
@RestController
public class OrderController {

    @RequestMapping(value = "/test_app", method = RequestMethod.GET)
    public @ResponseBody
    String test_app() {
        return "Yo Yo, app is up!! :)";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    Order post_call(@RequestBody Order order, BindingResult result) {
        if(OrderItem.validateItems(order.getOrderItems())== false)
            return null;
        return Order.createNewOrder(order);
    }
}
