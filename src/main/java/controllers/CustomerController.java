package controllers;

import common.ResponseError;
import common.Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import user.Customer;

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

    @RequestMapping(value = "/get_customer_from_token", method = RequestMethod.GET)
    public @ResponseBody
    Customer getCustomerFromToken(@RequestParam("token") String token) {
        Customer customer = Customer.getCustomerDetails("rememberToken",token);
        if(customer == null)
            return new Customer().updateResponseAttributes(false, 404, "Record not found");
        else
            return customer;
    }

    @RequestMapping(value = "/{emailId:.+}/update_remember_token", method = RequestMethod.PUT)
    public @ResponseBody
    String updateRememberToken(@PathVariable("emailId") String emailId, @RequestBody Customer cust) {
        Customer customer = Customer.getCustomerDetails("emailId",emailId);
        if(customer == null)
            return ResponseError.notFound();
        else {
            customer.updateRememberToken(cust.getRememberToken());
            return Utils.giveResponse(204);
        }
    }

    @RequestMapping(value = "/{emailId:.+}", method = RequestMethod.GET)
    public @ResponseBody
    Customer getCustomerDetails(@PathVariable("emailId") String emailId) {
        Customer customer = Customer.getCustomerDetails("emailId",emailId);
        if(customer == null) {
            return new Customer().updateResponseAttributes(false, 404, "Record not found");
        }
        else
            return customer;
    }

//    @RequestMapping(value = "/pass/{email_id:.+}", method = RequestMethod.GET)
//    public @ResponseBody
//    Password getPasswordDetails(@PathVariable("email_id") String email_id) {
//       return Customer.getCustomerDetails(email_id).getCustomerPassword();
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String cust_login(@RequestBody Customer customer, BindingResult result) {
        return Customer.validateLogin(customer);
    }

//    @RequestMapping(value = "/test_raw", method = RequestMethod.POST)
//    public @ResponseBody
//    ResponseEntity<String> test_raw(@RequestBody Map<String,Object> a) throws Exception {
//        System.out.println("=====================================");
//        System.out.println(a.get("googleLogin"));
//        System.out.println(((Map<String,Object>)a.get("googleLogin")).get("token"));
//        Map<String,Object> c = (Map<String,Object>)a.get("googleLogin");
//        System.out.println(c.get("token"));
//        System.out.println(a.get("abc"));
//        System.out.println(a.get("abc1"));
//        System.out.println("-------------------------------------");
//        return new ResponseEntity<String>("Hello", HttpStatus.OK);
//    }


}

