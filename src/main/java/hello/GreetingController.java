package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/base")
@RestController
public class GreetingController {

    private static final String template = "Hello yaar, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/employee")
    public Greeting employee() {
        ManageEmployee.execute();
        return new Greeting(counter.incrementAndGet(),
                String.format(template, "hello"));
    }

    @RequestMapping(value = "/post_call", method = RequestMethod.POST, produces="application/json")
    public String post_call(@ModelAttribute Employee e, BindingResult result){
        System.out.println(e);
        if (result.hasErrors()) {
            return "petForm";
        }
        System.out.println(e.getFirstName());
        return String.format("hey man,its a post call");
    }
}
