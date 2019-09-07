package net.proselyte.jwtappdemo.controller.rest;

import net.proselyte.jwtappdemo.dto.AuthenticationRequestDto;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.security.jwt.JwtTokenProvider;
import net.proselyte.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for authentication requests (login, logout, register, etc.)
 *
 * @author Arthem Smolonskiy
 * @version 1.0
 */

@RestController
@RequestMapping("/login")
public class AutAndRegistrationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AutAndRegistrationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping(value = "regist",
            produces = "application/json")
    public ResponseEntity registration(@RequestBody AuthenticationRequestDto user) {
        userService.register(user);
        return ResponseEntity.ok("ess");
    }

    @PostMapping
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String phone = requestDto.getPhone();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, requestDto.getPassword()));
            User user = userService.findUserByPhone(phone);

            if (user == null) {
                throw new UsernameNotFoundException("User with phone: " + phone + " not found");
            }

            String token = jwtTokenProvider.createToken(phone, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid phone or password");
        }
    }


}
