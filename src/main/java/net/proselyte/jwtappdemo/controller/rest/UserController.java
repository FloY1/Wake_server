package net.proselyte.jwtappdemo.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import net.proselyte.jwtappdemo.JsonView.Views;
import net.proselyte.jwtappdemo.dto.EventType;
import net.proselyte.jwtappdemo.dto.ObjectType;
import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.BookingStatus;
import net.proselyte.jwtappdemo.security.jwt.JwtUser;
import net.proselyte.jwtappdemo.service.BookingService;
import net.proselyte.jwtappdemo.service.UserService;
import net.proselyte.jwtappdemo.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.function.BiConsumer;

@RestController
@RequestMapping("user")
public class UserController {


    private UserService userService;
    private BookingService bookingService;
    private final BiConsumer<EventType,Booking> wsSender;


    public UserController(UserService userService, BookingService bookingService, WsSender wsSender) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.wsSender = wsSender.getSender(ObjectType.BOOKING,Views.IdTimeStatus.class);
    }

    @GetMapping("seasonTicket")
    public Long getSeasonTicket(@AuthenticationPrincipal JwtUser user){
        return userService.getSeasonTicket(user.getId());
    }


    @PostMapping(value = "booking",produces = "application/json" )
    public Booking addBooking(@RequestBody Booking booking,
                                @AuthenticationPrincipal JwtUser user){

        Booking newBooking = bookingService.addNewBooking(booking, user.getId());
        wsSender.accept(EventType.CREATE,newBooking);
        return newBooking;
    }

    @GetMapping("history")
    public Iterable<Booking> getBookingHistory(@AuthenticationPrincipal JwtUser user){
        return  bookingService.getBookingHistory(user.getId());
    }

    @GetMapping("booking")
    @JsonView(Views.IdTimeStatus.class)
    public Iterable<Booking> getAllBooking(@RequestBody Booking booking){
        return bookingService.getBookingByParm(booking);
    }

    @DeleteMapping("booking/{bookingId}")
    public void getBookingHistory(@PathVariable("bookingId") Long bookingId,
                                  @AuthenticationPrincipal JwtUser user){

        bookingService.deleteBooking(bookingId,user.getId());
    }

}
