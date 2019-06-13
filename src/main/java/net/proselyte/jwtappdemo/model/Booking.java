package net.proselyte.jwtappdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import net.proselyte.jwtappdemo.JsonView.Views;

import javax.persistence.*;

@Data
@Entity
@Table
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdTimeStatus.class)
    private Long id;


    private String bookingDate;



    @Enumerated(EnumType.STRING)
    private Location location;


    private int reversNumber;
    @JsonView(Views.IdTimeStatus.class)
    private int startTime;
    @JsonView(Views.IdTimeStatus.class)
    private int endTime;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.IdTimeStatus.class)
    private BookingStatus status;

    @ManyToOne
    @JsonIgnore
    private User client;

}
