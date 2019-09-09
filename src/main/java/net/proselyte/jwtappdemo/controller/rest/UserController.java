package net.proselyte.jwtappdemo.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import net.proselyte.jwtappdemo.JsonView.Views;
import net.proselyte.jwtappdemo.dto.EventType;
import net.proselyte.jwtappdemo.dto.ObjectType;
import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.TicketStory;
import net.proselyte.jwtappdemo.model.enums.Location;
import net.proselyte.jwtappdemo.security.jwt.JwtUser;
import net.proselyte.jwtappdemo.service.BookingService;
import net.proselyte.jwtappdemo.service.TicketService;
import net.proselyte.jwtappdemo.service.UserService;
import net.proselyte.jwtappdemo.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("user")
@EnableScheduling
public class UserController {


    private UserService userService;
    private BookingService bookingService;
    private TicketService ticketService;
    private final BiConsumer<EventType,Booking> wsSender;

    @Value("${spring.profile.active:prod}")
    private String profile;




    @Autowired
    public UserController(TicketService ticketService,UserService userService, BookingService bookingService, WsSender wsSender) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.bookingService = bookingService;
        this.wsSender = wsSender.getSender(ObjectType.BOOKING,Views.IdTimeDateReversStatus.class);
    }

    @GetMapping("seasonTicket")
    public Long getSeasonTicket(@AuthenticationPrincipal JwtUser user){
        return userService.getSeasonTicket(user.getId());
    }


    @GetMapping("history")

    public Iterable<Booking> getBookingHistory(@AuthenticationPrincipal JwtUser user){
        return  bookingService.getBookingHistory(user.getId());
    }

    @GetMapping("seasonTicketHistory")
    public Iterable<TicketStory> getTicketHistory(@AuthenticationPrincipal JwtUser user){
        return  ticketService.getTicketStoryByUser((user.getId()));
    }

    @GetMapping("booking")
    @JsonView(Views.IdTimeDateReversStatus.class)
    public Iterable<Booking> getAllBooking(){
        return bookingService.findAll();

    }
    @GetMapping("/timeList")
    @JsonView(Views.IdTimeStatus.class)
    public Iterable<Booking> getTimeList(@RequestHeader Location location,
                                         @RequestHeader String data,
                                         @RequestHeader int reversNumber,
                                         @AuthenticationPrincipal JwtUser user) {
        return bookingService.getTimeList(data,location,reversNumber,user.getId());
    }







    @PostMapping(value = "booking",produces = "application/json" )
    @JsonView(Views.IdTimeDateReversStatus.class)
    public Booking addBooking(@RequestBody Booking booking,
                                @AuthenticationPrincipal JwtUser user){

        Booking newBooking = bookingService.addNewBooking(booking, user.getId());


        wsSender.accept(EventType.CREATE,newBooking);
        return newBooking;
    }

    @PostMapping("/print")
    public String getTimeList(@RequestBody Booking booking) {

        String path = "iTextTable.pdf";
        if(!"dev".equals(profile)){
            path = "/opt/tomcat/latest/iTextTable.pdf";
        }

        bookingService.print(booking.getBookingDate(),booking.getLocation(),path);
        System.out.println(booking.getBookingDate()+" "+booking.getLocation());
        return "yse";
    }








    @PutMapping(value = "booking",produces = "application/json" )
    public void updateBooking(@RequestBody List<Booking> bookings,
                              @AuthenticationPrincipal JwtUser user){

        bookings.forEach(booking->{
        Booking newBooking = bookingService.updateNewBooking(booking, user.getId());
        if(newBooking!=null) {
            wsSender.accept(EventType.ACCEPT, newBooking);

        }
        });
    }







    @DeleteMapping("booking/{bookingId}")
    public void getBookingHistory(@PathVariable("bookingId") Long bookingId,
                                  @AuthenticationPrincipal JwtUser user){
        wsSender.accept(EventType.CANSELED, bookingService.deleteBooking(bookingId,user.getId()));
    }





    @Scheduled(fixedRate = 60000)
    private void removeBookingNoAcepted(){

        List<Booking> removeBooking = bookingService.removeBookingNoAcepted();
        removeBooking.forEach(booking -> {
            wsSender.accept(EventType.TIME_IS_OVER,booking);
        });
    }








}
