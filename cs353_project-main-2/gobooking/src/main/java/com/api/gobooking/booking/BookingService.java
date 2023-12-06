package com.api.gobooking.booking;
import com.api.gobooking.http.NameValueResponse;
import com.api.gobooking.http.StayingData;
import com.api.gobooking.http.TransactionsData;
import com.api.gobooking.user.appuser.AppUser;
import com.api.gobooking.user.appuser.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private AppUserRepository appUserRepository;

    // Constructor

    @Transactional
    public int createBooking(Booking booking) {
        Optional<AppUser> booker = appUserRepository.findById(booking.getBooker_id());
        double current_account = booker.get().getBalance();

        if(current_account<booking.getTotal_price())
            return 2;

        if(bookingRepository.insert(booking)){
            appUserRepository.updateBalance(booking.getBooker_id(), (current_account-(double) booking.getTotal_price()));
            return 0;

        }

        else{

            return 1;
        }


    }

    @Transactional
    public boolean updateBooking(Booking booking) {

        return bookingRepository.update(booking);
    }

    @Transactional
    public boolean deleteBookingById(int id) {

        return bookingRepository.deleteById(id);
    }

    public Booking findBookingById(int id) {

        return bookingRepository.findById(id);
    }

    public List<Booking> findAllBookingByBookerId(int booker_id) {

        return bookingRepository.findAllByBookerId(booker_id);
    }

    public List<Booking> findAllBookingByPropertyId(int property_id) {

        return bookingRepository.findAllByPropertyId(property_id);
    }

    public List<Booking> findPastBookingsByBookerId(int bookerId) {
        return bookingRepository.findPastBookingsByBookerId(bookerId);
    }

    public List<Booking> findAllBookings() {

        return bookingRepository.findAll();
    }

    public List<NameValueResponse> mostBookedCities() {
        return bookingRepository.mostBookedCities();
    }

    public List<StayingData> getStayingData() {
        return bookingRepository.getStayingData();
    }

    public List<TransactionsData> getTransactionsData(Integer mode) {
        return bookingRepository.getTransactionsData(mode);
    }
}
