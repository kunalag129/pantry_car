package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import common.Model;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 18/04/15.
 */


@Entity
@Table(name="social_auth_tokens")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="token_type")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialAuth extends Model {

    @Column(name = "token")
    protected String token;

    @Column(name = "bio")
    @Type(type="text")
    protected String bio;

    @JsonIgnore
    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="user_id")
    private Customer customer;

}
