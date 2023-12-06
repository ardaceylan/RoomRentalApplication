package com.api.gobooking.property;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PropertyResponse {
    @Id
    private Integer property_id;
    private String title;
    private String description;
    private Integer owner_id;
    private String owner_name;
    private String owner_surname;
    private String city;
    private String district;
    private String neighborhood;
    private Timestamp added_date;
    private Double avg_rating;
    private Double times_booked;
}
