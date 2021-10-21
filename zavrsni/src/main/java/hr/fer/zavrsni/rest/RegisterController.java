package hr.fer.zavrsni.rest;

import hr.fer.zavrsni.model.FormUser;
import hr.fer.zavrsni.model.User;
import hr.fer.zavrsni.service.EmailService;
import hr.fer.zavrsni.service.UserService;
import hr.fer.zavrsni.util.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value={"/register"})
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String userRegistration(Model model, @ModelAttribute FormUser formUser){

        //dohvat unesenih podataka
        String email=formUser.getEmail();
        String password=formUser.getPassword();
        String name=formUser.getName();
        String surname=formUser.getSurname();
        String role=formUser.getRole();
        String state=formUser.getState();
        String city=formUser.getCity();
        String streetAddress=formUser.getStreetAddress();
        String addressNumber=formUser.getAddressNumber();
        String phoneNumber=formUser.getPhoneNumber();

        //provjera ispravnosti unesenih podataka
        if(email==null || password==null || role==null || name==null || surname==null || state==null || city==null || streetAddress==null || addressNumber==null || phoneNumber==null){
            model.addAttribute("registrationFail","An error has occurred. Please try again.");
            return "register";
        }

        //provjera da li postoji korisnik sustava sa istim emailom
        if(userService.findUserByEmail(email)!=null){
            model.addAttribute("registrationFail","There is already a user with same email. Please try again using different email. ");
            model.addAttribute("formUser",new FormUser(null,null,null,name,surname,state,city,streetAddress,addressNumber,phoneNumber));
            return "register";
        }
        //email hash ce sluziti za verifikaciju
        String emailBcrypt=passwordEncoder.encode(email);
        //stvaranje sadrzaja verifikacijskog maila
        String verifikacijskiLink="http://localhost:8080/validation?usr="+emailBcrypt;
        String mailHtml="<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      .colored {\n" +
                "        color: green;\n" +
                "      }\n" +
                "      #body {\n" +
                "        font-size: 16px;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id='body'>\n" +
                "      <p class='colored'>Hi "+ name+",</p>\n" +
                "      <p>To verify your new account please click on this link: "+verifikacijskiLink+"</p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
        //slanje verifikacijskog maila
        try {
            Mail mail = new Mail();
            mail.setFrom("FDService365@gmail.com");
            mail.setTo(email);
            mail.setSubject("Account Registration");
            mail.setContent(mailHtml);

            emailService.sendSimpleMessage(mail);
        }catch (Exception e){
            model.addAttribute("registrationFail","An error has occurred while sending verification mail. Please try again.");
            return "register";
        }

        //spremanje korisinika u bazu
        String passwordBCrypt=passwordEncoder.encode(password);
        User user=new User(email,name,surname,passwordBCrypt,false,emailBcrypt,role,state,city,streetAddress,addressNumber,phoneNumber,false,new Long(-1));
        userService.registerUser(user);
        model.addAttribute("registrationSuccess","Verification mail has been sent. To finish your registration please check your email.");
        model.addAttribute("formUser",new FormUser());
        return "register";
    }

    @GetMapping
    public String getRegisterPage(Model model){
        model.addAttribute("formUser",new FormUser());
        return "register";
    }

}
