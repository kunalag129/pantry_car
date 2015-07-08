package responseParams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurants.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal.agarwal on 09/07/15.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuResponse extends ResponseParams{
    List<CustomCategory> categories = new ArrayList<CustomCategory>();

    public void addCategory(String name, List<MenuItem> items) {
        this.categories.add(new CustomCategory(name,items));
    }
}
