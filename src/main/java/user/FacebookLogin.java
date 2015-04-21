package user;

/**
 * Created by kunal.agarwal on 18/04/15.
 */
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Facebook")
public class FacebookLogin extends SocialAuth{
}
