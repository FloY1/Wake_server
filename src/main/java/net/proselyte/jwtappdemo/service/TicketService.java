package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.model.TicketStory;
import net.proselyte.jwtappdemo.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }


    public Iterable<TicketStory> getTicketStoryByUser(long id){
        return ticketRepo.findAllByClient(id);
    }
}
