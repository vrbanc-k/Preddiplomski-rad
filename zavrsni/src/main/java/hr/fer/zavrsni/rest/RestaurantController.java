package hr.fer.zavrsni.rest;

import hr.fer.zavrsni.model.*;
import hr.fer.zavrsni.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RestaurantController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value={"/my-restaurants"},method = RequestMethod.GET)
    public String myRestaurants(Model model){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();
        User user=userService.findUserByEmail(userName);

        List<Restaurant> restaurantList=restaurantService.findRestaurantsOwnedBy(user);
        model.addAttribute("formRestaurant",new FormRestaurant());
        model.addAttribute("restaurantList", restaurantList);
        return "myRestaurants";
    }

    @RequestMapping(value={"/removeRestaurant"},method = RequestMethod.GET)
    public String removeRestaurant(Model model,@RequestParam("restaurant") String id){
        //dohvacanje trenutno ulogiranog korisnika
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();
        User user=userService.findUserByEmail(userName);
        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));
        if(restaurant==null){
            model.addAttribute("message","You are trying to delete a non existant restaurant!");
        }else{
            restaurantService.deleteRestaurant(Long.parseLong(id));
        }

        List<Restaurant> restaurantList=restaurantService.findRestaurantsOwnedBy(user);
        model.addAttribute("formRestaurant",new FormRestaurant());
        model.addAttribute("restaurantList", restaurantList);
        return "myRestaurants";
    }

    @RequestMapping(value={"/my-restaurants"},method = RequestMethod.POST)
    public String addRestaurant(Model model,@ModelAttribute FormRestaurant formRestaurant,@RequestParam MultipartFile file){
        //dohvacanje trenutno ulogiranog korisnika
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();
        User user=userService.findUserByEmail(userName);

        String name=formRestaurant.getName();
        String openDays=formRestaurant.getOpenDays();
        String openFrom=formRestaurant.getOpenFrom();
        String openUntil=formRestaurant.getOpenUntil();
        String coordinates=formRestaurant.getCoordinates();
        String[] s=coordinates.split(",");
        String[] firstCoordinate= s[0].split("\\(");
        String[] secondCoordinate= s[1].split("\\)");
        String coordinatesToBeSaved=firstCoordinate[1]+","+secondCoordinate[0];
        String description=formRestaurant.getDescription();


        Restaurant restaurant=new Restaurant(name,openDays,openFrom,openUntil,user,coordinatesToBeSaved,description);
        restaurantService.addRestaurant(restaurant);
        User user1=userService.findUserByEmail(userName);
        Restaurant restaurantPom=restaurantService.findRestaurantByName(name);
        String imageName=user1.getId().toString()+"-"+restaurantPom.getId().toString()+".jpg";
        storageService.uploadFile(file,imageName,"zavrsniradrestaurants");

        List<Restaurant> restaurantList=restaurantService.findRestaurantsOwnedBy(user);
        model.addAttribute("formRestaurant",new FormRestaurant());
        model.addAttribute("restaurantList", restaurantList);
        return "myRestaurants";
    }


    @RequestMapping(value={"/restaurant-list"},method = RequestMethod.GET)
    public String Restaurants(Model model){
        List<Restaurant> listAllRestaurants=restaurantService.listAllRestaurants();
        model.addAttribute("listAllRestaurants",listAllRestaurants);
        return "restaurants";
    }
    @RequestMapping(value ={"/setRestaurantMenu"},method = RequestMethod.GET)
    public String get2Restaurant(Model model,@RequestParam("restaurant")String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();
        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));
        if(restaurant==null){
            model.addAttribute("restaurant",null);
        }else{
            model.addAttribute("restaurant",restaurant);
        }
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }

    @RequestMapping(value ={"/setRestaurantMenu"},method = RequestMethod.POST)
    public String getRestaurant(Model model,@RequestParam("restaurant")String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();
        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));
        if(restaurant==null){
            model.addAttribute("restaurant",null);
        }else{
            model.addAttribute("restaurant",restaurant);
        }
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }
    @RequestMapping(value={"/add-item"},method = RequestMethod.GET)
    public String addMenuItemGet(Model model,@ModelAttribute FormItem formItem,@RequestParam("restaurant")String id,@RequestParam MultipartFile file){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();
        //fetching restaurant
        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));
        model.addAttribute("restaurant",restaurant);
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("message","You are trying to add already added item!");
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }

    @RequestMapping(value={"/add-item"},method = RequestMethod.POST)
    public String addMenuItem(Model model,@ModelAttribute FormItem formItem,@RequestParam("restaurant")String id,@RequestParam MultipartFile file){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();
        //fetching restaurant
        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));

        String name=formItem.getName();
        String price=formItem.getPrice();
        String description=formItem.getDescription();

        itemService.addMenuItem(new Item(restaurant,name,price,description));

        model.addAttribute("restaurant",restaurant);
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);

        for (Item item:itemList){
            if(item.restaurant==restaurant && item.name==name && item.price==price && item.description==description){
                String imageName=restaurant.getId().toString()+"-"+item.getId().toString()+".jpg";
                storageService.uploadFile(file,imageName,"zavrsniraditems");
            }
        }

        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }

    @RequestMapping(value={"/add-delivery-worker"},method = RequestMethod.GET)
    public String addDeliveryWorkerGet(Model model, @ModelAttribute FormWorker formWorker, @RequestParam("restaurant")String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));

        model.addAttribute("message","You are trying to add already added delivery worker!");
        model.addAttribute("restaurant",restaurant);
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }


    @RequestMapping(value={"/add-delivery-worker"},method = RequestMethod.POST)
    public String addDeliveryWorker(Model model, @ModelAttribute FormWorker formWorker, @RequestParam("restaurant")String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));
        String email=formWorker.getEmail();
        //saving worker into db, password is generated using worker's id
        User worker=new User(email,"Worker","Worker","-1",true,passwordEncoder.encode(email),"ROLE_WORKER","-1","-1","-1","-1","-1",true,Long.parseLong(id));
        userService.registerUser(worker);
        //fetching worker and generating password
        User pom=userService.findUserByEmail(email);
        Long workerId=pom.getId();
        String newPass=passwordEncoder.encode(workerId.toString());
        userService.updateUserPassword(workerId,newPass);

        model.addAttribute("restaurant",restaurant);
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }

    @RequestMapping(value={"/order-item"},method = RequestMethod.POST)
    public String orderItem(Model model,@ModelAttribute FormOrder formOrder,@RequestParam("restaurant")String id){
        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));

        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        Long itemId=Long.parseLong(formOrder.getItemId());
        int quantity=formOrder.getQuantity();
        String name=formOrder.getItemName();
        String price=formOrder.getPrice();
        Long restaurantId=Long.parseLong(formOrder.getRestaurantId());
        double subTotal=Double.parseDouble(price)*quantity;

        orderService.addOrder(new Order(itemId,userName,quantity,name,price,restaurantId,subTotal));

        model.addAttribute("restaurant",restaurant);
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);




        return "setRestaurantMenu";
    }

    @RequestMapping(value={"/removeItem"},method = RequestMethod.GET)
    public String removeItem(Model model,@ModelAttribute FormOrder formOrder,@RequestParam("item")String id,@RequestParam("restaurant")String rid){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(rid));
        Item item=itemService.findItemById(Long.parseLong(id));
        if(item==null){
            model.addAttribute("message","You are trying to remove already removed item!");
        }else {
            itemService.deleteItem(Long.parseLong(id));
        }

        model.addAttribute("restaurant",restaurant);
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }

    @RequestMapping(value={"/removeWorker"},method = RequestMethod.GET)
    public String deleteDeliveryWorker(Model model,@RequestParam("id")String workerId, @RequestParam("restaurant")String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        Restaurant restaurant=restaurantService.findRestaurantById(Long.parseLong(id));
        User worker=userService.findUserById(Long.parseLong(workerId));
        userService.deleteUser(worker);

        model.addAttribute("restaurant",restaurant);
        List<Item> itemList=itemService.findAllByRestaurant(restaurant);
        model.addAttribute("itemList",itemList);
        model.addAttribute("formItem",new FormItem());
        List<User> workerList=userService.findUserByRestaurantId(Long.parseLong(id));
        model.addAttribute("workerList",workerList);
        FormWorker formWorker1=new FormWorker();
        model.addAttribute("formWorker",formWorker1);
        model.addAttribute("formOrder",new FormOrder());
        //getting ordered items by the user
        List<Order>orderedItems=orderService.findOrderedItemByUserEmail(userName);
        List<Long>orderedItemIds=new ArrayList<>();
        for (Order order:orderedItems){
            orderedItemIds.add(order.itemId);
        }
        model.addAttribute("orderedItems",orderedItemIds);
        return "setRestaurantMenu";
    }
}
