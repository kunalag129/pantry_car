package configs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lombok.Getter;
import lombok.Setter;
import restaurants.Location;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.Locale;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

@Getter @Setter
public class ApplicationContext {
    private static ApplicationContext instance = null;
    private AnnotationConfigApplicationContext ctx = null;
    private SessionFactory dbFactory = null;

    private ApplicationContext()
    {
        loadBeanClasses();
        loadHibernateFactory();
    }

    private void loadHibernateFactory() {
        dbFactory = new AnnotationConfiguration().configure().addAnnotatedClass(Location.class).buildSessionFactory();
    }

    private void loadBeanClasses() {
        ctx = new AnnotationConfigApplicationContext();
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

}
