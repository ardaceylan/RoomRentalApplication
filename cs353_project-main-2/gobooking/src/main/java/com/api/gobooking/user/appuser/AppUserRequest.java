package com.api.gobooking.user.appuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Timestamp birthDate;
    private Double balance;
    private String city;
}
