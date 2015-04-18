package user;

import org.springframework.boot.orm.jpa.EntityScan;
import restaurants.Model;

/**
 * Created by kunal.agarwal on 18/04/15.
 */

@Entity
@Table(name="social_auth_tokens")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="token_type")
public class SocialAuth extends Model {

    protected String token;
}
