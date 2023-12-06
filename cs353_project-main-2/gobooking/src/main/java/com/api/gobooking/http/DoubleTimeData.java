package com.api.gobooking.http;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DoubleTimeData {
    @Id
    private String time;
    private Integer ads;
    private Integer bookings;
}
