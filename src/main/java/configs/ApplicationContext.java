package configs;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.Getter;
import lombok.Setter;
import orders.Offer;
import orders.Order;
import orders.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import railways.Station;
import restaurants.*;
import users.*;

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
    private JsonNodeFactory nodeFactory;

    private ApplicationContext()
    {
        loadBeanClasses();
        loadHibernateFactory();
        nodeFactory = new JsonNodeFactory(false);

    }

    private void loadHibernateFactory() {
        Configuration configuration  = new Configuration().addAnnotatedClass(Location.class)
                .addAnnotatedClass(Restaurant.class)
                .addAnnotatedClass(BankDetail.class)
                .addAnnotatedClass(TaxDetail.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(SocialAuth.class)
                .addAnnotatedClass(GoogleLogin.class)
                .addAnnotatedClass(FacebookLogin.class)
                .addAnnotatedClass(Password.class)
                .addAnnotatedClass(Menu.class)
                .addAnnotatedClass(Offer.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderItem.class)
                .addAnnotatedClass(Station.class)
                .configure();
        ServiceRegistry builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        dbFactory = configuration.buildSessionFactory(builder);
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
