package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.Booking;
import net.proselyte.jwtappdemo.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Long> {

    List<Booking> findByBookingDateAndLocationAndReversNumberOrderByStartTime(String bookingDate, Location location, int reversNumber);

}
