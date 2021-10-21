package hr.fer.zavrsni.rest;

import hr.fer.zavrsni.model.Item;
import hr.fer.zavrsni.model.Order;
import hr.fer.zavrsni.model.PlacedOrder;
import hr.fer.zavrsni.model.Restaurant;
import hr.fer.zavrsni.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PlacedOrderService placedOrderService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value={"/shopping-cart"},method = RequestMethod.GET)
    public String getCart(Model model){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        List<Order> orderedItems = orderService.findOrderedItemByUserEmail(userName);
        double totalPrice=0;
        for(Order order : orderedItems){
            totalPrice+=order.getSubTotal();
        }
        model.addAttribute("total",totalPrice);
        model.addAttribute("items",orderedItems);
        return "cart";
    }

    @RequestMapping(value={"/remove-item"},method = RequestMethod.POST)
    public String removeFromCart(Model model, @RequestParam("id") String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        Long itemId=Long.parseLong(id);
        Order orderToBeRemoved=orderService.findOrderById(itemId);

        orderService.deleteOrderedItem(orderToBeRemoved);

        List<Order> orderedItems = orderService.findOrderedItemByUserEmail(userName);
        double totalPrice=0;
        for(Order order : orderedItems){
            totalPrice+=order.getSubTotal();
        }
        model.addAttribute("total",totalPrice);
        model.addAttribute("items",orderedItems);
        return "cart";
    }

    @RequestMapping(value={"/change-quantity"},method = RequestMethod.POST)
    public String changeQuantityOfOrderedItem(Model model, @RequestParam("id") String id,@RequestParam("quantity") String quantity){
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        Long itemId=Long.parseLong(id);
        int itemQuantity=Integer.parseInt(quantity);

        Order orderToBeUpdated=orderService.findOrderById(itemId);
        orderService.deleteOrderedItem(orderToBeUpdated);
        double subTotal=Double.parseDouble(orderToBeUpdated.getPrice())*itemQuantity;
        Order newOrder=new Order(orderToBeUpdated.getItemId(),userName,itemQuantity,orderToBeUpdated.getName(),orderToBeUpdated.price,orderToBeUpdated.getRestaurantId(),subTotal);
        orderService.addOrder(newOrder);

        List<Order> orderedItems = orderService.findOrderedItemByUserEmail(userName);
        double totalPrice=0;
        for(Order order : orderedItems){
            totalPrice+=order.getSubTotal();
        }
        model.addAttribute("total",totalPrice);
        model.addAttribute("items",orderedItems);
        return "cart";
    }

    @RequestMapping(value={"/place-order"},method = RequestMethod.POST)
    public String placeOrder(Model model, @RequestParam("coordinates") String coordinates){

        //getting currently logged in user's username
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        //coordinates where user set his food to be delivered
        String[] s=coordinates.split(",");
        String[] firstCoordinate= s[0].split("\\(");
        String[] secondCoordinate= s[1].split("\\)");
        String coordinatesToBeSaved=firstCoordinate[1]+","+secondCoordinate[0];

        //all of the items user has put in his cart
        List<Order> orderList=orderService.findOrderedItemByUserEmail(userName);


        //getting all the different restaurant id's
        List<Long>restaurantIds=new ArrayList<>();
        for (Order order : orderList){
            if(!restaurantIds.contains(order.restaurantId)){
                restaurantIds.add(order.restaurantId);
            }
        }

        //filter items to match restaurant id
        Map<Long,String> itemMap=new HashMap<>();
        String pom;
        for (Long resId:restaurantIds){
            if(!itemMap.containsKey(resId)){
                itemMap.put(resId,"");
            }
            pom=itemMap.get(resId);
            for (Order order:orderList){
                if (order.restaurantId == resId){
                    pom+=order.itemId;
                    pom+=",";
                }
            }
            itemMap.put(resId,pom);
        }

        Map<Long,String> orderMap=new HashMap<>();
        String orderQuantitys;
        for (Long resId:restaurantIds){
            if(!orderMap.containsKey(resId)){
                orderMap.put(resId,"");
            }
            orderQuantitys=orderMap.get(resId);
            for (Order order:orderList){
                if (order.restaurantId == resId){
                    orderQuantitys+=order.quantity;
                    orderQuantitys+=",";
                }
            }
            orderMap.put(resId,orderQuantitys);
        }

        //for every different restaurant id make a new PlacedOrder
        for (Long resId:restaurantIds){
            pom=itemMap.get(resId);
            orderQuantitys=orderMap.get(resId);
            Restaurant restaurant=restaurantService.findRestaurantById(resId);
            String restaurantCoordinates=restaurant.getPlace();
            placedOrderService.addPlacedOrder(new PlacedOrder(resId,pom,coordinatesToBeSaved,restaurantCoordinates,userName,false,null,false,orderQuantitys));
        }


        for(Order order:orderList){
            orderService.deleteOrderedItem(order);
        }




        List<Order> orderedItems = orderService.findOrderedItemByUserEmail(userName);
        double totalPrice=0;
        for(Order order : orderedItems){
            totalPrice+=order.getSubTotal();
        }
        String message="Your order has been placed.";
        model.addAttribute("total",totalPrice);
        model.addAttribute("message",message);
        model.addAttribute("items",orderedItems);
        return "cart";
    }
}
