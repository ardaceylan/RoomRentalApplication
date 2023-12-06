package com.api.gobooking.user.appuser;

import com.api.gobooking.user.Role;
import com.api.gobooking.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "app_user")
//@PrimaryKeyJoinColumn(name = "id")
public class AppUser extends User {
    private Double balance;
    private String city;
    private String taxNumber;
    private Timestamp registrationDate;
    private Boolean isBlocked;
    private Boolean isBannedFromBooking;
    private Boolean isBannedFromPosting;

    public AppUser(String name, String surname, String email, String password, Timestamp birthDate, Double balance, String city){
        super(name, surname, email, password, birthDate, Role.APP_USER);

        this.balance = balance;
        this.city = city;
        this.taxNumber = null;
        this.registrationDate = Timestamp.from(Instant.now());
        this.isBlocked = false;
        this.isBannedFromBooking = false;
        this.isBannedFromPosting = false;
    }

    public AppUser(AppUser appUser){
        super(appUser.getName(), appUser.getSurname(), appUser.getEmail(), appUser.getPassword(), appUser.getBirthDate(), Role.APP_USER);

        this.balance = appUser.getBalance();
        this.city = appUser.getCity();
        this.taxNumber = null;
        this.registrationDate = Timestamp.from(Instant.now());
        this.isBlocked = false;
        this.isBannedFromBooking = false;
        this.isBannedFromPosting = false;
    }

    public AppUser(AppUserRequest appUserRequest){
        super(appUserRequest.getName(), appUserRequest.getSurname(), appUserRequest.getEmail(), appUserRequest.getPassword(), appUserRequest.getBirthDate(), Role.APP_USER);

        this.balance = appUserRequest.getBalance();
        this.city = appUserRequest.getCity();
        this.taxNumber = null;
        this.registrationDate = Timestamp.from(Instant.now());
        this.isBlocked = false;
        this.isBannedFromBooking = false;
        this.isBannedFromPosting = false;
    }
}