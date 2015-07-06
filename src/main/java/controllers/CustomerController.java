package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ResponseError;
import common.Utils;
import external.railway.RailwayService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import users.Customer;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;

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
            return ResponseError.notFound(null);
        else {
            customer.updateRememberToken(cust.getRememberToken());
            return Utils.giveResponse(204);
        }
    }

    @RequestMapping(value = "/{emailId:.+}/update_verification_token", method = RequestMethod.PUT)
    public @ResponseBody
    String setVerificationToken(@PathVariable("emailId") String emailId, @RequestBody Customer cust) {
        Customer customer = Customer.getCustomerDetails("emailId",emailId);
        if(customer == null)
            return ResponseError.notFound(null);
        else {
            customer.updateVerificationToken(cust.getVerificationToken());
            return Utils.giveResponse(204);
        }
    }

    @RequestMapping(value = "/{emailId:.+}/update_password_reset_token", method = RequestMethod.PUT)
    public @ResponseBody
    String setPasswordResetToken(@PathVariable("emailId") String emailId, @RequestBody Customer cust) {
        Customer customer = Customer.getCustomerDetails("emailId",emailId);
        if(customer == null)
            return ResponseError.notFound(null);
        else {
            customer.updatePasswordResetToken(cust.getPassResetToken());
            return Utils.giveResponse(204);
        }
    }

    @RequestMapping(value = "/{emailId:.+}/reset_password", method = RequestMethod.PUT)
    public @ResponseBody
    String resetPassword(@PathVariable("emailId") String emailId, @RequestBody Customer cust) {
        Customer customer = Customer.getCustomerDetails("emailId",emailId);
        if(customer == null)
            return ResponseError.notFound(null);
        else if (customer.getPassResetToken() == null)
            return ResponseError.notFound("Invalid PassResetToken");
        else {
            customer.resetPassword(cust.getLoginPass());
            return Utils.giveResponse(204);
        }
    }

    @RequestMapping(value = "/set_customer_verified", method = RequestMethod.PUT)
    public @ResponseBody
    Customer setCustomerVerified(@RequestBody Customer cust) {
        Customer customer = Customer.getCustomerDetails("verificationToken",cust.getVerificationToken());
        if(customer == null)
            return new Customer().updateResponseAttributes(false, 404, "Record not found");
        else {
            customer.setVerificationToken(null);
            customer.setVerified(true);
            customer.update();
            return customer;
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

    @RequestMapping(value = "/test_rail", method = RequestMethod.GET)
    public @ResponseBody
    String get() throws SignatureException, IOException {
        RestTemplate rest = new RestTemplate();
        String url = "http://api.pantrycar.co.in/customers/get_customer_from_token?token=qOaQVzDMh3idSMZfcms4aKXkevL1C4bcNu7ZR1sRwnb7lydrHwumjKZYOWnE";
        String result = rest.getForObject(url,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(result);
        System.out.println("=====================================");
        System.out.println(result);
        HashMap<String,String> a = new HashMap<String, String>();
        a.put("pnr", "1234567890");
        System.out.println(RailwayService.getPnrDetails("1234567890"));
        a.put("pnr", "274790749");
        JsonNode jn = RailwayService.getPnrDetails("274790749");
        String fromStation = jn.get("from_station").get("code").toString();
        String toStation = jn.get("to_station").get("code").toString();
        System.out.println(fromStation);
        System.out.println(toStation);

        a = new HashMap<String, String>();
        a.put("train", "12004");
        System.out.println(RailwayService.getTrainRouteDetails("12004"));
        System.out.println(jn);
        System.out.println(actualObj.get("status").getClass());
        System.out.println(actualObj.get("status"));
        System.out.println(actualObj.get("error"));
        System.out.println(actualObj.get("error").getClass());

//        MyObject ob = new ObjectMapper().readValue(jsonString, MyObject.class);
        System.out.println("-------------------------------------");
        return result;
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

