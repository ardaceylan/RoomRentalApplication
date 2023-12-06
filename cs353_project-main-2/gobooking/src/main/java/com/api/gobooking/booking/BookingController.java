package com.api.gobooking.booking;

import com.api.gobooking.http.NameValueResponse;
import com.api.gobooking.http.StayingData;
import com.api.gobooking.http.TransactionsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gobooking/bookings")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        int created = bookingService.createBooking(booking);
        if (created==0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking created successfully");
        }
        else if(created==2) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Do Not Have Enough Money");
        }
        else{

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unsuccessful Insertion!");

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable int id, @RequestBody Booking booking) {
        booking.setBooking_id(id);
        boolean updated = bookingService.updateBooking(booking);
        if (updated) {
            return ResponseEntity.status(HttpStatus.OK).body("Booking updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable int id) {
        boolean deleted = bookingService.deleteBookingById(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Booking deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
        Booking booking = bookingService.findBookingById(id);
        if (booking != null) {
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/thesameBooker's/{booker_id}")
    public ResponseEntity<List<Booking>> getAllBookingByBookerId(@PathVariable int booker_id) {
        List<Booking> bookings = bookingService.findAllBookingByBookerId(booker_id);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }
    @GetMapping("/thesameProperty's/{property_id}")
    public ResponseEntity<List<Booking>> getAllBookingByPropertyId(@PathVariable int property_id) {
        List<Booking> bookings = bookingService.findAllBookingByBookerId(property_id);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping("/pastBookings/{bookerId}")
    public ResponseEntity<List<Booking>> getPastBookingsByBookerId(@PathVariable int bookerId) {
        List<Booking> pastBookings = bookingService.findPastBookingsByBookerId(bookerId);
        return ResponseEntity.status(HttpStatus.OK).body(pastBookings);
    }


    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.findAllBookings();
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping(path = "most_booked_cities")
    public List<NameValueResponse> mostBookedCities(){
        return bookingService.mostBookedCities();
    }

    @GetMapping(path = "staying_data")
    public List<StayingData> getStayingData(){
        return bookingService.getStayingData();
    }

    @GetMapping(path = "transactions={mode}")
    public List<TransactionsData> getTransactionsData(@PathVariable("mode") Integer mode){
        return bookingService.getTransactionsData(mode);
    }
}
