package net.proselyte.jwtappdemo.controller.rest;

import net.proselyte.jwtappdemo.dto.AuthenticationRequestDto;
import net.proselyte.jwtappdemo.dto.EventType;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.security.jwt.JwtUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.BiConsumer;

@RestController
@RequestMapping("admin")
public class AdminController {


    @GetMapping
    public String getAdmin(@AuthenticationPrincipal JwtUser user){
        System.out.println(user);
        return "I am admin";
    }
}
