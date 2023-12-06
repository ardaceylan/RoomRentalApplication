package com.api.gobooking.property;


import com.api.gobooking.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Table(name = "`property`")
@Entity

@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)


@PrimaryKeyJoinColumn(name = "property_id")

public class Property {
    @Id
    @Column(name = "property_id")
    private Integer id;
    private String title;
    private Integer max_people;
    private Integer room_number;
    private Integer bathroom_number;
    private String description;
    private Integer price_per_night;
    private Integer owner_id;
    private String city;
    private String district;
    private String neighborhood;
    private Integer buildingNo;
    private Integer apartmentNo;
    private Integer floor;
    private Boolean wifi;
    private Boolean kitchen;
    private Boolean furnished;
    private Boolean parking;
    private Boolean ac;
    private Boolean elevator;
    private Boolean fire_alarm;
    private Double rating;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp start_date;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp end_date;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp added_date;
    @Enumerated(value = EnumType.STRING)
    private PropertyType type;
    @Enumerated(value = EnumType.STRING)
    private Status status;




    public Property(String title, Status status, Integer max_people, Integer bathroom_number, Integer room_number, String description, Integer  price_per_night, PropertyType type, Integer owner_id) {
        this.title = title;
        this.status = Status.ACTIVE;
        this.max_people = max_people;
        this.description = description;
        this.price_per_night = price_per_night;
        this.added_date = Timestamp.from(Instant.now());
        this.bathroom_number = bathroom_number;
        this.room_number = room_number;
        this.type = type;
        this.owner_id = owner_id;
    }

    public Property(PropertyRequest propertyRequest) {
        this.title = propertyRequest.getTitle();
        this.status = Status.ACTIVE;
        this.max_people = propertyRequest.getMax_people();
        this.description = propertyRequest.getDescription();
        this.price_per_night = propertyRequest.getPrice_per_night();
        this.added_date = Timestamp.from(Instant.now());
        this.bathroom_number = propertyRequest.getBathroom_number();
        this.room_number = propertyRequest.getRoom_number();
        this.type = propertyRequest.getType();
        this.owner_id = propertyRequest.getOwner_id();
        this.city = propertyRequest.getCity();
        this.district = propertyRequest.getDistrict();
        this.neighborhood = propertyRequest.getNeighborhood();
        this.buildingNo = propertyRequest.getBuildingNo();
        this.apartmentNo = propertyRequest.getApartmentNo();
        this.wifi = propertyRequest.getWifi();
        this.kitchen = propertyRequest.getKitchen();
        this.furnished = propertyRequest.getFurnished();
        this.parking = propertyRequest.getParking();
        this.ac = propertyRequest.getAc();
        this.elevator = propertyRequest.getElevator();
        this.floor = propertyRequest.getFloor();
        this.start_date = propertyRequest.getStart_date();
        this.end_date = propertyRequest.getEnd_date();
    }
}
