package com.api.gobooking.booking;

import com.api.gobooking.http.NameValueResponse;
import com.api.gobooking.http.StayingData;
import com.api.gobooking.http.TimeData;
import com.api.gobooking.http.TransactionsData;
import com.api.gobooking.user.UserRepository;
import com.api.gobooking.user.appuser.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.awt.desktop.QuitEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class BookingRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public boolean insert(Booking booking){

        String bookingSql = "INSERT INTO " +
                "booking (start_date, end_date, status,booker_id,property_id,total_price) " +
                "VALUES (:start_date, :end_date, :status,:booker_id,:property_id,:total_price)";

        Query bookingQuery = entityManager.createNativeQuery(bookingSql);

        //bookingQuery.setParameter("booking_id", booking.getBooking_id());
        bookingQuery.setParameter("start_date", booking.getStart_date());
        bookingQuery.setParameter("end_date", booking.getEnd_date());
        bookingQuery.setParameter("status", booking.getStatus());
        bookingQuery.setParameter("property_id", booking.getProperty_id());
        bookingQuery.setParameter("booker_id", booking.getBooker_id());
        bookingQuery.setParameter("total_price", booking.getTotal_price());

        int rowsAffected = bookingQuery.executeUpdate();

        return rowsAffected > 0;
    }

    @Transactional
    public boolean update(Booking booking) {
        String updateSql = "UPDATE booking " +
                "SET start_date = :start_date, end_date = :end_date, status = :status " +
                "WHERE booking_id = :id";

        Query updateQuery = entityManager.createNativeQuery(updateSql);

        updateQuery.setParameter("start_date", booking.getStart_date());
        updateQuery.setParameter("end_date", booking.getEnd_date());
        updateQuery.setParameter("status", booking.getStatus());
        updateQuery.setParameter("id", booking.getBooking_id());

        int rowsAffected = updateQuery.executeUpdate();

        return rowsAffected > 0;
    }

    @Transactional
    public boolean deleteById(int id) {
        String deleteSql = "DELETE FROM booking WHERE booking_id = :id";

        Query deleteQuery = entityManager.createNativeQuery(deleteSql);

        deleteQuery.setParameter("id", id);

        int rowsAffected = deleteQuery.executeUpdate();

        return rowsAffected > 0;
    }

    public Booking findById(int id) {
        String selectSql = "SELECT * FROM booking WHERE booking_id = :id";

        Query selectQuery = entityManager.createNativeQuery(selectSql, Booking.class);

        selectQuery.setParameter("id", id);

        try {
            return (Booking) selectQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Booking> findAllByBookerId(int booker_id) {
        String selectSql = "SELECT * FROM booking WHERE booker_id = :id";

        Query selectQuery = entityManager.createNativeQuery(selectSql, Booking.class);

        selectQuery.setParameter("id", booker_id);

        return selectQuery.getResultList();
    }

    public List<Booking> findAllByPropertyId(int property_id) {
        String selectSql = "SELECT * FROM booking WHERE property_id = :id";

        Query selectQuery = entityManager.createNativeQuery(selectSql, Booking.class);

        selectQuery.setParameter("id", property_id);

        return selectQuery.getResultList();
    }


    public List<Booking> findPastBookingsByBookerId(int bookerId) {
        String selectSql = "SELECT * FROM booking " +
                "WHERE booker_id = :bookerId AND end_date < CURRENT_TIMESTAMP";

        Query selectQuery = entityManager.createNativeQuery(selectSql, Booking.class);

        selectQuery.setParameter("bookerId", bookerId);

        return selectQuery.getResultList();
    }

    public List<Booking> findAll() {
        String selectSql = "SELECT * FROM booking";

        Query selectQuery = entityManager.createNativeQuery(selectSql, Booking.class);

        return selectQuery.getResultList();
    }

    public List<NameValueResponse> mostBookedCities() {
        String sql = "select " +
                "   property.city as name, " +
                "   COALESCE(count(booking.booking_id), 0) as value " +
                "from " +
                "   property " +
                "LEFT JOIN " +
                "booking ON property.property_id = booking.property_id " +
                "group by property.city " +
                "having COALESCE(count(booking.booking_id), 0) > 0 " +
                "order by value desc";

        Query query = entityManager.createNativeQuery(sql, NameValueResponse.class);

        return query.getResultList();
    }

    public List<StayingData> getStayingData() {
        String sql = "select " +
                "   property.city, COALESCE(CAST(AVG(EXTRACT(day FROM (booking.end_date - booking.start_date))) AS double precision), 0) AS days " +
                "from " +
                "   property " +
                "left join " +
                "   booking on property.property_id = booking.property_id " +
                "where booking.status = 'completed'" +
                "group by property.city " +
                "order by days desc ";

        Query query = entityManager.createNativeQuery(sql, StayingData.class);

        return query.getResultList();
    }

    public List<TransactionsData> getTransactionsData(Integer mode) {
        Integer count = null;
        String interval = null;
        if (mode == 3){
            count = 12;
            interval = "month";
        }else if (mode == 2){
            count = 30;
            interval = "day";
        }else if (mode == 1){
            count = 7;
            interval = "day";
        }else if (mode == 4){
            count = 5;
            interval = "year";
        }

        List<TransactionsData> result = new ArrayList<>();

        ArrayList<String> times = new ArrayList<>();
        times.add("today");
        times.add("1");
        for (int i = 2; i < count; i++) {
            times.add(String.format("%s", i));
        }

        String sql;
        Query query = null;
        TransactionsData transactionsData;
        Double number;
        String s = "SELECT COALESCE(sum(total_price), 0) AS avg_price FROM booking WHERE end_date < CURRENT_DATE - INTERVAL '%s %s' AND status = 'completed'";
        for (int i = count - 1; i >= 0; i--){
            sql = String.format(s, i, interval);

            query = entityManager.createNativeQuery(sql);

            number = ((Number) query.getSingleResult()).doubleValue();
            transactionsData = new TransactionsData(times.get(i), number);

            result.add(transactionsData);
        }

        return result;
    }
}
