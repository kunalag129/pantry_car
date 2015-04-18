package user;

/**
 * Created by kunal.agarwal on 18/04/15.
 */
@Entity
@DiscriminatorValue("Facebook")
public class FacebookLogin extends SocialAuth{
}
