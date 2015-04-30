package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import common.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
@JsonIgnoreProperties({ "status", "responseCode", "error" })
@JsonInclude(JsonInclude.Include.NON_NULL)
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
