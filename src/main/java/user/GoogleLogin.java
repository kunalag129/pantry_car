package user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by kunal.agarwal on 18/04/15.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Google")
public class GoogleLogin extends SocialAuth{
}
