package com.api.gobooking.user.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "gobooking/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public List<Admin> getAdmins(){
        return adminService.getAdmins();
    }

    @GetMapping(path = "{adminId}")
    public Admin getAdmin(@PathVariable("adminId") Integer adminId){
        return adminService.getAdmin(adminId);
    }

    @PostMapping
    public void addNewAdmin(@RequestBody AdminRequest adminRequest){
        adminService.addAdmin(adminRequest);
    }

    @DeleteMapping(path = "{adminId}")
    public void deleteAdmin(@PathVariable("adminId") Integer adminId){
        adminService.deleteAdmin(adminId);
    }

    @PutMapping(path = "{adminId}")
    public void updateAdmin( @PathVariable("adminId") Integer adminId,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String surname,
                             @RequestParam(required = false) String password,
                             @RequestParam(required = false) Timestamp birthDate,
                             @RequestParam(required = false) AdminRole adminRole
    )
    {
        adminService.updateAdmin(adminId, name, surname, password, birthDate, adminRole);
    }
}
