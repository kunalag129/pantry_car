package user;

import org.springframework.boot.orm.jpa.EntityScan;

/**
 * Created by kunal.agarwal on 18/04/15.
 */
@Entity
@DiscriminatorValue("Google")
public class GoogleLogin extends SocialAuth{
}
