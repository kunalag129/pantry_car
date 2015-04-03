package configs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lombok.Getter;
import lombok.Setter;
import restaurants.BankDetail;
import restaurants.TaxDetail;
import restaurants.Location;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import restaurants.Restaurant;

import java.util.Locale;

/**
 * Created by kunal.agarwal on 14/03/15.
 */

@Getter @Setter
public class ApplicationContext {
    private static ApplicationContext instance = null;
    private AnnotationConfigApplicationContext ctx = null;
    private SessionFactory dbFactory = null;
    private Session session = null;
    private Transaction transaction;

    private ApplicationContext()
    {
        loadBeanClasses();
        loadHibernateFactory();
    }

    private void loadHibernateFactory() {
        dbFactory = new AnnotationConfiguration().addAnnotatedClass(Location.class)
                .addAnnotatedClass(Restaurant.class)
                .addAnnotatedClass(BankDetail.class)
                .addAnnotatedClass(TaxDetail.class)
                .configure().buildSessionFactory();
    }

    private void loadBeanClasses() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(CommonConfig.class);
        ctx.refresh();
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public Session getSession(){
        session = dbFactory.openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        transaction.commit();
        session.close();
    }

}
