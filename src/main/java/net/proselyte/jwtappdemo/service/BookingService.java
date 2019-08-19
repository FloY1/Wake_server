package net.proselyte.jwtappdemo.service;

import com.itextpdf.text.Document;
import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.enums.BookingStatus;
import net.proselyte.jwtappdemo.model.enums.Location;
import net.proselyte.jwtappdemo.repository.BookingRepo;
import net.proselyte.jwtappdemo.repository.UserRepo;
import net.proselyte.jwtappdemo.util.parser.PdfParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class BookingService {

    private BookingRepo bookingRepo;
    private UserRepo userRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo, UserRepo userRepo) {
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
    }

    public Booking addNewBooking(Booking booking, Long id) {
        booking.setClient(userRepo.findById(id).get());
        booking.setStatus(BookingStatus.BOOKED_NO_ACCEPTED);
        
        
        return bookingRepo.save(booking);
    }

    public Iterable<Booking> getBookingHistory(Long id) {
        return userRepo.findById(id).get().getSortedBookingHistory();
    }

    public void deleteBooking(Long bookingId, Long id) {
        Booking booking = bookingRepo.findById(bookingId).get();
        if(booking.getClient().getId()!=id){
            log.warn("user {} does not own (booking {}) , owner it is a user {} ",id,bookingId,booking.getClient().getId());
        }else {
            booking.setStatus(BookingStatus.MISSED);
        }

    }


    public Iterable<Booking> getBookingByParm(Booking booking) {
        return bookingRepo.findByBookingDateAndLocationAndReversNumberOrderByStartTime(
                booking.getBookingDate(),
                booking.getLocation(),
                booking.getReversNumber()
        );
    }

    public Iterable<Booking> findAll() {
        return bookingRepo.findAll();
    }

    public Iterable<Booking> getTimeList(String data, Location location, int reversNumber) {
       return bookingRepo.findByBookingDateAndLocationAndReversNumberOrderByStartTime(data,location,reversNumber);
    }

    public void print(String data, Location location,String path) {
        ArrayList<Booking> bookings =  new ArrayList<>(bookingRepo.findByBookingDateAndLocationOrderByStartTimeAsc(data,location));
        PdfParser parser = new PdfParser();

         parser.create(bookings,path);

    }
}
