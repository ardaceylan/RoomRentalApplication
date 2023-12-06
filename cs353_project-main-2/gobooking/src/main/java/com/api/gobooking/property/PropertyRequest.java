package com.api.gobooking.property;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest {
    private String title;
    private Integer max_people;
    private Integer room_number;
    private Integer bathroom_number;
    private String description;
    private Integer price_per_night;
    private PropertyType type;
    private Integer owner_id;
    private String city;
    private String district;
    private String neighborhood;
    private Integer buildingNo;
    private Integer apartmentNo;
    private Boolean wifi;
    private Boolean kitchen;
    private Boolean furnished;
    private Boolean parking;
    private Boolean ac;
    private Boolean elevator;
    private Boolean fire_alarm;
    private Integer floor;
    private Double rating;
    private Timestamp start_date;
    private Timestamp end_date;
}
