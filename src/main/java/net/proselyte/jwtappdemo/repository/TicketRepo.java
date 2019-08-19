package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.Role;
import net.proselyte.jwtappdemo.model.TicketStory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepo extends JpaRepository<TicketStory, Long> {

    public Iterable<TicketStory> findAllByClient(long id);
}
