package controllers;

import common.Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import responseParams.MenuResponse;
import restaurants.MenuCategory;
import restaurants.MenuItem;
import restaurants.Restaurant;

/**
 * Created by kunal.agarwal on 08/07/15.
 */

@RequestMapping("/menu")
@RestController
public class MenuController {

    @RequestMapping(value = "/add_category", method = RequestMethod.POST)
    public @ResponseBody
    MenuCategory addCategory(@RequestBody MenuCategory category, BindingResult result) {
        return MenuCategory.addNewCategory(category);
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    public @ResponseBody
    MenuItem addItem(@RequestBody MenuItem item, BindingResult result) {
        return MenuItem.addNewItem(item);
    }

    @RequestMapping(value = "/toggle_category_polarity", method = RequestMethod.PUT)
    public @ResponseBody
    String disableCategory(@RequestBody MenuCategory category, BindingResult result) {
        MenuCategory.toggleCategory(category.getId());
        return Utils.giveResponse(204);
    }

    @RequestMapping(value = "/toggle_item_polarity", method = RequestMethod.PUT)
    public @ResponseBody
    String disableItem(@RequestBody MenuItem item, BindingResult result) {
        MenuItem.toggleItem(item.getId());
        return Utils.giveResponse(204);
    }

    @RequestMapping(value = "/edit_category", method = RequestMethod.PUT)
    public @ResponseBody
    String editCategory(@RequestBody MenuCategory category, BindingResult result) {
        MenuCategory.editCategory(category.getId(), category.getName());
        return Utils.giveResponse(204);
    }

//    @RequestMapping(value="/{id}", method = RequestMethod.POST)
//    public @ResponseBody
//    Menu setMenu(@PathVariable("id") String id,@RequestBody Map<String, Object> menu){
//        return Menu.setMenu(id,menu);
//    }
//
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public @ResponseBody
    MenuResponse getMenu(@PathVariable("id") String id) {
        return Restaurant.getMenuByRestaurantId(id);
    }

}
