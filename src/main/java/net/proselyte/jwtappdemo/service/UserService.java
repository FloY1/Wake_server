package net.proselyte.jwtappdemo.service;

import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.dto.AuthenticationRequestDto;
import net.proselyte.jwtappdemo.model.Role;
import net.proselyte.jwtappdemo.model.enums.Status;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.repository.RoleRepo;
import net.proselyte.jwtappdemo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;




@Service
@Slf4j
public class UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public User register(AuthenticationRequestDto userDto) {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);

        user.setPhone(userDto.getPhone());
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setSeasonTicket(Long.parseLong("15"));
        User registerUser = userRepo.save(user);

        log.info("In register - user {} seccessfully" ,registerUser);

        return userRepo.save(registerUser);
    }

    public List<User> getAllUser() {
        log.info("In getAll");
        return (List<User>) userRepo.findAll();
    }

    public User findUserByPhone(String phone) {
        log.info("in findUserByPhone phone -"+ phone);

        return userRepo.findByPhone(phone);
    }

    public  User findUserById(Long id) {
        log.info("in findUserByID id -"+ id);

        return userRepo.findById(id).get();
    }

    public void deleteUser(Long id) {
        log.info("in deleteUser id -"+ id);

        User user = userRepo.findById(id).get();
        user.setStatus(Status.DELETED);
        userRepo.save(user);

    }

    public Long getSeasonTicket(Long id) {
        User user = userRepo.findById(id).get();
        return user.getSeasonTicket();
    }
}
