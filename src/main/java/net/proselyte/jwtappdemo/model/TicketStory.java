package net.proselyte.jwtappdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.proselyte.jwtappdemo.model.enums.TicketStatus;

import javax.persistence.*;

@Data
@Entity
@Table
public class TicketStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String date;

    private int time;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JsonIgnore
    private User client;




}
