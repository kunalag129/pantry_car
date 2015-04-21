package controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import user.Customer;
import user.Password;

/**
 * Created by kunal.agarwal on 19/04/15.
 */

@RequestMapping("/customers")
@RestController
public class CustomerController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    Customer post_call(@RequestBody Customer customer, BindingResult result) {
        return Customer.register(customer);
    }

    @RequestMapping(value = "/{email_id:.+}", method = RequestMethod.GET)
    public @ResponseBody
    Customer getCustomerDetails(@PathVariable("email_id") String email_id) {
        return Customer.getCustomerDetails(email_id);
    }

    @RequestMapping(value = "/pass/{email_id:.+}", method = RequestMethod.GET)
    public @ResponseBody
    Password getPasswordDetails(@PathVariable("email_id") String email_id) {
       return Customer.getCustomerDetails(email_id).getCustomerPassword();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    boolean cust_login(@RequestBody Customer customer, BindingResult result) {
        return Customer.validateLogin(customer);
    }
}

