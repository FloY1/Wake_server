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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        booking.setCreateTime(LocalTime.now().getHour()*60+LocalTime.now().getMinute());
        
        return bookingRepo.save(booking);
    }

    public Iterable<Booking> getBookingHistory(Long id) {
        List<Booking> bookingList =  bookingRepo.findByClient_IdAndStatus(id,BookingStatus.BOOKED_ACCEPTED);
        bookingList.addAll(bookingRepo.findByClient_IdAndStatus(id,BookingStatus.BOOKED));
        bookingList.addAll(bookingRepo.findByClient_IdAndStatus(id,BookingStatus.MISSED));
        bookingList.addAll(bookingRepo.findByClient_IdAndStatus(id,BookingStatus.MISSED_ADMIN));
        bookingList.addAll(bookingRepo.findByClient_IdAndStatus(id,BookingStatus.VISITED));
        bookingList.sort((booking, t1) -> {
            if(dateCompare(t1.getBookingDate(),booking.getBookingDate())==0){
                if(booking.getStartTime()>t1.getStartTime()){
                    return 1;
                }else {
                    return -1;
                }
            }
            return dateCompare(t1.getBookingDate(),booking.getBookingDate());
        });
        return bookingList;
    }

    public Booking deleteBooking(Long bookingId, Long id) {
        Booking booking = bookingRepo.findById(bookingId).get();
        if(booking.getClient().getId()!=id){
            log.warn("user {} does not own (booking {}) , owner it is a user {} ",id,bookingId,booking.getClient().getId());
            return null;

        }else {
            booking.setStatus(BookingStatus.MISSED);
            bookingRepo.save(booking);
            return booking;
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

    public Iterable<Booking> getTimeList(String data, Location location, int reversNumber,long id) {
        List<Booking> bookings = bookingRepo.findByBookingDateAndLocationAndReversNumberOrderByStartTime(data,location,reversNumber);
        bookings.forEach(booking -> {
            if(booking.getClient().getId() ==id&&booking.getStatus()==BookingStatus.BOOKED_NO_ACCEPTED){
                booking.setStatus(BookingStatus.MY_BOOKED_NO_ACCEPTED);
            }
        });


       return bookings;
    }

    public void print(String data, Location location,String path) {
        ArrayList<Booking> bookings =  new ArrayList<>(bookingRepo.findByBookingDateAndLocationOrderByStartTimeAsc(data,location));
        PdfParser parser = new PdfParser();

         parser.create(bookings,path);

    }

    public Booking updateNewBooking(Booking booking, Long id) {
        Booking booking1 = bookingRepo.findById(booking.getId()).get();
        if(booking1.getClient().getId()==id){
            booking1.setStatus(BookingStatus.BOOKED);
            bookingRepo.save(booking1);
            return booking1;
        }else{
            return null;
        }
    }
    private int dateCompare(String date,String date1){
        String s = date.substring(6,10);
        s= date.substring(3,5);
        s = date.substring(0,2);
        if(0==date.substring(6,10).compareTo(date1.substring(6,10))){
            if(0==date.substring(3,5).compareTo(date1.substring(3,5))){
                if(0==date.substring(0,2).compareTo(date1.substring(0,2))){
                    return 0;
                }else {
                    return date.substring(0,2).compareTo(date1.substring(0,2));
                }
            }else {
                return date.substring(3,5).compareTo(date1.substring(3,5));
            }
        }else {
            return date.substring(6,10).compareTo(date1.substring(6,10));
        }
    }

    public List<Booking> removeBookingNoAcepted() {
        List<Booking> bookings = bookingRepo.findByStatus(BookingStatus.BOOKED_NO_ACCEPTED);
        List<Booking> removeBooking = new ArrayList<>();
        bookings.forEach(booking -> {
            if(LocalTime.now().getHour()*60+LocalTime.now().getMinute() - booking.getCreateTime()>5){
                bookingRepo.delete(booking);
                removeBooking.add(booking);
            }
        });
        return  removeBooking;
    }

    public Booking delete(Long bookingId, Long id) {
        Booking booking = bookingRepo.findById(bookingId).get();
        if(booking.getClient().getId()!=id){
            log.warn("user {} does not own (booking {}) , owner it is a user {} ",id,bookingId,booking.getClient().getId());
            return null;

        }else {
            bookingRepo.delete(booking);
            return booking;
        }

    }
}
