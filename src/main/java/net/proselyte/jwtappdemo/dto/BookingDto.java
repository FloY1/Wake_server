package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import net.proselyte.jwtappdemo.JsonView.Views;
import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.model.enums.BookingStatus;
import net.proselyte.jwtappdemo.model.enums.Location;

import javax.persistence.*;

public class BookingDto {



    @JsonView(Views.IdTimeDateReversStatus.class)
    private Long id;

    @JsonView(Views.IdTimeDateReversStatus.class)
    private String bookingDate;



    @JsonView(Views.IdTimeDateReversStatus.class)
    private Location location;


    @JsonView(Views.IdTimeDateReversStatus.class)
    private int reversNumber;
    @JsonView(Views.IdTimeStatus.class)
    private int startTime;
    @JsonView(Views.IdTimeStatus.class)
    private int endTime;


    @JsonView(Views.IdTimeStatus.class)
    private BookingStatus status;

    @JsonView(Views.ClientId.class)
    private long clientId;


    public BookingDto(Booking booking) {
        this.id = booking.getId();
        this.bookingDate = booking.getBookingDate();
        this.location = booking.getLocation();
        this.reversNumber = booking.getReversNumber();
        this.startTime = booking.getStartTime();
        this.endTime = booking.getEndTime();
        this.status = booking.getStatus();
        this.clientId = booking.getClient().getId();
    }
}
