package com.api.gobooking.user.admin;

import com.api.gobooking.user.Role;
import com.api.gobooking.user.User;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "admin")
//@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {

    @Enumerated(value = EnumType.STRING)
    private AdminRole adminRole;

    public Admin(String name, String surname, String email, String password, Timestamp birthDate, AdminRole adminRole){
        super(name, surname, email, password, birthDate, Role.ADMIN);

        this.adminRole = adminRole;
    }

    public Admin(AdminRequest adminRequest) {
        super(adminRequest.getName(), adminRequest.getSurname(), adminRequest.getEmail(), adminRequest.getPassword(), adminRequest.getBirthDate(), Role.ADMIN);

        this.adminRole = adminRequest.getAdminRole();
    }
}
