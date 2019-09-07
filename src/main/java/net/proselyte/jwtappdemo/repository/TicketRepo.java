package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.Role;
import net.proselyte.jwtappdemo.model.TicketStory;
import net.proselyte.jwtappdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TicketRepo extends JpaRepository<TicketStory, Long> {

    public Iterable<TicketStory> findByClient( User user);
}
