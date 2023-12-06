package com.api.gobooking.booking;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    private int booking_id;

    private Timestamp start_date;

    private Timestamp end_date;

    private String status;

    private int booker_id;

    private int property_id;

    private int total_price;

}
