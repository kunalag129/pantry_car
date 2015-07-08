package common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import configs.ApplicationContext;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    @Transient
    protected boolean status = true;

    @Transient
    protected int responseCode = 200;

    @Transient
    protected ResponseError error;

    public <T extends Model> T updateTimeStamps(){
        if(this.getCreatedAt()==null)
            this.setCreatedAt(Utils.getTimestamp());
        this.setUpdatedAt(Utils.getTimestamp());
        return (T)this;
    }

    @JsonIgnore
    public List<? extends Model> getCriteriaList() {
        return null;
    }

    public int save() {
        updateTimeStamps();
        Session session = appContext.getSession();
        int locId = (Integer) session.save(this);
        appContext.closeSession();
        return locId;
    }

    public void update() {
        updateTimeStamps();
        Session session = appContext.getSession();
        session.update(this);
        appContext.closeSession();
    }
}
