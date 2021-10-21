package hr.fer.zavrsni.rest;


import hr.fer.zavrsni.model.User;
import hr.fer.zavrsni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value={"/validation"})
public class ValidationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String validateUser(Model model, @RequestParam("usr")String usr){
        User user=userService.findUserByEmailBcrypt(usr);
        if(user==null){
            model.addAttribute("validation","An error has occurred with account verification");
            return "home";
        }
        if(user.isValidated()){
            model.addAttribute("validation","Your account has already been verified.");
            return "home";
        }
        userService.updateUserValidatedTrue(user.getId());
        model.addAttribute("validation","You have successfully verified your account. Now you can log in.");
        return "home";
    }
}

