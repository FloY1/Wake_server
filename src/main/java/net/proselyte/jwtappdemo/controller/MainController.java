package net.proselyte.jwtappdemo.controller;

import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.Role;
import net.proselyte.jwtappdemo.security.jwt.JwtUser;
import net.proselyte.jwtappdemo.service.BookingService;
import net.proselyte.jwtappdemo.service.UserService;
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
    private UserService userService;


    @Autowired
    public MainController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping
    public String getMain(Model model){

        model.addAttribute("isDevMode", "dev".equals(profile));



        return "main";
    }

}
