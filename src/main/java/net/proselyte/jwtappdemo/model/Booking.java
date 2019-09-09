package net.proselyte.jwtappdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import net.proselyte.jwtappdemo.JsonView.Views;
import net.proselyte.jwtappdemo.model.enums.BookingStatus;
import net.proselyte.jwtappdemo.model.enums.Location;

import javax.persistence.*;

@Data
@Entity
@Table
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdTimeDateReversStatus.class)
    private Long id;

    @JsonView(Views.IdTimeDateReversStatus.class)
    private String bookingDate;


    @Enumerated(EnumType.STRING)
    @JsonView(Views.IdTimeDateReversStatus.class)
    private Location location;


    @JsonView(Views.IdTimeDateReversStatus.class)
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


    @JsonIgnore
    private long createTime;


}
