package net.proselyte.jwtappdemo.controller;

import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.security.jwt.JwtUser;
import net.proselyte.jwtappdemo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainController {

    @Value("${spring.profile.active:prod}")
    private String profile;


    private BookingService bookingService;

    @Autowired
    public MainController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String getMain(Model model, @AuthenticationPrincipal JwtUser user){

        if(user!=null) {
           model.addAttribute("bookigList",bookingService.findAll());
        }else{
            model.addAttribute("bookigList",null);
        }
        model.addAttribute("isDevMode", "dev".equals(profile));



        return "main";
    }

}
