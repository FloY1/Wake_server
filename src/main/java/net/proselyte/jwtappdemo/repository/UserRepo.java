package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByPhone (String phone);
}
