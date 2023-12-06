package com.api.gobooking.user.admin;

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
public class AdminRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Timestamp birthDate;
    private AdminRole adminRole;
}
