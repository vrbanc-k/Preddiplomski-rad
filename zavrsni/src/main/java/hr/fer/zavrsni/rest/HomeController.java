package hr.fer.zavrsni.rest;

import hr.fer.zavrsni.model.Restaurant;
import hr.fer.zavrsni.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value={"/","/home","/index"})
    public String home(Model model){
        List<Restaurant> restaurants=restaurantService.listAllRestaurants();
        List<String> markers=new ArrayList<>();
        for(Restaurant r : restaurants){
            String[] numbers=r.getPlace().split(",");
            markers.add(numbers[0]);
            markers.add(numbers[1]);
        }
        model.addAttribute("markers",markers.toArray());
        return "home";
    }

}
