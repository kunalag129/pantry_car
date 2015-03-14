import configs.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Application.main");
        System.out.println("========================================");
        System.out.println(ApplicationContext.getInstance().getCtx());
        System.out.println(ApplicationContext.getInstance().getDbFactory());
        System.out.println("========================================");
        SpringApplication.run(Application.class, args);
    }
}
