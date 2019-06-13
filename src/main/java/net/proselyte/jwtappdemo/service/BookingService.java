package net.proselyte.jwtappdemo.service;

import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.BookingStatus;
import net.proselyte.jwtappdemo.repository.BookingRepo;
import net.proselyte.jwtappdemo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
