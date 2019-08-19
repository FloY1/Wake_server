package net.proselyte.jwtappdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.proselyte.jwtappdemo.model.enums.TicketStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table
public class TicketStory {

    @Id
    private long id;

    private String data;

    private int time;

    private TicketStatus status;

    @ManyToOne
    @JsonIgnore
    private User client;




}
