package restaurants;

import common.Utils;
import configs.ApplicationContext;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import javax.persistence.*;

import java.sql.Timestamp;

/**
 * Created by kunal.agarwal on 04/04/15.
 */
@MappedSuperclass
@Getter @Setter
public abstract class Model {

    static ApplicationContext appContext = ApplicationContext.getInstance();

    @Id @GeneratedValue
    @Column(name = "id")
    protected int id;

    @Column(name = "created_at")
    protected Timestamp createdAt;

    @Column(name = "updated_at")
    protected Timestamp updatedAt;

    public void updateTimeStamps(){
        if(this.getCreatedAt()==null)
            this.setCreatedAt(Utils.getTimestamp());
        this.setUpdatedAt(Utils.getTimestamp());
    }

    public void save() {
        updateTimeStamps();
        Session session = appContext.getSession();
        int locId = (Integer) session.save(this);
        appContext.closeSession();
    }

    public void update() {
        updateTimeStamps();
        Session session = appContext.getSession();
        session.update(this);
        appContext.closeSession();
    }
}
