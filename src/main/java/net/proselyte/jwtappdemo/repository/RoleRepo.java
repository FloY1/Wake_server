package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role, Long> {

    public Role findByName (String name);
}
