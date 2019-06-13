package net.proselyte.jwtappdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login/ad")
public class MainController {

    @Value("${spring.profile.active}")
    private String profile;


    @GetMapping
    public String getMain(Model model){

        model.addAttribute("isDevMode", "dev".equals(profile));



        return "main";
    }

}
