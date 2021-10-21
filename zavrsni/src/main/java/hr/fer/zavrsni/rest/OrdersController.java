package hr.fer.zavrsni.rest;

import hr.fer.zavrsni.model.Item;
import hr.fer.zavrsni.model.Order;
import hr.fer.zavrsni.model.PlacedOrder;
import hr.fer.zavrsni.model.User;
import hr.fer.zavrsni.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrdersController {

    @Autowired
    private PlacedOrderService placedOrderService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value={"/order-list"},method = RequestMethod.GET)
    public String getOrders(Model model){
        //fetching currently logged in user's username
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        //fetching user
        User deliveryWorker=userService.findUserByEmail(userName);

        //fetching all placed orders for the worker's restaurant
        Long restaurantId=deliveryWorker.getRestaurantId();
        List<PlacedOrder> placedOrders=placedOrderService.findByRestaurantId(restaurantId);

        //showing all orders that aren't completed or taken
        Map<PlacedOrder,Map<Integer,Double>>orders=new HashMap<>();
        for (PlacedOrder order:placedOrders){
            if((!order.isCompleted())&&(!order.isTaken())){
                //fetching all the items from the order
                //fetching items to deliver
                String[]itemIds=order.items.split(",");
                String[]orderQuantitys=order.orders.split(",");
                Map<Integer,Double>itemList=new HashMap<>();
                int cnt=0;
                int total=0;
                int pom=0;
                for (String string: itemIds){
                    cnt+=1;
                    Item item=itemService.findItemById(Long.parseLong(string));
                    total+=Long.parseLong(item.price)*Long.parseLong(orderQuantitys[pom]);
                    pom++;
                }
                itemList.put(cnt,Double.valueOf(total));
                orders.put(order,itemList);
            }
        }

        model.addAttribute("orders",orders);
        return "orders";
    }

    @RequestMapping(value={"/take-order"},method = RequestMethod.POST)
    public String takeAnOrder(Model model, @RequestParam("id") String id){
        //currently logged in user's username
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        //checking if there is more than one active order for user
        List<PlacedOrder>placedOrders=placedOrderService.findByTakenBy(userName);
        for (PlacedOrder placedOrder:placedOrders){
            if(!placedOrder.isCompleted){
                //fetching user
                User deliveryWorker=userService.findUserByEmail(userName);

                //fetching all placed orders for the worker's restaurant
                Long restaurantId=deliveryWorker.getRestaurantId();
                List<PlacedOrder> placedOrders1=placedOrderService.findByRestaurantId(restaurantId);

                //showing all orders that aren't completed or taken
                Map<PlacedOrder,Map<Integer,Double>>orders=new HashMap<>();
                for (PlacedOrder order:placedOrders1){
                    if((!order.isCompleted())&&(!order.isTaken())){
                        //fetching all the items from the order
                        //fetching items to deliver
                        String[]itemIds=order.items.split(",");
                        String[]orderQuantitys=order.orders.split(",");
                        Map<Integer,Double>itemList=new HashMap<>();
                        int cnt=0;
                        int total=0;
                        int pom=0;
                        for (String string: itemIds){
                            cnt+=1;
                            Item item=itemService.findItemById(Long.parseLong(string));
                            total+=Long.parseLong(item.price)*Long.parseLong(orderQuantitys[pom]);
                            pom++;
                        }
                        itemList.put(cnt,Double.valueOf(total));
                        orders.put(order,itemList);
                    }
                }
                model.addAttribute("message","You already have an active order! You can only have one active order at a time");
                model.addAttribute("orders",orders);
                return "orders";
            }
        }

        //making order active for the user
        Long orderId=Long.parseLong(id);
        placedOrderService.takeOrder(true,userName,orderId);

        //fetching user
        User deliveryWorker=userService.findUserByEmail(userName);

        //fetching all placed orders for the worker's restaurant
        Long restaurantId=deliveryWorker.getRestaurantId();
        List<PlacedOrder> placedOrders1=placedOrderService.findByRestaurantId(restaurantId);

        //showing all orders that aren't completed or taken
        Map<PlacedOrder,Map<Integer,Double>>orders=new HashMap<>();
        for (PlacedOrder order:placedOrders){
            if((!order.isCompleted())&&(!order.isTaken())){
                //fetching all the items from the order
                //fetching items to deliver
                String[]itemIds=order.items.split(",");
                String[]orderQuantitys=order.orders.split(",");
                Map<Integer,Double>itemList=new HashMap<>();
                int cnt=0;
                int total=0;
                int pom=0;
                for (String string: itemIds){
                    cnt+=1;
                    Item item=itemService.findItemById(Long.parseLong(string));
                    total+=Long.parseLong(item.price)*Long.parseLong(orderQuantitys[pom]);
                    pom++;
                }
                itemList.put(cnt,Double.valueOf(total));
                orders.put(order,itemList);
            }
        }
        model.addAttribute("orders",orders);
        return "orders";
    }

    @RequestMapping(value={"/active-order"},method = RequestMethod.GET)
    public String getActiveOrder(Model model){
        //fetching currently logged in user's username
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        //fetching all orders taken by the user and showing the one that is not completed
        List<PlacedOrder>placedOrders=placedOrderService.findByTakenBy(userName);
        for (PlacedOrder placedOrder:placedOrders){
            if(!placedOrder.isCompleted){
                model.addAttribute("orderToDeliver",placedOrder);
                model.addAttribute("from",placedOrder.coordinatesFrom);
                model.addAttribute("to",placedOrder.coordinatesTo);
                //fetching items to deliver
                String[]itemIds=placedOrder.items.split(",");
                String[]orderQuantitys=placedOrder.orders.split(",");
                Map<Item,String> itemList=new HashMap<>();
                int pom=0;
                double total=0;
                for (String string: itemIds){
                    Item item=itemService.findItemById(Long.parseLong(string));
                    itemList.put(item,orderQuantitys[pom]);
                    total+=Long.parseLong(item.price)*Long.parseLong(orderQuantitys[pom]);
                    pom++;
                }
                User user=userService.findUserByEmail(placedOrder.deliveringTo);
                model.addAttribute("phone",user.getPhoneNumber());
                model.addAttribute("total",total);
                model.addAttribute("itemsToDeliver",itemList);
                return "activeOrder";
            }
        }

        //there is no active order
        model.addAttribute("orderToDeliver",null);
        return "activeOrder";
    }
    @RequestMapping(value={"/complete-order"},method = RequestMethod.GET)
    public String completeOrderGet(Model model, @RequestParam("id") String id){
        model.addAttribute("orderToDeliver",null);
        model.addAttribute("message","You are trying to complete already completed order!.");
        return "activeOrder";
    }

    @RequestMapping(value={"/complete-order"},method = RequestMethod.POST)
    public String completeOrder(Model model, @RequestParam("id") String id){
        //fetching currently logged in user's username
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName=authentication.getName();

        //updating orders completition
        Long orderId=Long.parseLong(id);
        placedOrderService.completeOrder(true,orderId);

        model.addAttribute("orderToDeliver",null);
        model.addAttribute("message","You have finished delivering the order.");
        return "activeOrder";
    }
}
